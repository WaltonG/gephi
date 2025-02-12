/*
Copyright 2008-2010 Gephi
Authors : Sébastien Heymann <sebastien.heymann@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

Copyright 2011 Gephi Consortium. All rights reserved.

The contents of this file are subject to the terms of either the GNU
General Public License Version 3 only ("GPL") or the Common
Development and Distribution License("CDDL") (collectively, the
"License"). You may not use this file except in compliance with the
License. You can obtain a copy of the License at
http://gephi.org/about/legal/license-notice/
or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
specific language governing permissions and limitations under the
License.  When distributing the software, include this License Header
Notice in each file and include the License files at
/cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
License Header, with the fields enclosed by brackets [] replaced by
your own identifying information:
"Portions Copyrighted [year] [name of copyright owner]"

If you wish your version of this file to be governed by only the CDDL
or only the GPL Version 3, indicate your decision by adding
"[Contributor] elects to include this software in this distribution
under the [CDDL or GPL Version 3] license." If you do not indicate a
single choice of license, a recipient has the option to distribute
your version of this file under either the CDDL, the GPL Version 3 or
to extend the choice of license to its licensees as provided above.
However, if you add GPL Version 3 code and therefore, elected the GPL
Version 3 license, then the option applies only if the new code is
made subject to such option by the copyright holder.

Contributor(s):

Portions Copyrighted 2011 Gephi Consortium.
 */

package org.gephi.desktop.recentfiles;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.gephi.desktop.importer.api.ImportControllerUI;
import org.gephi.desktop.mrufiles.api.MostRecentFiles;
import org.gephi.desktop.project.api.ProjectControllerUI;
import org.openide.awt.DynamicMenuContent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * @author Sébastien Heymann
 */
public class RecentFiles extends AbstractAction implements DynamicMenuContent {

    private static final String GEPHI_EXTENSION = "gephi";

    @Override
    public void actionPerformed(ActionEvent e) {
        // does nothing, this is a popup menu
    }

    @Override
    public JComponent[] getMenuPresenters() {
        return createMenu();
    }

    @Override
    public JComponent[] synchMenuPresenters(JComponent[] items) {
        return createMenu();
    }

    private JComponent[] createMenu() {
        JMenu menu = new JMenu(NbBundle.getMessage(RecentFiles.class, "CTL_OpenRecentFiles"));
        JComponent[] menuItems = createSubMenus();
        for (JComponent item : menuItems) {
            menu.add(item);
        }
        return new JComponent[] {menu};
    }

    private JComponent[] createSubMenus() {
        List<JComponent> items = new ArrayList<>();

        MostRecentFiles mru = Lookup.getDefault().lookup(MostRecentFiles.class);
        for (String filePath : mru.getMRUFileList()) {
            final File file = new File(filePath);
            if (file.exists()) {
                final String fileName = file.getName();
                JMenuItem menuItem = new JMenuItem(new AbstractAction(fileName) {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FileObject fileObject = FileUtil.toFileObject(file);
                        if (fileObject.hasExt(GEPHI_EXTENSION)) {
                            ProjectControllerUI pc = Lookup.getDefault().lookup(ProjectControllerUI.class);
                            try {
                                pc.openProject(file);
                            } catch (Exception ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        } else {
                            ImportControllerUI importController = Lookup.getDefault().lookup(ImportControllerUI.class);
                            if (importController.getImportController().isFileSupported(file)) {
                                importController.importFile(fileObject);
                            }
                        }
                    }
                });
                items.add(menuItem);
            }
        }
        return items.toArray(new JComponent[0]);
    }
}
