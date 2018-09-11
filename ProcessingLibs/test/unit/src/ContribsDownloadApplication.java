/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.netbeans.modules.java.processingproject.contributions.dialog.ContribsDownloadController;

/**
 *
 * @author jerom
 */
public class ContribsDownloadApplication extends JApplet {

    private static final int JFXPANEL_WIDTH_INT = 710;
    private static final int JFXPANEL_HEIGHT_INT = 400;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }

                JFrame frame = new JFrame("JavaFX 2 in Swing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JApplet applet = new ContribsDownloadApplication();
                applet.init();

                frame.setContentPane(applet.getContentPane());

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                applet.start();
            }
        });
    }

    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    createScene();
                } catch (IOException ex) {
                    Logger.getLogger(ContribsDownloadApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ContribsDownloadController.class.getResource("ContribsDownloadFXML.fxml"));
        AnchorPane contribsOverview = (AnchorPane) loader.load();
        ContribsDownloadController controller = loader.getController();     
        controller.setFolderPath(".\\");
        StackPane root = new StackPane();
        root.getChildren().add(contribsOverview);
        fxContainer.setScene(new Scene(root));
    }

}
