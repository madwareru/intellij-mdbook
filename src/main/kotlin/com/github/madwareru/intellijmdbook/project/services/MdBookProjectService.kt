package com.github.madwareru.intellijmdbook.project.services

import com.github.madwareru.intellijmdbook.project.MdBookProject
import com.intellij.openapi.project.Project

interface MdBookProjectService {
    val project: Project
    val mdBookProject: MdBookProject?
}

