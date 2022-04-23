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

typealias ExportAction = (ScriptExport) -> Unit
typealias UnexportAction = (ScriptExport) -> Unit
typealias ExportWithPropertyCallback = Pair<((Any?) -> Any?)?, ((Any?, Any?) -> Unit)?>
typealias ExportWithMethodCallback =  ((Any?, Array<out Any?>) -> Any?)?
typealias ExportWith = (ScriptExport, MutableMap<String, ExportWithPropertyCallback>, MutableMap<String, ExportWithMethodCallback>) -> Unit
