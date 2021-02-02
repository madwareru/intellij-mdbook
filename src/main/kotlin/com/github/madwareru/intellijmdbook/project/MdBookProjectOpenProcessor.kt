package com.github.madwareru.intellijmdbook.project

import com.github.madwareru.intellijmdbook.project.services.MdBookProjectService
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupManager
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.PlatformProjectOpenProcessor
import com.intellij.projectImport.ProjectOpenProcessor

class MdBookProjectOpenProcessor : ProjectOpenProcessor() {
    override fun getName() =
        MdBookProjectConstants.NAME

    override fun canOpenProject(file: VirtualFile) =
        FileUtil.namesEqual(file.name, MdBookProjectConstants.MANIFEST_FILE)

    override fun doOpenProject(
        virtualFile: VirtualFile,
        projectToClose: Project?,
        forceOpenInNewFrame: Boolean
    ): Project? =
        PlatformProjectOpenProcessor
            .getInstance()
            .doOpenProject(
                if (virtualFile.isDirectory) virtualFile else virtualFile.parent,
                projectToClose,
                forceOpenInNewFrame
            )?.also { project ->
                StartupManager
                    .getInstance(project)
                    .runWhenProjectIsInitialized {
                        Log.debug("started")
                    }
            }

    private val Log = Logger.getInstance(MdBookProjectOpenProcessor::class.java)
}

