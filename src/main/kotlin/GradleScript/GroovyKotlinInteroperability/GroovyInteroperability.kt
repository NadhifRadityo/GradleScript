package GradleScript.GroovyKotlinInteroperability

import GradleScript.Common.addOnConfigFinished
import GradleScript.Common.groovyKotlinCaches
import GradleScript.Context
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda1
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda2
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.deleteMetaMethod0
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.deleteMetaProperty0
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda0ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda1ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda2ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.setMetaMethod0
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.setMetaProperty0
import GradleScript.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils.flattenVararg
import GradleScript.Strategies.ClassUtils.metaClassFor
import GradleScript.Strategies.GradleUtils.extraPropertiesRemoveKey
import groovy.lang.Closure
import groovy.lang.MetaClassImpl
import org.codehaus.groovy.reflection.ReflectionCache.getCachedClass
import org.gradle.api.Project
import java.lang.reflect.Modifier
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties

object GroovyInteroperability {
	@JvmStatic private var cache: GroovyKotlinCache<GroovyInteroperability>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(GroovyInteroperability)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	@JvmStatic
	fun <T: Any> prepareGroovyKotlinCache(obj: T): GroovyKotlinCache<T> {
		val kclass = obj::class
		val jclass = obj::class.java
		val cache = GroovyKotlinCache(obj, kclass, jclass)

		/**
		 * Groovy Class Case
		 *
		 * Allow clashing between InjectedMethodAsMethod and InjectedPropertyAsMethod(Getter|Setter)
		 * Example:
		 * fun test(): Boolean => Will resolve as `test(): Boolean`
		 * val test: Boolean => Will resolve as `isTest(): Boolean` and `setTest(Boolean)`
		 *
		 * Don't allow clashing between (same name) InjectedMethodAsMethod and InjectedPropertyAsMethod(Getter|Setter)
		 * Example:
		 * fun isTest(): Boolean => Will resolve as `isTest(): Boolean`
		 * val test: Boolean => Will resolve as `isTest(): Boolean` and `setTest(Boolean)`
		 * Error: Ambiguous overload call `isTest`
		 *
		 * Allow clashing between InjectedMethodAsMethod and InjectedPropertyAsProperty
		 * Example:
		 * fun test(): Int => Will resolve as `test(): Int`
		 * val test: Int => Will resolve as `val test: Int`
		 *
		 * Allow clashing between InjectedMethodAsProperty and InjectedPropertyAsMethod(Getter|Setter)
		 * Example:
		 * fun test(): Any => Will resolve as `val test: () -> Any`
		 * val test: Any => Will resolve as `getTest(): Any` and `setTest(Any)`
		 *
		 * Don't allow clashing between InjectedMethodAsProperty and InjectedPropertyAsProperty
		 * Example:
		 * fun test(): Any => Will resolve as `val test: () -> Any`
		 * val test: Int => Will resolve as `val test: Int`
		 * Error: Same signature property returns two different type
		 */

		/**
		 * Extra Properties Case (Deprecated)
		 * Warning: All property are converted to getter and setter callback
		 *
		 * Allow clashing between InjectedMethodAsMethod and InjectedPropertyAsMethod(Getter|Setter)
		 * Example:
		 * fun test(): Boolean => Will resolve as `val test: () -> Boolean`
		 * val test: Boolean => Will resolve as `val isTest: () -> Boolean` and `val setTest: (Boolean) -> Unit`
		 *
		 * Allow clashing between InjectedMethodAsMethod and InjectedPropertyAsProperty
		 * Example:
		 * fun test(): Int => Will resolve as `val test: () -> Int`
		 * val test: Int => Will resolve as `val getTest: () -> Int` and `val setTest: (Int) -> Unit`
		 *
		 * Don't allow clashing between InjectedMethodAsProperty and Non-Bool InjectedPropertyAsMethod(Getter|Setter)
		 * Example:
		 * fun test(): Any => Will resolve as `val getTest: () -> (() -> Any)`
		 * val test: Any => Will resolve as `val getTest: () -> Any` and `val setTest: (Any) -> Unit`
		 * Error: Same signature property returns two different type
		 *
		 * Don't allow clashing between InjectedMethodAsProperty and Non-Bool InjectedPropertyAsProperty
		 * Example:
		 * fun test(): Any => Will resolve as `val getTest: () -> (() -> Any)`
		 * val test: Int => Will resolve as `val getTest: () -> Int` and `val setTest: (Int) -> Unit`
		 * Error: Same signature property returns two different type
		 *
		 * Allow clashing between InjectedMethodAsProperty and Bool InjectedPropertyAsMethod(Getter|Setter)
		 * Example:
		 * fun test(): Any => Will resolve as `val getTest: () -> (() -> Any)`
		 * val test: Boolean => Will resolve as `val isTest: () -> Boolean` and `val setTest: (Boolean) -> Unit`
		 * Warning: Coincidence the property will be renamed to `isTest`
		 *
		 * Allow clashing between InjectedMethodAsProperty and Bool InjectedPropertyAsProperty
		 * Example:
		 * fun test(): Any => Will resolve as `val getTest: () -> (() -> Any)`
		 * val test: Boolean => Will resolve as `val isTest: () -> Boolean` and `val setTest: (Boolean) -> Unit`
		 * Warning: Coincidence the property will be renamed to `isTest`
		 */
		for(function in kclass.functions) {
			val qualifiedName = kclass.qualifiedName!!
			val functionName = function.name
			val annotation = function.findAnnotation<ExportGradle>()
//			val asProperty = annotation == null || annotation.asMode == 0 || annotation.asMode == 1
//			val asMethod = annotation != null && annotation.asMode == 2
			// TODO: Test method as default
			val asProperty = annotation != null && annotation.asMode == 1
			val asMethod = annotation == null || annotation.asMode == 0 || annotation.asMode == 2

			if(asMethod) {
				val id = "${qualifiedName}.${functionName}.method"
				val injected0 = cache.pushed[id]
				if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedMethodAsMethod)
					throw Error("Id $id is defined but not as an InjectedMethodAsMethod")
				var injected = injected0 as? GroovyKotlinCache.InjectedMethodAsMethod
				if(injected == null) {
					val names = mutableSetOf("__INTERNAL_${qualifiedName.replace(".", "$")}_${functionName}")
					val closure = KotlinClosure(functionName)
					injected = GroovyKotlinCache.InjectedMethodAsMethod(names, closure)
					cache.pushed[id] = injected
				}
				if(annotation != null)
					if(annotation.names.isEmpty()) injected.names += functionName
					else injected.names += annotation.names
				val originalOverloads = KotlinClosure.getKFunctionOverloads(arrayOf(obj), function)
				injected.callback!!.overloads += originalOverloads
				if(annotation == null || annotation.additionalOverloads == 1)
					injected.callback!!.overloads += originalOverloads.map { KotlinClosure.IgnoreSelfOverload(Any::class.java, it) }
				if(annotation != null && annotation.additionalOverloads == 2)
					injected.callback!!.overloads += originalOverloads.map { KotlinClosure.WithSelfOverload(null, it) }
			}
			if(asProperty) {
				val id = "${qualifiedName}.${functionName}"
				val injected0 = cache.pushed[id]
				if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedMethodAsProperty)
					throw Error("Id $id is defined but not as an InjectedMethodAsProperty")
				var injected = injected0 as? GroovyKotlinCache.InjectedMethodAsProperty
				if(injected == null) {
					val names = mutableSetOf("__INTERNAL_${qualifiedName.replace(".", "$")}_${functionName}")
					val closure = KotlinClosure(functionName)
					val methodClosure = KotlinClosure(functionName)
					closure.overloads += lambda0ToOverload { methodClosure }
					closure.overloads += lambda1ToOverload { self: Any? -> methodClosure } // Should I bind the function here?
					injected = GroovyKotlinCache.InjectedMethodAsProperty(names, closure, methodClosure)
					cache.pushed[id] = injected
				}
				if(annotation != null)
					if(annotation.names.isEmpty()) injected.names += functionName
					else injected.names += annotation.names
				val originalOverloads = KotlinClosure.getKFunctionOverloads(arrayOf(obj), function)
				injected.methodCallback!!.overloads += originalOverloads
				if(annotation == null || annotation.additionalOverloads == 1)
					injected.methodCallback!!.overloads += originalOverloads.map { KotlinClosure.IgnoreSelfOverload(Any::class.java, it) }
				if(annotation != null && annotation.additionalOverloads == 2)
					injected.methodCallback!!.overloads += originalOverloads.map { KotlinClosure.WithSelfOverload(null, it) }
			}
		}
		for(member in kclass.memberProperties) {
			val qualifiedName = kclass.qualifiedName!!
			val memberName = member.name
			val annotation = member.findAnnotation<ExportGradle>()
			val asProperty = annotation == null || annotation.asMode == 0 || annotation.asMode == 1
			val asMethod = annotation != null && annotation.asMode == 2
			val allowGet = annotation == null || annotation.allowGet
			val allowSet = member is KMutableProperty1<*, *> && annotation != null && annotation.allowSet

			if(asProperty) {
				val id = "${qualifiedName}.${memberName}"
				val injected0 = cache.pushed[id]
				if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedPropertyAsProperty)
					throw Error("Id $id is defined but not as an InjectedPropertyAsProperty")
				var injected = injected0 as? GroovyKotlinCache.InjectedPropertyAsProperty
				if(injected == null) {
					val names = mutableSetOf("__INTERNAL_${qualifiedName.replace(".", "$")}_${memberName}")
					val getter = if(allowGet) KotlinClosure(memberName) else null
					val setter = if(allowSet) KotlinClosure(memberName) else null
					injected = GroovyKotlinCache.InjectedPropertyAsProperty(names, getter, setter)
					cache.pushed[id] = injected
				}
				if(annotation != null)
					if(annotation.names.isEmpty()) injected.names += memberName
					else injected.names += annotation.names
				if(allowGet) {
					val originalOverload = KotlinClosure.KProperty1Overload(obj, member as KProperty1<T, *>)
					injected.getter!!.overloads += originalOverload
					if(annotation == null || annotation.additionalOverloads == 1)
						injected.getter!!.overloads += KotlinClosure.IgnoreSelfOverload(Any::class.java, originalOverload)
					if(annotation != null && annotation.additionalOverloads == 2)
						injected.getter!!.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
				}
				if(allowSet) {
					val originalOverload = KotlinClosure.KMutableProperty1Overload(obj, member as KMutableProperty1<T, *>)
					injected.setter!!.overloads += originalOverload
					if(annotation == null || annotation.additionalOverloads == 1)
						injected.setter!!.overloads += KotlinClosure.IgnoreSelfOverload(Any::class.java, originalOverload)
					if(annotation != null && annotation.additionalOverloads == 2)
						injected.setter!!.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
				}
			}
			if(asMethod) {
				if(allowGet) {
					val id = "${qualifiedName}.${memberName}.get"
					val injected0 = cache.pushed[id]
					if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedPropertyAsMethodGetter)
						throw Error("Id $id is defined but not as an InjectedPropertyAsMethodGetter")
					var injected = injected0 as? GroovyKotlinCache.InjectedPropertyAsMethodGetter
					if(injected == null) {
						val names = mutableSetOf("__INTERNAL_${qualifiedName.replace(".", "$")}_${memberName}")
						val callback = if(allowGet) KotlinClosure(memberName) else null
						injected = GroovyKotlinCache.InjectedPropertyAsMethodGetter(names, callback)
						cache.pushed[id] = injected
					}
					if(annotation != null)
						if(annotation.names.isEmpty()) injected.names += memberName
						else injected.names += annotation.names
					val originalOverload = KotlinClosure.KProperty1Overload(obj, member as KProperty1<T, *>)
					injected.callback!!.overloads += originalOverload
					if(annotation == null || annotation.additionalOverloads == 1)
						injected.callback!!.overloads += KotlinClosure.IgnoreSelfOverload(Any::class.java, originalOverload)
					if(annotation != null && annotation.additionalOverloads == 2)
						injected.callback!!.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
				}
				if(allowGet) {
					val id = "${qualifiedName}.${memberName}.set"
					val injected0 = cache.pushed[id]
					if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedPropertyAsMethodSetter)
						throw Error("Id $id is defined but not as an InjectedPropertyAsMethodSetter")
					var injected = injected0 as? GroovyKotlinCache.InjectedPropertyAsMethodSetter
					if(injected == null) {
						val names = mutableSetOf("__INTERNAL_${qualifiedName.replace(".", "$")}_${memberName}")
						val callback = if(allowGet) KotlinClosure(memberName) else null
						injected = GroovyKotlinCache.InjectedPropertyAsMethodSetter(names, callback)
						cache.pushed[id] = injected
					}
					if(annotation != null)
						if(annotation.names.isEmpty()) injected.names += memberName
						else injected.names += annotation.names
					val originalOverload = KotlinClosure.KMutableProperty1Overload(obj, member as KMutableProperty1<T, *>)
					injected.callback!!.overloads += originalOverload
					if(annotation == null || annotation.additionalOverloads == 1)
						injected.callback!!.overloads += KotlinClosure.IgnoreSelfOverload(Any::class.java, originalOverload)
					if(annotation != null && annotation.additionalOverloads == 2)
						injected.callback!!.overloads += KotlinClosure.WithSelfOverload(null, originalOverload)
				}
			}
		}
		return cache
	}
	@JvmStatic
	fun prepareGroovyKotlinCache(obj: Any, properties: Map<String, Pair<((Any?) -> Any?)?, ((Any?, Any?) -> Unit)?>>, methods: Map<String, ((Any?, Array<out Any?>) -> Any?)?>): GroovyKotlinCache<*> {
		val kclass = obj::class
		val jclass = obj::class.java
		val cache = GroovyKotlinCache(obj, kclass, jclass)
		for(entry in methods) {
			val id = entry.key
			val callback = entry.value
			val injected0 = cache.pushed[id]
			if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedMethod)
				throw Error("Id $id is defined but not as an InjectedMethod")
			var injected = injected0 as? GroovyKotlinCache.InjectedMethod
			if(injected == null) {
				val names = mutableSetOf(entry.key)
				val closure = KotlinClosure(id)
				injected = GroovyKotlinCache.InjectedMethodAsMethod(names, closure)
				cache.pushed[id] = injected
			}
			injected.names += entry.key
			if(callback != null) {
				val originalOverload = KotlinClosure.KLambdaOverload { callback(it[0], it.copyOfRange(1, it.size)) }
				injected.callback!!.overloads += originalOverload
			}
		}
		for(entry in properties) {
			val id = entry.key
			val callback = entry.value
			val injected0 = cache.pushed[id]
			if(injected0 != null && injected0 !is GroovyKotlinCache.InjectedProperty)
				throw Error("Id $id is defined but not as an InjectedProperty")
			var injected = injected0 as? GroovyKotlinCache.InjectedProperty
			if(injected == null) {
				val names = mutableSetOf(entry.key)
				val getter = KotlinClosure(id)
				val setter = KotlinClosure(id)
				injected = GroovyKotlinCache.InjectedPropertyAsProperty(names, getter, setter)
				cache.pushed[id] = injected
			}
			injected.names += entry.key
			if(callback.first != null) {
				val originalOverload = lambda1ToOverload(callback.first!!)
				injected.getter!!.overloads += originalOverload
			}
			if(callback.second != null) {
				val originalOverload = lambda2ToOverload(callback.second!!)
				injected.setter!!.overloads += originalOverload
			}
		}
		return cache
	}

	@ExportGradle @JvmStatic
	fun attachObject(context: Context, cache: GroovyKotlinCache<*>) {
		// Mostly, any property getter will use context.that
		// As shown in BasicScript$ScriptDynamicObject.tryGetProperty
		val (that, _, project) = context
		val klass = that::class.java
		val METHOD_BuildScript_target = klass.declaredMethods.firstOrNull { m -> m.name == "getScriptTarget" && m.parameterCount == 0 }
		if(METHOD_BuildScript_target != null) {
			METHOD_BuildScript_target.isAccessible = true
			val target = METHOD_BuildScript_target.invoke(that)
			// But, the setter isn't going through the scriptObject
			// As shown in BasicScript$ScriptDynamicObject.trySetProperty
			// So we need to inject to script target. Call to script target
			// is shown in CompositeDynamicObject.trySetProperty
			attachAnyObject(target, cache)
		}
		attachAnyObject(that, cache)
//		attachProjectObject(project, cache)
	}
	@ExportGradle @JvmStatic
	fun attachAnyObject(that: Any, cache: GroovyKotlinCache<*>) {
		val metaClass = metaClassFor(that) as MetaClassImpl
		val declaringClass = metaClass.theCachedClass

		for(pushed in cache.pushed.values) {
			if(pushed is GroovyKotlinCache.InjectedProperty) {
				val modifiers = Modifier.PUBLIC or Modifier.STATIC
				val type = pushed.getter?.getReturnType() ?: Any::class.java
				val getter = if(pushed.getter != null) closureToLambda1(pushed.getter) else null
				val setter = if(pushed.setter != null) closureToLambda2(pushed.setter as Closure<Unit>) else null
				val names = pushed.implNames()
				for(name in names) {
					val metaProperty = GroovyManipulation.KotlinMetaProperty(modifiers, name, type, getter, setter)
					setMetaProperty0(metaClass, metaProperty, declaringClass)
				}
			}
			if(pushed is GroovyKotlinCache.InjectedMethod) {
				val modifiers = Modifier.PUBLIC or Modifier.STATIC
				val parameterTypes = (pushed.callback?.getParameterTypes() ?: arrayOf(Array<out Any?>::class.java)).map { getCachedClass(it) }.toTypedArray()
				val returnType = pushed.callback?.getReturnType() ?: Any::class.java
				val callback = if(pushed.callback != null) { self: Any?, args: Array<out Any?> -> pushed.callback.call(self, *flattenVararg(args)) } else null
				val names = pushed.implNames()
				for(name in names) {
					val metaMethod = GroovyManipulation.KotlinMetaMethod(modifiers, name, declaringClass, parameterTypes, returnType, callback)
					setMetaMethod0(metaClass, metaMethod)
				}
			}
		}
		addOnConfigFinished(0) { detachAnyObject(that, cache) }
	}
	@Deprecated("Leaky import")
	@ExportGradle @JvmStatic
	fun attachProjectObject(project: Project, cache: GroovyKotlinCache<*>) {
		val ext = project.extensions.extraProperties
		fun setExt(name: String, value: Any?) {
			if(ext.has(name))
				System.err.println("Redefining extra properties '${name}' with '$value'")
			if(value != null) ext.set(name, value)
			else extraPropertiesRemoveKey(name, ext)
		}
		cacheExtensionNames(cache,
			{ pushed, name -> setExt(name, pushed.getter) },
			{ pushed, name -> setExt(name, pushed.setter) },
			{ pushed, name -> setExt(name, pushed.callback) })
		addOnConfigFinished(0) { detachProjectObject(project, cache) }
	}
	fun cacheExtensionNames(cache: GroovyKotlinCache<*>, callbackPropertyGetter: (GroovyKotlinCache.InjectedProperty, String) -> Unit,
							callbackPropertySetter: (GroovyKotlinCache.InjectedProperty, String) -> Unit, callbackMethod: (GroovyKotlinCache.InjectedMethod, String) -> Unit) {
		for(pushed in cache.pushed.values) {
			if(pushed is GroovyKotlinCache.InjectedProperty) {
				val type = pushed.getter?.getReturnType() ?: Any::class.java
				val boolean = type == Boolean::class.java || type == Boolean::class.javaPrimitiveType
				val names = pushed.implNames()
				for(name in names) {
					callbackPropertyGetter(pushed, "${if(boolean) "is" else "get"}${name.replaceFirstChar { c -> c.uppercase() }}")
					callbackPropertySetter(pushed, "set${name.replaceFirstChar { c -> c.uppercase() }}")
				}
			}
			if(pushed is GroovyKotlinCache.InjectedMethod) {
				val names = pushed.implNames()
				for(name in names)
					callbackMethod(pushed, name)
			}
		}
	}
	@ExportGradle @JvmStatic
	fun detachObject(context: Context, cache: GroovyKotlinCache<*>) {
		val (that, _, project) = context
		detachAnyObject(that, cache)
//		detachProjectObject(project, cache)
	}
	@ExportGradle @JvmStatic
	fun detachAnyObject(that: Any, cache: GroovyKotlinCache<*>) {
		val metaClass = metaClassFor(that) as MetaClassImpl
		val declaringClass = metaClass.theCachedClass

		for(pushed in cache.pushed.values) {
			if(pushed is GroovyKotlinCache.InjectedProperty) {
				val names = pushed.implNames()
				for(name in names)
					deleteMetaProperty0(metaClass, name, declaringClass)
			}
			if(pushed is GroovyKotlinCache.InjectedMethod) {
				val parameterTypes = (pushed.callback?.getParameterTypes() ?: arrayOf(Array<out Any?>::class.java)).map { getCachedClass(it) }.toTypedArray()
				val names = pushed.implNames()
				for(name in names)
					deleteMetaMethod0(metaClass, name, declaringClass, parameterTypes)
			}
		}
	}
	@Deprecated("Leaky import")
	@ExportGradle @JvmStatic
	fun detachProjectObject(project: Project, cache: GroovyKotlinCache<*>) {
		val ext = project.extensions.extraProperties
		cacheExtensionNames(cache,
			{ pushed, name -> extraPropertiesRemoveKey(name, ext) },
			{ pushed, name -> extraPropertiesRemoveKey(name, ext) },
			{ pushed, name -> extraPropertiesRemoveKey(name, ext) })
	}
}
