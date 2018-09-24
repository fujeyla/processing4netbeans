/**
 *   This file is part of Processing4Netbeans Netbeans plugin, 
 *   initially created by fujeyla.
 *
 *   Processing4Netbeans is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Processing4Netbeans is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Processing4Netbeans.  If not, see <https://www.gnu.org/licenses/>
 */

package org.netbeans.modules.java.processingproject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.netbeans.modules.java.processingproject.contributions.dialog.ContribsDownloadController;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;
import org.openide.DialogDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

public final class AddProcessingLibAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(AddProcessingLibAction.class.getName());
    private static final int JFXPANEL_WIDTH_INT = 710;
    private static final int JFXPANEL_HEIGHT_INT = 400;

    private final DataFolder contribsFolder;
    private final J2SEProject project;
    private PropertyEvaluator eval;

    public AddProcessingLibAction(DataFolder df, J2SEProject project) {
        super(NbBundle.getMessage(AddProcessingLibAction.class, "FN_addprocessingcontrib"));
        this.contribsFolder = df;
        this.project = project;
    }

    DialogDescriptor dialogDescriptor;

    @Override
    public void actionPerformed(ActionEvent e) {
        JFXPanel jfxPanel = initFxContainer(this.project.getProjectDirectory().getPath() + File.separator + contribsFolder.getName() + File.separator);

        Frame mainFrame = WindowManager.getDefault().getMainWindow();
        JDialog dialog = new JDialog(mainFrame);
        dialog.setTitle("Add processing.org contributions libraries...");
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        dialog.setContentPane(contentPane);
        contentPane.add(jfxPanel, BorderLayout.CENTER);
        dialog.setModal(true);
        dialog.pack();
        dialog.setLocationRelativeTo(mainFrame);
        //dialog.setLocation((mainFrame.getWidth() - JFXPANEL_WIDTH_INT)/2, (mainFrame.getHeight() - JFXPANEL_HEIGHT_INT)/2);
        dialog.setVisible(true);
        

        DataObject[] contribsSubFolders = contribsFolder.getChildren();
        for (DataObject contribsSubFolder : contribsSubFolders) {

            final String folderString = contribsSubFolder.getName();
            try {


                FileObject[] compileRoots = ClassPath.getClassPath(contribsFolder.getPrimaryFile(), ClassPath.COMPILE).getRoots();
                this.eval = this.project.getAntProjectHelper().getStandardPropertyEvaluator();
                LOGGER.info("javac.classpath is : " + this.eval.getProperty("javac.classpath"));
                FileObject contribFo = this.project.getProjectDirectory().getFileObject("contribs").getFileObject(folderString);
                FileObject contribLibrary = contribFo.getFileObject("library");
                if(contribLibrary!=null){
                    FileObject[] libraryChildren = contribLibrary.getChildren();
                    for (FileObject libraryFile : libraryChildren) {
                        if (libraryFile.getExt().equals("jar")) {
                            ProjectClassPathModifier.addRoots(new URL[]{FileUtil.getArchiveRoot(libraryFile.toURL())}, this.project.getSourceRoots().getRoots()[0], ClassPath.COMPILE);
                        }
                    }
                    LOGGER.info("javac.classpath after adding contrib is : " + this.eval.getProperty("javac.classpath"));
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    

    public JFXPanel initFxContainer(final String contribsFolderPath) {
        final JFXPanel fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                try {
                    createScene(fxContainer, contribsFolderPath);
                } catch (IOException ex) {
                    Logger.getLogger(ContribsDownloadController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return fxContainer;
    }

    private void createScene(JFXPanel fxContainer, String contribsFolderPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ContribsDownloadController.class.getResource("ContribsDownloadFXML.fxml"));
        AnchorPane contribsOverview = (AnchorPane) loader.load();
        ContribsDownloadController controller = loader.getController();
        controller.setFolderPath(contribsFolderPath);
        StackPane root = new StackPane();
        root.getChildren().add(contribsOverview);
        fxContainer.setScene(new Scene(root));
    }

}
