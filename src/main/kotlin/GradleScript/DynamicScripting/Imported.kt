package GradleScript.DynamicScripting

import GradleScript.DynamicScripting.Scripting.scripts
import GradleScript.GroovyKotlinInteroperability.*
import GradleScript.Strategies.Utils.__invalid_type
import groovy.lang.*

open class Imported(
	@ExportGradle val scriptId: String,
	@ExportGradle val importId: String
): GroovyManipulation.DummyGroovyObject() {
	var cache: GroovyKotlinCache<*>? = null
		internal set
	internal var reverseCache: Map<String, GroovyKotlinCache.Injected>? = null
	@ExportGradle val script: Script
		get() { return scripts[scriptId]!! }
	@ExportGradle val scriptImport: ScriptImport
		get() { return script.imports.first { it.id == importId } }

	override fun __end__() {
		super.__end__()
		val reverse = mutableMapOf<String, GroovyKotlinCache.Injected>()
		for(pushed in cache!!.pushed.values) {
			val names = pushed.names
			for(name in names)
				reverse[name] = pushed
		}
		reverseCache = reverse
	}

	// https://kotlinlang.org/docs/destructuring-declarations.html
	internal fun parsePropertyName(property: String): String {
		val index = if(property.startsWith("component")) property.substring("component".length).toIntOrNull() else null
		return if(index == null) property else scriptImport.what?.getOrNull(index - 1) ?: property
	}
	internal fun getProperty0(name: String): Any? {
		return lookupProperty(metaClass, name)!!.getProperty(this)
	}
	internal fun setProperty0(name: String, value: Any?) {
		lookupProperty(metaClass, name)!!.setProperty(this, value)
	}
	internal val methodPropertyCache = mutableMapOf<String, KotlinClosure>()
	internal fun getMethodProperty(name: String, arguments: Array<out Class<*>>): Closure<*> {
		val id = "${name}[${arguments.joinToString(", ") { it.toString() }}]"
		var result = methodPropertyCache[name]
		if(result != null) return result
		val metaMethod = lookupMethod(metaClass, name, arguments)
		if(metaMethod == null) throw IllegalStateException("There's no such method named '${name}' " +
				"with arguments [${arguments.joinToString(", ") { it.toString() }}]")
		result = KotlinClosure("MethodProperty ${name}")
		result.overloads += object: KotlinClosure.Overload(metaMethod.parameterTypes.map { it.theClass }.toTypedArray(),
			metaMethod.returnType, { args -> metaMethod.invoke(null, args) }) {
			override fun toString(): String { return metaMethod.toString() }
		}
		methodPropertyCache[name] = result
		return result
	}

	override fun getProperty(property: String): Any? {
		return super.getProperty(parsePropertyName(property))
	}
	override fun setProperty(property: String, value: Any?) {
		super.setProperty(parsePropertyName(property), value)
	}
	@ExportGradle
	operator fun get(property: Any?): Any? {
		if(property is String) {
			val propertyName = parsePropertyName(property)
			val pushed = reverseCache!![propertyName]
			if(pushed is GroovyKotlinCache.InjectedMethod) return getMethodProperty(propertyName, arrayOf())
			if(pushed is GroovyKotlinCache.InjectedProperty) return getProperty0(propertyName)
			return super.getProperty(propertyName)
		}
		if(property is PropertySymbol) {
			val pushed = reverseCache!![property.name]
			if(pushed is GroovyKotlinCache.InjectedProperty) return getProperty0(property.name)
			return super.getProperty(property.name)
		}
		if(property is MethodSymbol) {
			val pushed = reverseCache!![property.name]
			if(pushed is GroovyKotlinCache.InjectedMethod) return getMethodProperty(property.name, property.parameterTypes)
			throw IllegalStateException("There's no such method named '${property.name}' " +
					"with arguments [${property.parameterTypes.joinToString(", ") { it.toString() }}]")
		}
		throw __invalid_type()
	}
	@ExportGradle
	operator fun set(property: Any?, value: Any?) {
		if(property is String) {
			val propertyName = parsePropertyName(property)
			val pushed = reverseCache!![propertyName]
			if(pushed is GroovyKotlinCache.InjectedMethod) throw IllegalStateException("Cannot set method property.")
			if(pushed is GroovyKotlinCache.InjectedProperty) return setProperty0(propertyName, value)
			return super.setProperty(propertyName, value)
		}
		if(property is PropertySymbol) {
			val pushed = reverseCache!![property.name]
			if(pushed is GroovyKotlinCache.InjectedProperty) return setProperty0(property.name, value)
			return super.setProperty(property.name, value)
		}
		if(property is MethodSymbol)
			throw IllegalStateException("Cannot set method property.")
		throw __invalid_type()
	}

	companion object {
		@JvmStatic val METHOD_MetaClassImpl_getMetaProperty = MetaClassImpl::class.java.getDeclaredMethod("getMetaProperty", String::class.java, Boolean::class.javaPrimitiveType)
		init {
			METHOD_MetaClassImpl_getMetaProperty.isAccessible = true
		}

		fun lookupProperty(metaClass: MetaClass, name: String): MetaProperty? {
			if(metaClass is MetaClassImpl)
				return METHOD_MetaClassImpl_getMetaProperty.invoke(metaClass, name, false) as MetaProperty
			return metaClass.getMetaProperty(name)
		}
		fun lookupMethod(metaClass: MetaClass, name: String, arguments: Array<out Class<*>>): MetaMethod? {
			return metaClass.pickMethod(name, arguments)
		}
	}
}
