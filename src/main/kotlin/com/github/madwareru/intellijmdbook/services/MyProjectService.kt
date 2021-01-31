package com.github.madwareru.intellijmdbook.services

import com.github.madwareru.intellijmdbook.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
