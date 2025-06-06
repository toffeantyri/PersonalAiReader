package ai.personal.reader.platform

import kotlinx.coroutines.suspendCancellableCoroutine
import javax.swing.JFileChooser
import kotlin.coroutines.resume

actual class FilePicker {
    actual suspend fun pickFile(): PlatformFile? = suspendCancellableCoroutine { continuation ->
        val fileChooser = JFileChooser()
        fileChooser.dialogTitle = "Выберите файл"
        // Можно добавить фильтры, если нужно ограничить типы файлов
        // fileChooser.fileFilter = FileNameExtensionFilter("Text Files", "txt")

        val userSelection = fileChooser.showOpenDialog(null)

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            val file = fileChooser.selectedFile
            if (file != null) {
                try {
                    val bytes = file.readBytes()
                    continuation.resume(
                        PlatformFile(
                            filePath = file.absolutePath,
                            fileContent = bytes
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    continuation.resume(null)
                }
            } else {
                continuation.resume(null)
            }
        } else {
            continuation.resume(null)
        }
    }
}