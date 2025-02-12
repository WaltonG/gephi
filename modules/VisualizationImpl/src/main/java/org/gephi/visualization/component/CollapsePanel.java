/*
Copyright 2008-2010 Gephi
Authors : Mathieu Bastian <mathieu.bastian@gephi.org>
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

package org.gephi.visualization.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.UIManager;
import org.gephi.ui.utils.UIUtils;
import org.gephi.visualization.VizController;

/**
 * @author Mathieu Bastian
 */
public class CollapsePanel extends javax.swing.JPanel {

    private boolean extended;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton extendButton;
    // End of variables declaration//GEN-END:variables

    /**
     * Creates new form CollapsePanel
     */
    public CollapsePanel() {
        initComponents();
    }

    public void init(JComponent topBar, final JComponent extendedPanel, boolean extended) {
        add(topBar, BorderLayout.CENTER);
        add(extendedPanel, BorderLayout.SOUTH);

        this.extended = extended;
        if (extended) {
            extendButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/org/gephi/visualization/component/arrowDown.png"))); // NOI18N
            extendButton.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/org/gephi/visualization/component/arrowDown_rollover.png"))); // NOI18N

        } else {
            extendButton.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/org/gephi/visualization/component/arrowUp.png"))); // NOI18N
            extendButton.setRolloverIcon(new javax.swing.ImageIcon(
                getClass().getResource("/org/gephi/visualization/component/arrowUp_rollover.png"))); // NOI18N
        }
        extendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ext = CollapsePanel.this.extended;
                ext = !ext;
                CollapsePanel.this.extended = ext;
                if (ext) {
                    extendButton.setIcon(new javax.swing.ImageIcon(
                        getClass().getResource("/org/gephi/visualization/component/arrowDown.png"))); // NOI18N
                    extendButton.setRolloverIcon(new javax.swing.ImageIcon(
                        getClass().getResource("/org/gephi/visualization/component/arrowDown_rollover.png"))); // NOI18N
                } else {
                    extendButton.setIcon(new javax.swing.ImageIcon(
                        getClass().getResource("/org/gephi/visualization/component/arrowUp.png"))); // NOI18N
                    extendButton.setRolloverIcon(new javax.swing.ImageIcon(
                        getClass().getResource("/org/gephi/visualization/component/arrowUp_rollover.png"))); // NOI18N
                }
                extendedPanel.setVisible(ext);

                // Workaround for JOGL bug 1274
                (VizController.getInstance().getDrawable()).reinitWindow();
            }
        });
        if (!extended) {
            extendedPanel.setVisible(extended);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        extendButton = new javax.swing.JButton();

        setOpaque(true);
        setLayout(new java.awt.BorderLayout());

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 3));

        extendButton.setToolTipText(
            org.openide.util.NbBundle.getMessage(CollapsePanel.class, "CollapsePanel.extendButton.text")); // NOI18N
        extendButton.setAlignmentY(0.0F);
        extendButton.setBorderPainted(false);
        extendButton.setContentAreaFilled(false);
        extendButton.setFocusable(false);
        buttonPanel.add(extendButton);

        add(buttonPanel, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents
}
