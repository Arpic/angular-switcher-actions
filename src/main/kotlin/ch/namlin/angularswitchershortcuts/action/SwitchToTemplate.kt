package ch.namlin.angularswitchershortcuts.action

import ch.namlin.angularswitchershortcuts.util.ExtensionSwitcher
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project


class SwitchToTemplate : AnAction() {

    override fun update(event: AnActionEvent) {
        val project: Project? = event.project
        val editor: Editor? = event.getData(CommonDataKeys.EDITOR)
        event.presentation.isEnabledAndVisible = (project != null && editor != null)
    }

    override fun actionPerformed(event: AnActionEvent) {
        val editor: Editor? = event.getData(CommonDataKeys.EDITOR)
        editor?.let {
            ExtensionSwitcher(it).switchToTemplate()
        }
    }
}