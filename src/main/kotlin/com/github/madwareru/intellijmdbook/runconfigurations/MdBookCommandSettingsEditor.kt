package com.github.madwareru.intellijmdbook.runconfigurations

import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DescriptionLabel
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.layout.panel
import javax.swing.JComponent

class MdBookCommandSettingsEditor : SettingsEditor<MdBookBuildConfiguration>()  {
    private val buildActionChoiceBox =
        ComboBox<String>().apply {
            BuildCommandChoice
                .variants
                .forEach { this.addItem(it.command) }
        }

    private val mdBookTextField = TextFieldWithBrowseButton()

    private val infoText = DescriptionLabel("")

    override fun resetEditorFrom(s: MdBookBuildConfiguration) {
        val id = BuildCommandChoice.variants.indexOfFirst { it.command == s.command }
        if (id == -1) return
        setChoiceId(id)
        mdBookTextField.text = s.mdBookPath
    }

    @Throws(ConfigurationException::class)
    override fun applyEditorTo(s: MdBookBuildConfiguration) {
        val id = buildActionChoiceBox.selectedIndex
        val choice = BuildCommandChoice.variants[id]

        s.command = choice.command
        s.suggestedNameValue = choice.suggestedName
        s.mdBookPath = mdBookTextField.text
        infoText.text = choice.description
    }

    override fun createEditor(): JComponent = panel {
        row("build action") { buildActionChoiceBox() }
        row("mdBook path") { mdBookTextField() }
        row { infoText() }
    }

    private fun setChoiceId(id: Int) {
        val choice = BuildCommandChoice.variants[id]
        infoText.text = choice.description
        buildActionChoiceBox.selectedIndex = id
    }
}

