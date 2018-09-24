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

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

public class ProcessingDataNodeFactory implements NodeFactory {

    @Override
    public NodeList<?> createNodes(Project project) {
        J2SEProject p = project.getLookup().lookup(J2SEProject.class);
        assert p != null;
        return new DataNodeList(p);
    }

    private class DataNodeList implements NodeList<Node> {

        J2SEProject project;

        public DataNodeList(J2SEProject project) {
            this.project = project;
        }

        @Override
        public List<Node> keys() {
            FileObject dataFolder = project.getProjectDirectory().getFileObject("data");
            List<Node> result = new ArrayList<>();
            if (dataFolder != null) {
                try {
                    result.add(DataObject.find(dataFolder).getNodeDelegate());
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
