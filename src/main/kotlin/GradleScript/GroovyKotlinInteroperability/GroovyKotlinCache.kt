package GradleScript.GroovyKotlinInteroperability

import kotlin.reflect.KClass

class GroovyKotlinCache<T: Any>(
	val owner: T?,
	val ownerKotlinClass: KClass<out T>,
	val ownerJavaClass: Class<out T>
) {
	val pushed = HashMap<String, Injected>()

	abstract class Injected(
		val names: MutableSet<String>
	) {
		abstract fun implNames(): Set<String>
	}
	abstract class InjectedProperty(
		names: MutableSet<String>,
		val getter: KotlinClosure?,
		val setter: KotlinClosure?
	): Injected(names)
	abstract class InjectedMethod(
		names: MutableSet<String>,
		val callback: KotlinClosure?
	): Injected(names)

	open class InjectedMethodAsMethod(
		names: MutableSet<String>,
		callback: KotlinClosure?
	): InjectedMethod(names, callback) {
		override fun implNames(): Set<String> {
			return names.toSet()
		}
	}
	open class InjectedMethodAsProperty(
		names: MutableSet<String>,
		callback: KotlinClosure?,
		val methodCallback: KotlinClosure?
	): InjectedProperty(names, callback, null) {
		override fun implNames(): Set<String> {
			return names.toSet()
		}
	}
	open class InjectedPropertyAsProperty(
		names: MutableSet<String>,
		getter: KotlinClosure?,
		setter: KotlinClosure?
	): InjectedProperty(names, getter, setter) {
		override fun implNames(): Set<String> {
			return names.toSet()
		}
	}
	open class InjectedPropertyAsMethodGetter(
		names: MutableSet<String>,
		callback: KotlinClosure?
	): InjectedMethod(names, callback) {
		override fun implNames(): Set<String> {
			val type = callback?.getReturnType() ?: Any::class.java
			val boolean = type == Boolean::class.java || type == Boolean::class.javaPrimitiveType
			return names.map { n -> "${if(boolean) "is" else "get"}${n.replaceFirstChar { c -> c.uppercase() }}" }.toSet()
		}
	}
	open class InjectedPropertyAsMethodSetter(
		names: MutableSet<String>,
		callback: KotlinClosure?
	): InjectedMethod(names, callback) {
		override fun implNames(): Set<String> {
			return names.map { n -> "set${n.replaceFirstChar { c -> c.uppercase() }}" }.toSet()
		}
	}
}
