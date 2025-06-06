package ai.personal.reader.platform

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

actual class FilePicker(private val activity: ComponentActivity) {

    private val getContentLauncher =
        activity.registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            resultDeferred?.complete(uri)
            resultDeferred = null
        }
    private var resultDeferred: CompletableDeferred<Uri?>? = null

    actual suspend fun pickFile(): PlatformFile? = withContext(Dispatchers.Main) {
        if (resultDeferred?.isActive == true) {
            resultDeferred?.cancel()
        }
        resultDeferred = CompletableDeferred()

        getContentLauncher.launch("*/*")

        val uri = resultDeferred?.await()

        if (uri != null) {
            val fileName = getFileName(activity, uri)
            val fileContent = readFileContent(activity, uri)
            if (fileContent != null) {
                PlatformFile(filePath = uri.toString(), fileContent = fileContent)
            } else {
                null
            }
        } else {
            null
        }
    }

    private fun getFileName(context: Context, uri: Uri): String {
        var name = "unknown_file"
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex)
                }
            }
        }
        return name
    }

    private fun readFileContent(context: Context, uri: Uri): ByteArray? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream: InputStream ->
                inputStream.readBytes()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
} 