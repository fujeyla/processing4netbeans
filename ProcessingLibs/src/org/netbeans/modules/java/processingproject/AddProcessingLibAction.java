/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.java.processingproject;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

public final class AddProcessingLibAction extends AbstractAction {

    private static final Logger LOGGER = Logger.getLogger(AddProcessingLibAction.class.getName());

    private final DataFolder folder;
    private final J2SEProject project;
    private PropertyEvaluator eval;

    public AddProcessingLibAction(DataFolder df, J2SEProject project) {
        super(NbBundle.getMessage(AddProcessingLibAction.class, "FN_addprocessingcontrib"));
        this.folder = df;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NotifyDescriptor.InputLine nd = new NotifyDescriptor.InputLine(
                "do you want to add a contribs lib?",
                "Processing Contribs Lib",
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE);

        Object result = DialogDisplayer.getDefault().notify(nd);

        if (result.equals(NotifyDescriptor.OK_OPTION)) {
            final String folderString = nd.getInputText();
            try {
                DataFolder.create(folder, folderString);
                FileObject[] compileRoots = ClassPath.getClassPath(folder.getPrimaryFile(), ClassPath.COMPILE).getRoots();
                LOGGER.info("compileRoots are: " + Arrays.asList(compileRoots));
                this.eval = this.project.getAntProjectHelper().getStandardPropertyEvaluator();
                LOGGER.info("javac.classpath is : " + this.eval.getProperty("javac.classpath"));
                FileObject newContribFo = this.project.getProjectDirectory().getFileObject("contribs").getFileObject(folderString);
                FileObject newContribLibrary = newContribFo.getFileObject("library");
                FileObject[] libraryChildren = newContribLibrary.getChildren();
                LOGGER.info(" libraryChildren are " + Arrays.asList(libraryChildren));
                for (FileObject libraryFile : libraryChildren) {
                    if(libraryFile.getExt().equals("jar")){
                        LOGGER.info("FOUND " + libraryFile.getName() + " to add");
                        ProjectClassPathModifier.addRoots(new URL[]{FileUtil.getArchiveRoot(libraryFile.toURL())}, this.project.getSourceRoots().getRoots()[0], ClassPath.COMPILE);
                    }
                }
                LOGGER.info("javac.classpath after adding contrib is : " + this.eval.getProperty("javac.classpath"));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
