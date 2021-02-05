package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.ComboBox
import com.intellij.ui.layout.panel
import com.intellij.openapi.ui.DescriptionLabel
import javax.swing.JComponent

class MdBookCommandSettingsEditor : SettingsEditor<MdBookBuildConfiguration>()  {
    private val buildActionChoiceBox = ComboBox<String>()
        .apply { choices.forEach { addItem(it.command) } }

    private val infoText = DescriptionLabel("")

    override fun resetEditorFrom(s: MdBookBuildConfiguration) {
        val id = choices.indexOfFirst { it.command == s.command }
        if (id == -1) return
        buildActionChoiceBox.selectedIndex = id
        infoText.text = choices[id].description
    }

    @Throws(ConfigurationException::class)
    override fun applyEditorTo(s: MdBookBuildConfiguration) {
        s.command = buildActionChoiceBox.selectedItem as String
        val id = buildActionChoiceBox.selectedIndex
        s.suggestedNameValue = choices[id].suggestedName
        infoText.text = choices[id].description
    }

    override fun createEditor(): JComponent = panel {
        row("build action") { buildActionChoiceBox() }
        row { infoText() }
    }

    companion object {
        private val choices = arrayOf(
            ChoiceInfo (
                "build --open",
                "Build And Open In Browser",
                "Build book and open it in browser to see the result"
            ),
            ChoiceInfo (
                "watch --open",
                "Watch In Browser",
                "Open the book in the browser while continuously watch " +
                        "it for changes and rebuild if changes occur."
            )
        )
    }
}

private data class ChoiceInfo(
    val command: String,
    val suggestedName: String,
    val description: String
)