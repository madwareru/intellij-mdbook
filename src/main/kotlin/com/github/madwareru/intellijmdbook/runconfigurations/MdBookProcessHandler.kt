package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.AnsiEscapeDecoder
import com.intellij.execution.process.KillableProcessHandler
import com.intellij.openapi.util.Key

class MdBookProcessHandler(commandLine: GeneralCommandLine) :
    KillableProcessHandler(mediate(commandLine, false, false)),
    AnsiEscapeDecoder.ColoredTextAcceptor {
    private val decoder: AnsiEscapeDecoder = MdBookAnsiEscapeDecoder()

    init {
        setShouldKillProcessSoftly(true)
    }

    override fun notifyTextAvailable(text: String, outputType: Key<*>) {
        decoder.escapeText(text, outputType, this)
    }

    override fun coloredTextAvailable(text: String, attributes: Key<*>) {
        super.notifyTextAvailable(text, attributes)
    }
}