/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.java.processingproject;

import org.netbeans.api.project.Project;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeFactorySupport;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

public class ProcessingContribsNodeFactory implements NodeFactory {

    public ProcessingContribsNodeFactory() {
    }

    @Override
    public NodeList createNodes(Project project) {
        AbstractNode nd = new AbstractNode(Children.LEAF);
        nd.setDisplayName("Hello World!");
        
        return NodeFactorySupport.fixedNodeList(nd);
     }

}
