/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.processing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Project",
        id = "org.processing.AddProcessingLib"
)
@ActionRegistration(
        iconBase = "org/processing/ProcessingFileTemplate.png",
        displayName = "#CTL_AddProcessingLib"
)
@ActionReference(path="Projects/Actions")
@Messages("CTL_AddProcessingLib=Add processing lib")
public final class AddProcessingLib implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
    }
}
