package com.github.madwareru.intellijmdbook.project

import com.github.madwareru.intellijmdbook.project.services.MdBookProjectService
import com.github.madwareru.intellijmdbook.vfs.findParent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.GeneratedSourcesFilter
import com.intellij.openapi.vfs.VirtualFile

class MdBookGeneratedSourcesFilter : GeneratedSourcesFilter() {
    override fun isGeneratedSource(file: VirtualFile, project: Project): Boolean {
        val mdBookProject = project.service<MdBookProjectService>().mdBookProject ?: return false
        val buildDir = mdBookProject.buildDirectory
        return file.findParent(buildDir) != null
    }
}

