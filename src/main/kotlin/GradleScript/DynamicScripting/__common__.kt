package GradleScript.DynamicScripting

fun construct() {
	Scripting.construct()
}
fun destruct() {
	Scripting.destruct()
}

typealias ImportAction = (ScriptImport) -> Unit
typealias UnimportAction = (ScriptImport) -> Unit
typealias ImportWith = (ScriptImport) -> Unit
typealias ExportWith = (ScriptExport, MutableMap<String, (Array<out Any?>) -> Any?>, MutableMap<String, (Array<out Any?>) -> Any?>, MutableMap<String, (Array<out Any?>) -> Any?>) -> Unit
