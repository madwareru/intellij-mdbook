package com.github.madwareru.intellijmdbook.project.services

import com.github.madwareru.intellijmdbook.project.MdBookProject
import com.github.madwareru.intellijmdbook.project.MdBookProjectConstants
import com.github.madwareru.intellijmdbook.psi.*
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ex.ProjectRootManagerEx
import com.intellij.psi.PsiManager
import org.toml.lang.psi.TomlFile

open class MdBookProjectServiceImpl(final override val project: Project) : MdBookProjectService {
    final override val mdBookProject: MdBookProject
    init {
        val pman = ProjectRootManagerEx.getInstance(project)

        val contentRootUrl = pman.contentRootUrls[0]
        val contentRoots = pman.contentRoots
        val childVirtualFile = contentRoots[0].findChild(MdBookProjectConstants.MANIFEST_FILE)

        mdBookProject = ApplicationManager
            .getApplication()
            .runReadAction<MdBookProject> {
                val tomlFile = PsiManager
                    .getInstance(project)
                    .findFile(childVirtualFile!!) as TomlFile
                val bookEntry = tomlFile.getTable{ "book" }
                val buildEntry = tomlFile.getTable{ "build" }

                MdBookProject(
                    bookEntry.getEntry("src", "src"),
                    buildEntry.getEntry("build-dir", "book"),
                    contentRootUrl
                )
            }
    }
}