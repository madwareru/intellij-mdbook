package com.github.madwareru.intellijmdbook.runconfigurations

object BuildCommandChoice {
    data class ChoiceInfo(
        val command: String,
        val suggestedName: String,
        val description: String
    )

    val variants = arrayOf(
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