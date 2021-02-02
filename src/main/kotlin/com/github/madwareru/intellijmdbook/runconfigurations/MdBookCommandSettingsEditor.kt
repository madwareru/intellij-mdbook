package com.github.madwareru.intellijmdbook.runconfigurations

import com.github.madwareru.intellijmdbook.runconfigurations.MdBookCommandConfiguration
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.options.SettingsEditor
import kotlin.Throws
import javax.swing.JComponent
import javax.swing.JPanel
import com.intellij.openapi.ui.TextFieldWithBrowseButton

import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.openapi.ui.LabeledComponent


class MdBookCommandSettingsEditor : SettingsEditor<MdBookCommandConfiguration>() {
    private lateinit var panel: JPanel
    private lateinit var myMainClass: LabeledComponent<ComponentWithBrowseButton<*>>

    override fun resetEditorFrom(s: MdBookCommandConfiguration) {

    }

    @Throws(ConfigurationException::class)
    override fun applyEditorTo(s: MdBookCommandConfiguration) {

    }

    override fun createEditor(): JComponent = panel

    fun createUIComponents() {
        myMainClass = LabeledComponent<ComponentWithBrowseButton<*>>()
        myMainClass.setComponent(TextFieldWithBrowseButton())
    }
}