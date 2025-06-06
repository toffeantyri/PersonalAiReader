package ai.personal.reader.platform

expect class FilePicker {
    suspend fun pickFile(): PlatformFile?
}