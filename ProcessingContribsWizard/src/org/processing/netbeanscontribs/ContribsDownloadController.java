/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.processing.netbeanscontribs;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.processing.netbeanscontribs.contributions.Contribution;
import org.processing.netbeanscontribs.contributions.ContributionsReader;

/**
 * FXML Controller class
 *
 */
public class ContribsDownloadController implements Initializable {

    private static final int UNZIP_BUFFER_SIZE = 4096;
    private static final String PROCESSING_ORG_LISTING_URL = "http://download.processing.org/contribs";
    private static final String LOCAL_FILENAME = "contribs.txt";

    @FXML
    private TableView<Contribution> contribsTableView;

    @FXML
    private Button installButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextArea selectedContribDescriptionArea;

    @FXML
    private Hyperlink selectedContribNameAndLink;

    @FXML
    private Label selectedContribVersionLabel;

    @FXML
    private Label selectedContribAuthorsLabel;

    @FXML
    private TextField filterField;

    @FXML
    private TableColumn<Contribution, String> nameColumn;

    @FXML
    private TableColumn<Contribution, String> purposeColumn;

    @FXML
    private TableColumn<Contribution, String> categoryColumn;

    @FXML
    private ProgressIndicator contribsLoadingProgressIndicator;

    @FXML
    private ProgressBar downloadProgressBar;

    private Contribution selectedContrib;

    private String folderPath;

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public class DownloadContribService extends Service<String> {

        private final String urlStr;
        private final String folderPath;
        private Downloader downloader;

        public DownloadContribService(String urlString, String folderPath) {
            this.urlStr = urlString;
            this.folderPath = folderPath;
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {

                    downloader = new Downloader(new URL(urlStr), folderPath);
                    downloadProgressBar.visibleProperty().bind(downloader.getRunningProperty());
                    downloadProgressBar.progressProperty().bind(downloader.getProgressProperty());
                    installButton.disableProperty().bind(downloader.getRunningProperty());
                    downloader.getRunningProperty().addListener((observable, oldValue, newValue) -> {
                        if (oldValue == true && newValue == false) {
                            if (downloader.getStatus() == downloader.COMPLETE) {
                                try {
                                    unzip(downloader.getDownloadedFilePath(), folderPath);
                                    File file = new File(downloader.getDownloadedFilePath());
                                    if(file.delete()){
                                        System.out.println("archive successfully deleted");
                                    }else{
                                        System.out.println("unable to delete archive");
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(ContribsDownloadController.class.getName()).log(Level.SEVERE, "Unable to unzip or delete downloaded file " + downloader.getDownloadedFilePath(), ex);
                                }

                            }
                        }
                    });
                    return null;
                }
            };
        }

        public String getDownloadedFilePath() {
            return downloader.getDownloadedFilePath();
        }
    }

    public class InitService extends Service<String> {

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                @Override
                protected String call() throws Exception {
                    URL processingOrgListingURL = new URL(PROCESSING_ORG_LISTING_URL);
                    File tempContribFile = new File(LOCAL_FILENAME);
                    tempContribFile.setWritable(true);

                    download(processingOrgListingURL, null, tempContribFile);

                    ContributionsReader contributionsReader = new ContributionsReader(tempContribFile.getPath());
                    ObservableList<Contribution> contributions = contributionsReader.read();
//                    for (Contribution contribution : contributions) {
//                        System.out.println("Contribution is : " + contribution);
//                    }

                    nameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
                    purposeColumn.setCellValueFactory(cellData -> cellData.getValue().getSentenceProperty());
                    categoryColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoriesProperty());

                    // Listen for selection changes and show the contrib details when changed.
                    contribsTableView.getSelectionModel().selectedItemProperty().addListener(
                            (observable, oldValue, newValue) -> showContribDetails(newValue));

                    FilteredList<Contribution> filteredContribs = new FilteredList<>(contributions, p -> true);

                    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredContribs.setPredicate(contrib -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();

                            if (contrib.getName().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (contrib.getCategories().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (contrib.getAuthors().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            } else if (contrib.getParagraph() != null && contrib.getParagraph().toLowerCase().contains(lowerCaseFilter)) {
                                return true;
                            }

                            return false; // Does not match.
                        });
                    });

                    contribsTableView.setItems(filteredContribs);
                    return null;
                }
            };
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final InitService initService = new InitService();
            contribsLoadingProgressIndicator.visibleProperty().bind(initService.runningProperty());
            initService.restart();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showContribDetails(Contribution contrib) {
        if (contrib != null) {
            this.selectedContrib = contrib;
            selectedContribNameAndLink.setText(contrib.getName());
            selectedContribNameAndLink.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(contrib.getWebsiteUrl());
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            });
            selectedContribDescriptionArea.setText(contrib.getParagraph().length() > 0 ? contrib.getParagraph() : contrib.getSentence());
            selectedContribAuthorsLabel.setText(extractAuthorName(contrib.getAuthors()));
            selectedContribVersionLabel.setText(contrib.getPrettyVersion());
        } else {
            selectedContribNameAndLink.setText("");
            selectedContribDescriptionArea.setText("");
            selectedContribAuthorsLabel.setText("");
            selectedContribVersionLabel.setText("");
        }
    }

    public String extractAuthorName(String authorsText) {
        StringBuilder sbAuthors = new StringBuilder();
        if (!authorsText.contains("[")) {
            sbAuthors.append(authorsText); //only author name, no link provided
        } else {
            String[] authors = authorsText.split("[\\[\\]]");
            for (String author : authors) {
                if (author.trim().length() > 0 && !author.trim().startsWith("(")) {
                    sbAuthors.append(author).append(",");
                }
            }
            sbAuthors.deleteCharAt(sbAuthors.length() - 1);
        }
        return sbAuthors.toString();
    }

    @FXML
    public void downloadAndInstall(ActionEvent actionEvent) {
        DownloadContribService downloadService = new DownloadContribService(this.selectedContrib.getDownloadUrl(), this.folderPath);
        downloadService.restart();

    }

    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[UNZIP_BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    static boolean download(URL source, byte[] post, File dest) {
        boolean success = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) source.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            conn.setConnectTimeout(15 * 1000);
            conn.setReadTimeout(60 * 1000);

            if (post == null) {
                conn.setRequestMethod("GET");
                conn.connect();
            }

            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(dest);

            byte[] b = new byte[8192];
            int amount;
            int total = 0;
            while ((amount = in.read(b)) != -1) {
                out.write(b, 0, amount);
                total += amount;
            }
            out.flush();
            out.close();
            success = true;

        } catch (SocketTimeoutException ste) {
            ste.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return success;
    }
}
