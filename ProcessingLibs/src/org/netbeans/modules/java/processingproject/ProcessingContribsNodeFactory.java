package org.netbeans.modules.java.processingproject;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

public class ProcessingContribsNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        J2SEProject p = project.getLookup().lookup(J2SEProject.class);
        assert p != null;
        return new ContribsNodeList(p);
    }

    private class ContribsNodeList implements NodeList<Node> {

        J2SEProject project;

        public ContribsNodeList(J2SEProject project) {
            this.project = project;
        }

        @Override
        public List<Node> keys() {
            FileObject contribsFolder = project.getProjectDirectory().getFileObject("contribs");
            List<Node> result = new ArrayList<>();
            if (contribsFolder != null) {
                try {
                    result.add(new ContribsNode(DataObject.find(contribsFolder).getNodeDelegate()));
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            return result;
        }

        @Override
        public Node node(Node node) {
            return new FilterNode(node);
        }

        @Override
        public void addNotify() {
        }

        @Override
        public void removeNotify() {
        }

        @Override
        public void addChangeListener(ChangeListener cl) {
        }

        @Override
        public void removeChangeListener(ChangeListener cl) {
        }

    }

}
