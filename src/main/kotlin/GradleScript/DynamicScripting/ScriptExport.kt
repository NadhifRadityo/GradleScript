package GradleScript.DynamicScripting

open class ScriptExport(
	val scriptId: String,
	var what: Any?,
	val being: String,
	val with: MutableList<ExportWith>
)
