package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.execution.Executor
import com.intellij.execution.ExternalizablePath
import com.intellij.execution.configurations.*
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class MdBookBuildConfiguration(
    project: Project,
    name: String,
    factory: ConfigurationFactory
) : BaseCommandConfiguration(project, name, factory) {
    override var command: String = BuildCommandChoice.variants[0].command
    override var suggestedNameValue = BuildCommandChoice.variants[0].suggestedName
    override var info = BuildCommandChoice.variants[0].description

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val wd = ExternalizablePath
                    .urlValue(workingDirectory.toString())
                    .removePrefix("file:")

                val cmd = PtyCommandLine()
                    .withUseCygwinLaunch(false)
                    .withWorkDirectory(wd)
                    .withExePath("~/.cargo/bin/mdbook")
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

