package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.icons.AllIcons

class MdBookCommandConfigurationType : ConfigurationTypeBase(
    "MdBookCommandRunConfiguration",
    "MdBook Build",
    "MdBook command run configuration",
    AllIcons.Actions.Execute
) {
    init {
        addFactory(MdBookBuildConfigurationFactory(this))
    }

    val factory: ConfigurationFactory get() = configurationFactories.single()

    companion object {
        fun getInstance(): MdBookCommandConfigurationType =
            ConfigurationTypeUtil.findConfigurationType(MdBookCommandConfigurationType::class.java)
    }
}

