package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.execution.process.AnsiEscapeDecoder
import com.intellij.execution.process.ProcessOutputType
import com.intellij.openapi.util.Key

class MdBookAnsiEscapeDecoder : AnsiEscapeDecoder() {
    override fun escapeText(text: String, outputType: Key<*>, textAcceptor: ColoredTextAcceptor) {
        val resultText = when {
            text.contains("[INFO]") -> "\u001B[34m$text"
            text.contains("[WARN]") -> "\u001B[33m$text"
            text.contains("Process finished with exit code 0") -> "\u001B[32m$text"
            text.contains("[ERROR]") -> "\u001B[31m$text"
            text.contains("Process finished") -> "\u001B[31m$text"
            else -> "\u001B[37m$text"
        }
        super.escapeText(resultText, ProcessOutputType.STDERR, textAcceptor)
    }
}