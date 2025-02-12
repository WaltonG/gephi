package org.gephi.branding.desktop;

import java.awt.desktop.OpenFilesEvent;
import java.awt.desktop.OpenFilesHandler;
import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Logger;
import org.gephi.desktop.importer.api.ImportControllerUI;
import org.gephi.desktop.project.api.ProjectControllerUI;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * Handles files from double click at opening time
 */
public class ProjectOpenFilesHandler implements OpenFilesHandler {

    private static final String GEPHI_EXTENSION = "gephi";

    @Override
    public void openFiles(OpenFilesEvent openFilesEvent) {
        Logger.getLogger(ProjectOpenFilesHandler.class.getName())
            .info("Handling " + openFilesEvent.getFiles().size() + " from opening");

        FileObject[] fileObjects = openFilesEvent.getFiles().stream().filter(File::exists).map(
            FileUtil::toFileObject).toArray(FileObject[]::new);

        Optional<FileObject>
            projectFile = Arrays.stream(fileObjects).filter(f -> f.hasExt(GEPHI_EXTENSION)).findFirst();

        // Open single project file and discard any other files
        if (projectFile.isPresent()) {
            ProjectControllerUI pc = Lookup.getDefault().lookup(ProjectControllerUI.class);
            try {
                pc.openProject(FileUtil.toFile(projectFile.get()));
            } catch (Exception ew) {
                Exceptions.printStackTrace(ew);
                NotifyDescriptor.Message msg = new NotifyDescriptor.Message(NbBundle
                    .getMessage(ProjectOpenFilesHandler.class, "ProjectOpenFilesHandler.openGephiError"),
                    NotifyDescriptor.WARNING_MESSAGE);
                DialogDisplayer.getDefault().notify(msg);
            }
        } else if (fileObjects.length > 0) {
            // Open files
            ImportControllerUI importController = Lookup.getDefault().lookup(ImportControllerUI.class);
            importController.importFiles(fileObjects);
        }
    }
}
