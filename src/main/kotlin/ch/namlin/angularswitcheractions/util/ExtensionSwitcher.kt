package ch.namlin.angularswitcheractions.util

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import java.util.*

class ExtensionSwitcher(private val project: Project, private val editor: Editor) {
    private val testExtensions = listOf("spec.ts")
    private val templateExtensions = listOf("html", "svg")
    private val componentExtensions = listOf("ts")
    private val styleExtensions = listOf("css", "scss", "less", "styl")
    private val supportedExtensions = testExtensions + templateExtensions + componentExtensions  + styleExtensions

    fun switchToTemplate() {
        switchToGroup(templateExtensions)
    }

    fun switchToComponent() {
        switchToGroup(componentExtensions)
    }

    fun switchToStyle() {
        switchToGroup(styleExtensions)
    }

    fun switchToTest() {
        switchToGroup(testExtensions)
    }

    fun switchToGroup(nextGroup: List<String>) {
        val basePath = this.currentBasePath
        for (extension in nextGroup) {
            val file = findFileByPath("$basePath.$extension")
            if (file.isPresent) {
                openFile(file.get())
                return
            }
        }
    }

    private val currentBasePath: String?
        get() {
            var path = editor.virtualFile?.canonicalPath ?: throw BasePathNotFoundException()
            supportedExtensions.forEach { extension: String ->
                path = path.replace(".$extension", "")
            }
            return path
        }

    private fun findFileByPath(path: String): Optional<VirtualFile> {
        return Optional.ofNullable<VirtualFile>(LocalFileSystem.getInstance().findFileByPath(path))
    }

    private fun openFile(file: VirtualFile) {
        OpenFileDescriptor(this.project, file).navigate(true)
    }
}