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
    abstract var info: String
    abstract var mdBookPath: String

    var workingDirectory: Path? =
        if (project.service<MdBookProjectService>().mdBookProject == null) {
            null
        } else {
            Paths.get(
                ExternalizablePath.localPathValue(
                    project
                        .service<MdBookProjectService>()
                        .mdBookProject!!
                        .workingDirectory
                )
            )
        }

    override fun suggestedName(): String = suggestedNameValue

    private fun writeOption(
        element: Element,
        action: (Element) -> Unit
    ) {
        val opt = Element("option")
        action(opt)
        element.addContent(opt)
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)
        writeOption(element) { opt ->
            opt.setAttribute("name", "command")
            opt.setAttribute("value", command)
        }
        writeOption(element) { opt ->
            opt.setAttribute("name", "suggestedName")
            opt.setAttribute("value", suggestedNameValue)
        }
        writeOption(element) { opt ->
            opt.setAttribute("name", "info")
            opt.setAttribute("value", info)
        }
        writeOption(element) { opt ->
            opt.setAttribute("name", "workingDirectory")
            opt.setAttribute("value", ExternalizablePath.urlValue(workingDirectory.toString()))
        }
        writeOption(element) { opt ->
            opt.setAttribute("name", "mdBookPath")
            opt.setAttribute("value", mdBookPath)
        }
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
                    "info" -> {
                        info = attributeValue
                    }
                    "workingDirectory" -> {
                        workingDirectory = Paths.get(ExternalizablePath.localPathValue(attributeValue))
                    }
                    "mdBookPath" -> {
                        mdBookPath = attributeValue
                    }
                    else -> {}
                }
            }
    }
}