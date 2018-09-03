/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.java.processingproject;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.loaders.DataFolder;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

public final class AddProcessingLibAction extends AbstractAction {

    private final DataFolder folder;

    public AddProcessingLibAction(DataFolder df) {
        super(NbBundle.getMessage(AddProcessingLibAction.class, "FN_addprocessingcontrib"));
        folder = df;
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
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
