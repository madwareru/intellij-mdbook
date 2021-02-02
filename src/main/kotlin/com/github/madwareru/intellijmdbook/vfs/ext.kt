package com.github.madwareru.intellijmdbook.vfs

import com.intellij.openapi.vfs.VirtualFile

fun VirtualFile.findParent(searchedName: String ): VirtualFile? =
    when {
        parent == null -> null
        parent.name == searchedName -> parent
        else -> parent.findParent(searchedName)
    }