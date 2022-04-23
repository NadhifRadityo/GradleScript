package GradleScript.DynamicScripting

import GradleScript.Common.addOnConfigFinished
import GradleScript.Common.addOnConfigStarted
import GradleScript.Common.currentSession
import GradleScript.Common.groovyKotlinCaches
import GradleScript.Common.lastContext
import GradleScript.Context
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.attachAnyObject
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.attachObject
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.detachAnyObject
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.detachObject
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda1
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda2
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda1ToClosure
import GradleScript.Strategies.CommonUtils.purgeThreadLocal
import GradleScript.Strategies.GradleUtils.asBuildProject0
import GradleScript.Strategies.GradleUtils.extraPropertiesRemoveKey
import GradleScript.Strategies.KeywordsUtils
import GradleScript.Strategies.KeywordsUtils.being
import GradleScript.Strategies.KeywordsUtils.with
import GradleScript.Strategies.StringUtils.randomString
import GradleScript.Strategies.Utils.__invalid_type
import GradleScript.Strategies.Utils.__must_not_happen
import groovy.lang.Closure
import org.gradle.api.initialization.IncludedBuild
import org.gradle.api.internal.file.BaseDirFileResolver
import java.io.File
import java.util.*

object Scripting {
	@JvmStatic private var cache: GroovyKotlinCache<Scripting>? = null
	@JvmStatic internal val scripts = HashMap<String, Script>()
	@JvmStatic internal val stack = ThreadLocal.withInitial<LinkedList<ScriptImport>> { LinkedList() }
	@JvmStatic internal val builds = ArrayList<IncludedBuild>()
	@JvmStatic internal val injectScripts = ArrayList<GroovyKotlinCache<*>>()

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(Scripting)
		groovyKotlinCaches += cache!!
		addOnConfigStarted(0) {
			for(cache in groovyKotlinCaches)
				addInjectScript(cache)
		}
		addOnConfigFinished(0) {
			for(cache in groovyKotlinCaches)
				removeInjectScript(cache)
		}
		addOnConfigFinished(1) {
			for(script in scripts.values) {
				val imports = script.imports.toTypedArray()
				for(scriptImport in imports)
					scriptUnimport(script.id, scriptImport.id)
			}
		}
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
		scripts.clear()
		purgeThreadLocal(stack)
		builds.clear()
	}

	@ExportGradle @JvmStatic
	fun getScript(id: String): Script? {
		return scripts[id]
	}
	@ExportGradle @JvmStatic
	fun getScript(info: ScriptImport): Script? {
		return getScript(info.scriptId)
	}
	@ExportGradle @JvmStatic
	fun getScript(file: File): Script? {
		return getScript(getScriptId(file))
	}
	@ExportGradle @JvmStatic
	fun getScriptId(file: File): String {
		return file.canonicalPath
	}
	@ExportGradle @JvmStatic
	fun __getLastImport(): ScriptImport? {
		val stack = stack.get()
		return if(stack.size > 0) stack.last else null
	}
	@ExportGradle @JvmStatic
	fun __getLastImportScript(): Script? {
		val lastImport = __getLastImport()
		return if(lastImport != null) getScript(lastImport) else null
	}

	@JvmStatic
	fun __checkBuild(build: IncludedBuild?) {
		if(build == null || builds.contains(build)) return
		val context = Context(build, asBuildProject0(build))
		for(injectScript in injectScripts)
			__addInjectScript(context, injectScript)
		builds += build
	}
	@JvmStatic
	fun __getScriptFile(scriptObj: Any): Pair<IncludedBuild?, File> {
		val context = lastContext()
		val (that, _, project) = context
		val lastImportScript = __getLastImportScript()
		val build: IncludedBuild?
		val scriptFile: File

		if(scriptObj is String && scriptObj.contains(':')) {
			val split = scriptObj.indexOf(':')
			val buildName = scriptObj.substring(0, split)
			val path = scriptObj.substring(split + 1)
			build = project.gradle.includedBuild(buildName)
			scriptFile = File(build.projectDir, path)
		} else if(lastImportScript != null) {
			build = lastImportScript.build
			scriptFile = if(scriptObj is String && scriptObj.startsWith('/')) File(if(build != null) build.projectDir else project.rootDir, scriptObj)
				else BaseDirFileResolver(lastImportScript.file.parentFile).resolve(scriptObj)
		} else if(scriptObj is String && scriptObj.startsWith('/')) {
			build = null
			scriptFile = File(project.rootDir, scriptObj)
		} else if(scriptObj is File) {
			build = null
			scriptFile = scriptObj
		} else {
			build = null
			scriptFile = (that as org.gradle.api.Script).file(scriptObj)
		}
		__checkBuild(build)
		return Pair(build, scriptFile)
	}

	/**
	 * - `scriptImport from('test.gradle')` only run script without exporting
	 * - `scriptImport from('test.gradle'), with(includeFlags('expose_exports'))` expose all exports as global variable
	 * - `scriptImport listOf('testMethod'), from('test.gradle')` export only `testMethod` as global variable
	 * - `scriptImport from('test.gradle'), being('test')` export all exports wrapped in `test` global variable
	 * - `scriptImport listOf('testMethod'), from('test.gradle'), being('test')` export only `testMethod` wrapped in `test` global variable
	 * - `scriptImport listOf('testMethod'), from('test.gradle'), being('test'), with(includeFlags('expose_exports'))` export only `testMethod` as global variable and wrapped in `test` global variable
	 */
	@JvmStatic
	fun __applyImport(scriptImport: ScriptImport, script: Script) {
		val context = scriptImport.context
		val project = context.project0
		val imported = scriptImport.imported
		val properties = HashMap<String, ExportWithPropertyCallback>()
		val methods = HashMap<String, ExportWithMethodCallback>()

		scriptImport.what?.forEach { if(script.exports.find { e -> e.being == it } == null)
			throw IllegalArgumentException("There's no such export '$it' from ${script.file}") }
		for(export in script.exports) {
			if(scriptImport.what != null && !scriptImport.what.contains(export.being))
				continue
			export.with.forEach { it(export, properties, methods) }
		}
		val builtInCache = prepareGroovyKotlinCache(imported)
		imported.cache = prepareGroovyKotlinCache(scriptImport, properties, methods)
		imported.cache!!.pushed += builtInCache.pushed
		imported.__start__()
		attachAnyObject(imported, imported.cache!!)
		imported.__end__()
		if((scriptImport.what != null && scriptImport.being == null) || containsFlag("expose_exports", scriptImport))
			attachObject(context, imported.cache!!)
		if(scriptImport.being != null)
			project.extensions.extraProperties.set(scriptImport.being, imported)
	}
	@JvmStatic
	fun __unapplyImport(scriptImport: ScriptImport, script: Script) {
		val context = scriptImport.context
		val project = context.project0
		val imported = scriptImport.imported

		if(scriptImport.being != null)
			extraPropertiesRemoveKey(scriptImport.being, project.extensions.extraProperties)
		if((scriptImport.what != null && scriptImport.being == null) || containsFlag("expose_exports", scriptImport))
			detachObject(context, imported.cache!!)
		detachAnyObject(imported, imported.cache!!)
		imported.__clear__()
		imported.cache = null
	}
	@JvmStatic
	fun __addInjectScript(context: Context, cache: GroovyKotlinCache<*>) {
		val project = context.project0
		val source = File(project.rootDir, "std$${cache.ownerJavaClass.simpleName}")
		val (build, scriptFile) = __getScriptFile(source)
		val scriptId = getScriptId(scriptFile)
		val script = scripts.computeIfAbsent(scriptId) { Script(build, scriptFile, scriptId) }

		if(script.file.canonicalPath != scriptFile.path)
			throw IllegalStateException("Duplicate gradle script id \"$scriptId\", another " +
					"import is from \"${script.file.canonicalPath}\"")
		script.context = context
		for(pushed in cache.pushed.values) {
			if(pushed is GroovyKotlinCache.InjectedProperty) {
				val names = pushed.implNames()
				for(name in names) {
					val export = ScriptExport(script.id, pushed, name, ArrayList())
					script.exports += export
					export.with += { exportInfo, properties, _ ->
						val getter = if(pushed.getter != null) closureToLambda1(pushed.getter) else null
						val setter = if(pushed.setter != null) closureToLambda2(pushed.setter as Closure<Unit>) else null
						properties[exportInfo.being] = Pair(getter, setter)
					}
				}
			}
			if(pushed is GroovyKotlinCache.InjectedMethod) {
				val names = pushed.implNames()
				for(name in names) {
					val export = ScriptExport(script.id, pushed, name, ArrayList())
					script.exports += export
					export.with += { exportInfo, _, methods ->
						val callback = if(pushed.callback != null) { self: Any?, args: Array<out Any?> -> pushed.callback.call(self, *args) } else null
						methods[exportInfo.being] = callback
					}
				}
			}
		}
	}
	@JvmStatic
	fun __removeInjectScript(context: Context, cache: GroovyKotlinCache<*>) {
		val project = context.project0
		val source = File(project.rootDir, "std$${cache.ownerJavaClass.simpleName}")
		val (_, scriptFile) = __getScriptFile(source)
		val scriptId = getScriptId(scriptFile)
		scripts.remove(scriptId)
	}

	@JvmStatic
	fun addInjectScript(cache: GroovyKotlinCache<*>) {
		if(currentSession == null) return
		injectScripts += cache
		__addInjectScript(currentSession!!.context, cache)
	}
	@JvmStatic
	fun removeInjectScript(cache: GroovyKotlinCache<*>) {
		if(currentSession == null) return
		__removeInjectScript(currentSession!!.context, cache)
		injectScripts -= cache
	}

	/**
	 * Import script. If the script is already imported before, then it will only extract [Script.exports].
	 * If not, it loads the script, calls the [Script.construct], and extracts the exported list.
	 *
	 * Example:
	 * - `require('test.gradle')` loads `test.gradle` from current path
	 * - `require('/test.gradle')` loads `test.gradle` from project root
	 * - `require('Test:test.gradle')` or `require('Test:/test.gradle')` loads `test.gradle` from project root build `Test`
	 * - `require('Test:test.gradle')` and `test.gradle` contains `require('test2.gradle')`, loads `test.gradle` from project
	 *   root build `Test`, then loads `test2.gradle` from the same build too.
	 */
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(what: List<String>?, from: Any, being: String? = null, with: List<ImportWith> = listOf()): Imported {
		val context = lastContext()
		val project = context.project0
		val stack = stack.get()

		val (build, scriptFile) = __getScriptFile(from)
		val scriptId = getScriptId(scriptFile)
		val script = scripts.computeIfAbsent(scriptId) { Script(build, scriptFile, scriptId) }
		val scriptImportId = "$scriptId#${randomString()}"
		val scriptImport = ScriptImport(scriptImportId, context, scriptId, what, being, ArrayList(with))
		scriptImport.imported = Imported(script.id, scriptImport.id)
		if(script.file.canonicalPath != scriptFile.path)
			throw IllegalStateException("Duplicate gradle script id \"$scriptId\", another " +
					"import is from \"${script.file.canonicalPath}\"")

		val preAction: () -> Unit = preAction@{
			for(j in with.indices step 2)
				with[j](scriptImport)
			script.imports.add(scriptImport)
		}
		val postAction: () -> Unit = postAction@{
			if(script.context == null)
				throw IllegalStateException("Imported script does not call scriptApply()")
			__applyImport(scriptImport, script)
			for(j in with.size - 1 downTo 0 step 2)
				with[j](scriptImport)
		}
		val catchCheck: (Throwable) -> Throwable? = catchCheck@{ e ->
			script.imports.remove(scriptImport)
			return@catchCheck e
		}

		try {
			stack.addLast(scriptImport)
			preAction()
			if(script.context == null)
				project.apply { it.from(project.relativePath(scriptFile)) }
			if(script.imports.size == 1)
				script.construct?.invoke()
			script.importAction?.invoke(scriptImport)
			postAction()
		} catch(e: Throwable) {
			val e0 = catchCheck(e)
			if(e0 != null) throw e0
		} finally {
			val lastStack = stack.removeLast()
			if(scriptImport != lastStack)
				__must_not_happen()
		}
		return scriptImport.imported
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(from: Any, being: String? = null, with: List<ImportWith> = listOf()): Imported {
		return scriptImport(null as List<String>?, from, being, with)
	}
	@ExportGradle @JvmStatic
	fun scriptImport(from: Any, with: List<ImportWith>): Imported {
		return scriptImport(null as List<String>?, from, null, with)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(what: List<String>?, from: KeywordsUtils.From<Any>, being: KeywordsUtils.Being<String?> = being(null),
					 with: KeywordsUtils.With<List<ImportWith>> = with(listOf())): Imported {
		return scriptImport(what, from.user, being.user, with.user)
	}
	@ExportGradle @JvmStatic
	fun scriptImport(what: List<String>?, from: KeywordsUtils.From<Any>, with: KeywordsUtils.With<List<ImportWith>>): Imported {
		return scriptImport(what, from.user, null, with.user)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptImport(from: KeywordsUtils.From<Any>, being: KeywordsUtils.Being<String?> = being(null),
					 with: KeywordsUtils.With<List<ImportWith>> = with(listOf())): Imported {
		return scriptImport(null as List<String>?, from.user, being.user, with.user)
	}
	@ExportGradle @JvmStatic
	fun scriptImport(from: KeywordsUtils.From<Any>, with: KeywordsUtils.With<List<ImportWith>>): Imported {
		return scriptImport(null as List<String>?, from.user, null, with.user)
	}
	@ExportGradle @JvmStatic
	fun scriptUnimport(scriptId: String, importId: String) {
		val stack = stack.get()
		val script = scripts[scriptId]!!
		val scriptImport = script.imports.first { it.id == importId }
		val with = scriptImport.with
		if(script.context == null)
			throw IllegalStateException("Unimported script does not call scriptApply()")

		val preAction: () -> Unit = preAction@{
			for(j in with.indices step 2)
				with[j](scriptImport)
			__unapplyImport(scriptImport, script)
		}
		val postAction: () -> Unit = postAction@{
			script.imports.remove(scriptImport)
			for(j in with.size - 1 downTo 0 step 2)
				with[j](scriptImport)
		}
		val catchCheck: (Throwable) -> Throwable? = catchCheck@{ e ->
			script.imports.add(scriptImport)
			return@catchCheck e
		}

		try {
			stack.addLast(scriptImport)
			preAction()
			script.unimportAction?.invoke(scriptImport)
			if(script.imports.size == 1)
				script.destruct?.invoke()
			postAction()
		} catch(e: Throwable) {
			val e0 = catchCheck(e)
			if(e0 != null) throw e0
		} finally {
			val lastStack = stack.removeLast()
			if(scriptImport != lastStack)
				__must_not_happen()
		}
	}
	@ExportGradle @JvmStatic
	fun scriptUnimport(scriptImport: ScriptImport) {
		scriptUnimport(scriptImport.scriptId, scriptImport.id)
	}
	@ExportGradle @JvmStatic
	fun scriptUnimport(imported: Imported) {
		scriptUnimport(imported.scriptImport)
	}

	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: String, with: List<ExportWith> = listOf()): ScriptExport {
		val lastImportScript = __getLastImportScript()!!
		val scriptExport = ScriptExport(lastImportScript.id, what, being, ArrayList(with))
		lastImportScript.exports += scriptExport
		lastImportScript.exportAction?.invoke(scriptExport)
		return scriptExport
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun scriptExport(what: Any?, being: KeywordsUtils.Being<String>, with: KeywordsUtils.With<List<ExportWith>> = with(exportProperty())): ScriptExport {
		return scriptExport(what, being.user, with.user)
	}
	@ExportGradle @JvmStatic
	fun scriptUnexport(being: String) {
		val lastImportScript = __getLastImportScript()!!
		val scriptExport = lastImportScript.exports.first { it.being == being }
		lastImportScript.unexportAction?.invoke(scriptExport)
		lastImportScript.exports -= scriptExport
	}
	@ExportGradle @JvmStatic
	fun scriptUnexport(scriptExport: ScriptExport) {
		scriptUnexport(scriptExport.being)
	}

	@ExportGradle @JvmStatic
	fun scriptApply() {
		val lastImportScript = __getLastImportScript()!!
		val context = lastContext()
		lastImportScript.context = context
		for(cache in groovyKotlinCaches)
			attachObject(context, cache)
	}
	@ExportGradle @JvmStatic
	fun scriptConstruct(callback: () -> Unit) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.construct = callback
	}
	@ExportGradle @JvmStatic
	fun scriptDestruct(callback: () -> Unit) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.destruct = callback
	}
	@ExportGradle @JvmStatic
	fun scriptImportAction(action: ImportAction?) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.importAction = action
	}
	@ExportGradle @JvmStatic
	fun scriptUnimportAction(action: UnimportAction?) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.unimportAction = action
	}
	@ExportGradle @JvmStatic
	fun scriptExportAction(action: ExportAction?) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.exportAction = action
	}
	@ExportGradle @JvmStatic
	fun scriptUnexportAction(action: UnexportAction?) {
		val lastImportScript = __getLastImportScript()!!
		lastImportScript.unexportAction = action
	}

	@ExportGradle @JvmStatic
	fun includeFlags(vararg flags: String): List<ImportWith> {
		return listOf(
			{ scriptImport -> for(flag in flags) scriptImport.context.project0.extensions.extraProperties.set("${scriptImport.id}_${flag}", true) },
			{ scriptImport -> for(flag in flags) extraPropertiesRemoveKey("${scriptImport.id}_${flag}", scriptImport.context.project0.extensions.extraProperties) }
		)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun containsFlag(flag: String, scriptImport: ScriptImport? = __getLastImport()): Boolean {
		val context = scriptImport?.context ?: lastContext()
		val project = context.project0
		val ext = project.extensions.extraProperties
		return if(scriptImport == null) ext.has(flag)
		else ext.has("${scriptImport.id}_${flag}")
	}

	@ExportGradle @JvmStatic
	fun exportMethod(): List<ExportWith> {
		return listOf { exportInfo, _, methods ->
			val callback = { self: Any?, args: Array<out Any?> ->
				val callback = exportInfo.what
				if(callback is Closure<*>)
					callback.call(*args)
				else __invalid_type()
			}
			methods[exportInfo.being] = callback
		}
	}
	fun exportProperty(allowSetter: Boolean = false, allowGetter: Boolean = true): List<ExportWith> {
		return listOf { exportInfo, properties, _ ->
			val getter = if(allowGetter) { self: Any? -> exportInfo.what } else null
			val setter = if(allowSetter) { self: Any?, value: Any? -> exportInfo.what = value } else null
			properties[exportInfo.being] = Pair(getter, setter)
		}
	}

	@ExportGradle @JvmStatic
	fun withCurrentImport(): Closure<*> {
		val lastImport = __getLastImport()!!
		return lambda1ToClosure({ closure: Closure<*> ->
			val stack = stack.get()
			stack.addLast(lastImport)
			try {
				closure.call()
			} finally {
				val lastStack = stack.removeLast()
				if(lastImport != lastStack)
					__must_not_happen()
			}
		}, "scriptState (${lastImport.id})")
	}
}
