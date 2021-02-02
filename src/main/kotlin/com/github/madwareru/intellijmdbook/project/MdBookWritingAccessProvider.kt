package com.github.madwareru.intellijmdbook.project

import com.github.madwareru.intellijmdbook.project.services.MdBookProjectService
import com.github.madwareru.intellijmdbook.vfs.findParent
import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.WritingAccessProvider

class MdBookWritingAccessProvider(private val project: Project) : WritingAccessProvider() {
    override fun requestWriting(vararg files: VirtualFile): Collection<VirtualFile> {
        val mdBookProject = project.service<MdBookProjectService>().mdBookProject
        val buildDir = mdBookProject.buildDirectory
        return files.filter { it.findParent(buildDir) != null }
    }

    override fun isPotentiallyWritable(file: VirtualFile): Boolean {
        val mdBookProject = project.service<MdBookProjectService>().mdBookProject
        val buildDir = mdBookProject.buildDirectory
        return file.findParent(buildDir) == null
    }
}

class MdBookTreeStructureProvider(private val project: Project) : TreeStructureProvider {
    override fun modify(
        parent: AbstractTreeNode<*>,
        children: MutableCollection<AbstractTreeNode<*>>,
        settings: ViewSettings?
    ): Collection<AbstractTreeNode<*>> {
        val buildDir = project
            .service<MdBookProjectService>()
            .mdBookProject
            .buildDirectory
        return children
            .filter { child ->
                val (virtualFile, isDir) = when (child) {
                    is PsiFileNode -> child.virtualFile to false
                    is PsiDirectoryNode -> child.virtualFile to true
                    else -> null to false
                }
                if (virtualFile == null) {
                    false
                } else {
                    val isValidFile = !isDir
                    val isValidDirectory = isDir && virtualFile.name != buildDir
                    isValidFile || isValidDirectory
                }
            }
    }

}