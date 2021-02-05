package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class MdBookBuildConfigurationFactory(type: MdBookCommandConfigurationType) : ConfigurationFactory(type) {

    override fun getId(): String = ID

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return MdBookBuildConfiguration(project, "MdBook Build", this)
    }

    companion object {
        const val ID: String = "MdBook Build"
    }
}
