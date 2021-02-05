package com.github.madwareru.intellijmdbook.runconfigurations

import com.github.madwareru.intellijmdbook.project.services.MdBookProjectService
import com.intellij.execution.ExternalizablePath
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.LocatableConfigurationBase
import com.intellij.execution.configurations.RunConfigurationWithSuppressedDefaultDebugAction
import com.intellij.execution.configurations.RunProfileState
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import org.jdom.Element
import java.nio.file.Path
import java.nio.file.Paths

abstract class BaseCommandConfiguration(
    project: Project,
    name: String,
    factory: ConfigurationFactory
) : LocatableConfigurationBase<RunProfileState>(project, factory, name),
    RunConfigurationWithSuppressedDefaultDebugAction {
    abstract var command: String
    abstract var suggestedNameValue: String

    var workingDirectory: Path? = Paths.get(
        ExternalizablePath.localPathValue(
            project
                .service<MdBookProjectService>()
                .mdBookProject
                .workingDirectory
        )
    )

    override fun suggestedName(): String = suggestedNameValue

    override fun writeExternal(element: Element) {
        super.writeExternal(element);
        val opt = Element("option")
        opt.setAttribute("name", "command")
        opt.setAttribute("value", command)
        element.addContent(opt)

        val opt2 = Element("option")
        opt2.setAttribute("name", "suggestedName")
        opt2.setAttribute("value", suggestedNameValue)
        element.addContent(opt2)

        val opt1 = Element("option")
        opt1.setAttribute("name", "workingDirectory")
        opt1.setAttribute("value", ExternalizablePath.urlValue(workingDirectory.toString()))
        element.addContent(opt1)
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        element.children
            .asSequence()
            .filter {
                it.name == "option" &&
                it.getAttributeValue("name") in arrayOf( "command", "workingDirectory" )
            }
            .forEach {
                val attributeValue = it.getAttributeValue("value" )
                when (it.getAttributeValue("name")) {
                    "command" -> {
                        command = attributeValue
                    }
                    "suggestedName" -> {
                        suggestedNameValue = attributeValue
                    }
                    "workingDirectory" -> {
                        workingDirectory = Paths.get(ExternalizablePath.localPathValue(attributeValue))
                    }
                    else -> {}
                }
            }
    }
}