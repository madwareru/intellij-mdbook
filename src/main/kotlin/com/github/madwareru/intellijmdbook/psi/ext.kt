package com.github.madwareru.intellijmdbook.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.toml.lang.psi.TomlFile
import org.toml.lang.psi.TomlKey
import org.toml.lang.psi.TomlKeyValue
import org.toml.lang.psi.TomlTable

inline fun <reified T : PsiElement> PsiElement.childrenOfType(): Sequence<T> =
    PsiTreeUtil.getChildrenOfTypeAsList(this, T::class.java).asSequence()

fun TomlTable?.getEntry(key: String, default: String) = this?.childrenOfType<TomlKeyValue>()
    ?.firstOrNull { kv -> kv.key.textMatches(key)}
    ?.value?.text?.trim { it == '"' } ?: default

inline fun TomlFile.getTable(selector: () -> String): TomlTable? =
    childrenOfType<TomlTable>()
        .firstOrNull{ table ->
            val headerKey = table.header.childrenOfType<TomlKey>().first()
            headerKey.textMatches(selector())
        }