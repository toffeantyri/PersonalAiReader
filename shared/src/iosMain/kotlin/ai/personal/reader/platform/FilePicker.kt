package ai.personal.reader.platform

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.reinterpret
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL
import platform.UIKit.UIApplication
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerMode
import platform.UIKit.UIDocumentPickerViewController
import platform.UniformTypeIdentifiers.UTTypeItem
import platform.darwin.NSObject
import kotlin.coroutines.resume

actual class FilePicker {
    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual suspend fun pickFile(): PlatformFile? = suspendCancellableCoroutine { continuation ->
        val documentPicker = UIDocumentPickerViewController(
            documentTypes = listOf(UTTypeItem.identifier), // Allow picking any item type
            inMode = UIDocumentPickerMode.UIDocumentPickerModeOpen
        )

        val delegate = object : NSObject(), UIDocumentPickerDelegateProtocol {
            override fun documentPicker(
                controller: UIDocumentPickerViewController,
                didPickDocumentAtURL: NSURL
            ) {
                controller.dismissViewControllerAnimated(true, null)
                didPickDocumentAtURL.startAccessingSecurityScopedResource()
                val data = NSData.dataWithContentsOfURL(didPickDocumentAtURL)
                didPickDocumentAtURL.stopAccessingSecurityScopedResource()

                val fileName = didPickDocumentAtURL.lastPathComponent ?: "unknown_file"
                val fileContent = data?.let { nsData ->
                    memScoped {
                        val bytes = nsData.bytes?.reinterpret<ByteVar>()
                        val length = nsData.length.toInt()
                        if (bytes != null) {
                            ByteArray(length) { index -> bytes[index] }
                        } else {
                            null
                        }
                    }
                }

                if (fileContent != null) {
                    continuation.resume(
                        PlatformFile(
                            filePath = didPickDocumentAtURL.path ?: fileName,
                            fileContent = fileContent
                        )
                    )
                } else {
                    continuation.resume(null)
                }
            }

            override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
                controller.dismissViewControllerAnimated(true, null)
                continuation.resume(null)
            }
        }
        documentPicker.delegate = delegate

        // Present the picker from the root view controller
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
            documentPicker,
            true,
            null
        )
    }
}