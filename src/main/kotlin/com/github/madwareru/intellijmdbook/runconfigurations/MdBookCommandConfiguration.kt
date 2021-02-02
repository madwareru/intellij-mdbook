package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.execution.Executor
import com.intellij.execution.ExternalizablePath
import com.intellij.execution.configurations.*
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class MdBookCommandConfiguration(
    project: Project,
    name: String,
    factory: ConfigurationFactory
) : BaseCommandConfiguration(project, name, factory) {
    override var command: String = "build --open"

    override fun suggestedName() = "Build And Open In Browser"

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val wd = ExternalizablePath
                    .urlValue(workingDirectory.toString())
                    .removePrefix("file:")

                val cmd = PtyCommandLine()
                    .withUseCygwinLaunch(false)
                    .withWorkDirectory(wd)
                    .withExePath("mdbook")
                    .withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.NONE)
                    .withParameters(command.split(' '))

                val processHandler = MdBookProcessHandler(cmd)
                ProcessTerminatedListener.attach(processHandler)
                return processHandler
            }
        }
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return MdBookCommandSettingsEditor()
    }
}

