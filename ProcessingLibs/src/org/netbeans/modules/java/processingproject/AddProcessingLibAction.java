/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.java.processingproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Project",
        id = "org.netbeans.modules.java.processingproject.AddProcessingLibAction"
)
@ActionRegistration(
        iconBase = "org/netbeans/modules/java/processingproject/ProcessingFileTemplate.png",
        displayName = "#CTL_AddProcessingLibAction"
)
@ActionReference(path = "Projects/Actions")
@Messages("CTL_AddProcessingLibAction=Add Processing.org Lib")
public final class AddProcessingLibAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }
}
