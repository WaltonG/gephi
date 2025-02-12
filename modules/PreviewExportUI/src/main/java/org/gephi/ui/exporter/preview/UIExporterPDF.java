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

package org.gephi.ui.exporter.preview;

import com.itextpdf.text.Rectangle;
import javax.swing.JPanel;
import org.gephi.io.exporter.preview.PDFExporter;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.io.exporter.spi.ExporterUI;
import org.netbeans.validation.api.ui.ValidationPanel;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * @author Mathieu Bastian
 */
@ServiceProvider(service = ExporterUI.class)
public class UIExporterPDF implements ExporterUI {

    private final ExporterPDFSettings settings = new ExporterPDFSettings();
    private UIExporterPDFPanel panel;
    private ValidationPanel validationPanel;
    private PDFExporter exporterPDF;

    @Override
    public void setup(Exporter exporter) {
        exporterPDF = (PDFExporter) exporter;
        settings.load(exporterPDF);
        panel.setup(exporterPDF);
    }

    @Override
    public void unsetup(boolean update) {
        if (update) {
            panel.unsetup(exporterPDF);
            settings.save(exporterPDF);
        }
        panel = null;
        exporterPDF = null;
    }

    @Override
    public JPanel getPanel() {
        panel = new UIExporterPDFPanel();
        validationPanel = UIExporterPDFPanel.createValidationPanel(panel);
        return validationPanel;
    }

    @Override
    public boolean isUIForExporter(Exporter exporter) {
        return exporter instanceof PDFExporter;
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(UIExporterPDF.class, "UIExporterPDF.name");
    }

    private static class ExporterPDFSettings extends AbstractExporterSettings {

        // Preference names
        private final static String MARGIN_TOP = "PDF_marginTop";
        private final static String MARGIN_BOTTOM = "PDF_marginBottom";
        private final static String MARGIN_LEFT = "PDF_marginLeft";
        private final static String MARGIN_RIGHT = "PDF_marginRight";
        private final static String LANDSCAPE = "PDF_landscape";
        private final static String PAGE_SIZE_WIDTH = "PDF_pageSizeWidth";
        private final static String PAGE_SIZE_HEIGHT = "PDF_pageSizeHeight";
        // Default
        private final static PDFExporter DEFAULT = new PDFExporter();

        private void load(PDFExporter exporter) {
            exporter.setMarginTop(get(MARGIN_TOP, DEFAULT.getMarginTop()));
            exporter.setMarginBottom(get(MARGIN_BOTTOM, DEFAULT.getMarginBottom()));
            exporter.setMarginLeft(get(MARGIN_LEFT, DEFAULT.getMarginLeft()));
            exporter.setMarginRight(get(MARGIN_RIGHT, DEFAULT.getMarginRight()));
            exporter.setLandscape(get(LANDSCAPE, DEFAULT.isLandscape()));
            float width = get(PAGE_SIZE_WIDTH, DEFAULT.getPageSize().getWidth());
            float height = get(PAGE_SIZE_HEIGHT, DEFAULT.getPageSize().getHeight());
            exporter.setPageSize(new Rectangle(width, height));
        }

        private void save(PDFExporter exporter) {
            put(MARGIN_TOP, exporter.getMarginTop());
            put(MARGIN_BOTTOM, exporter.getMarginBottom());
            put(MARGIN_LEFT, exporter.getMarginLeft());
            put(MARGIN_RIGHT, exporter.getMarginRight());
            put(LANDSCAPE, exporter.isLandscape());
            put(PAGE_SIZE_WIDTH, exporter.getPageSize().getWidth());
            put(PAGE_SIZE_HEIGHT, exporter.getPageSize().getHeight());
        }
    }
}
