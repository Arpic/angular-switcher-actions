package ch.namlin.angularswitchershortcuts.action

import ch.namlin.angularswitchershortcuts.util.ExtensionSwitcher
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project


abstract class SwitchAction : AnAction() {

    override fun update(event: AnActionEvent) {
        val project: Project? = event.project
        val editor: Editor? = event.getData(EDITOR)
        event.presentation.isEnabledAndVisible = (project != null && editor != null)
    }

    override fun actionPerformed(event: AnActionEvent) {
        val editor: Editor? = event.getData(EDITOR)
        if (editor == null || event.project == null) return
        doSwitch(ExtensionSwitcher(event.project!!, editor))
    }

    abstract fun doSwitch(switcher: ExtensionSwitcher)

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.EDT
    }
}

class SwitchToTemplate : SwitchAction() {
    override fun doSwitch(switcher: ExtensionSwitcher) = switcher.switchToTemplate()
}

class SwitchToComponent : SwitchAction() {
    override fun doSwitch(switcher: ExtensionSwitcher) = switcher.switchToComponent()
}

class SwitchToStyle : SwitchAction() {
    override fun doSwitch(switcher: ExtensionSwitcher) = switcher.switchToStyle()
}

class SwitchToTest : SwitchAction() {
    override fun doSwitch(switcher: ExtensionSwitcher) = switcher.switchToTest()
}