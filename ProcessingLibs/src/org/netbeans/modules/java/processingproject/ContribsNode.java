/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.java.processingproject;

import java.awt.Image;
import javax.swing.Action;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;

public class ContribsNode extends FilterNode {

    private final J2SEProject project;

    public ContribsNode(Node folderNode, J2SEProject project) {
        super(folderNode);
        this.project = project;
    }

    @Override
    public javax.swing.Action[] getActions(boolean popup) {
        DataFolder df = getLookup().lookup(DataFolder.class);
        return new Action[]{
            new AddProcessingLibAction(df, this.project)
        };
    }

    @Override
    public Image getIcon(int type) {
        DataFolder root = DataFolder.findFolder(FileUtil.getConfigRoot());
        Image original = root.getNodeDelegate().getIcon(type);
        Image logoImage = ImageUtilities.loadImage("org/netbeans/modules/java/processingproject/processinglogo.png");
        return ImageUtilities.mergeImages(original, logoImage.getScaledInstance(12, 12, Image.SCALE_SMOOTH), 7, 7);
    }

    @Override
    public Image getOpenedIcon(int type) {
        return getIcon(type);
    }

}
