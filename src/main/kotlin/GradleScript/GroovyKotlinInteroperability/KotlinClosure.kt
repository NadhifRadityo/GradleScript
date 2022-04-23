package GradleScript.GroovyKotlinInteroperability

import GradleScript.DynamicScripting.Scripting.addInjectScript
import GradleScript.DynamicScripting.Scripting.removeInjectScript
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.closureToLambda
import GradleScript.Strategies.ClassUtils.boxedToPrimitive
import GradleScript.Strategies.ClassUtils.boxedWideningConversion
import GradleScript.Strategies.ClassUtils.primitiveWideningConversions
import groovy.lang.Closure
import java.lang.reflect.Field
import java.lang.reflect.Method
import kotlin.reflect.*
import kotlin.reflect.jvm.jvmErasure

open class KotlinClosure(
	val name: String,
	vararg _overloads: Overload
): Closure<Any?>(this) {
	val overloads = arrayListOf(*_overloads)

	override fun call(vararg args: Any?): Any? {
		val matched = doOverloading(overloads, args)
		return matched.first.callback(matched.second)
	}
	override fun getParameterTypes(): Array<out Class<*>> {
		return parameterTypesClashing(overloads.map { it.parameterTypes }.toTypedArray())
	}
	fun getReturnType(): Class<*> {
		return typeClashing(overloads.map { it.returnType }.toTypedArray())
	}

	override fun getProperty(property: String): Any? {
		return when(property) {
			"name" -> name
			"overloads" -> overloads
			"parameterType" -> getParameterTypes()
			"returnType" -> getReturnType()
			else -> super.getProperty(property)
		}
	}
	override fun setProperty(property: String, value: Any?) {
		when(property) {
			"overloads" -> { overloads.clear(); overloads += (value as Collection<Overload>) }
			else -> super.setProperty(property, value)
		}
	}
	override fun toString(): String {
		val result = StringBuilder()
		result.append("$name[")
		for(overload in overloads) {
			result.append("(")
			for(parameterType in overload.parameterTypes)
				result.append(parameterType.toString()).append(", ")
			if(overload.parameterTypes.isNotEmpty())
				result.setLength(result.length - 2)
			result.append("), ")
		}
		if(overloads.isNotEmpty())
			result.setLength(result.length - 2)
		result.append("]")
		return result.toString()
	}

	open class Overload(
		val parameterTypes: Array<out Class<*>>,
		val returnType: Class<*>,
		val callback: (Array<out Any?>) -> Any?,
		var priority: Int = 0
	) {
		val parameterCount = parameterTypes.size
	}
	open class IgnoreSelfOverload(
		val selfClass: Class<*>,
		val overload: Overload
	): Overload(
		arrayOf(selfClass, *overload.parameterTypes),
		overload.returnType,
		{ overload.callback(it.copyOfRange(1, it.size)) },
		-1
	) {
		override fun toString(): String {
			return "^$overload"
		}
	}
	open class WithSelfOverload(
		val self: Any?,
		val overload: Overload
	): Overload(
		overload.parameterTypes.copyOfRange(1, overload.parameterTypes.size),
		overload.returnType,
		{ overload.callback(arrayOf(self, *it)) },
		-1
	) {
		override fun toString(): String {
			return "*$overload"
		}
	}
	open class MethodOverload(
		val owner: Any?,
		val method: Method
	): Overload(
		method.parameterTypes,
		method.returnType,
		{ method.invoke(owner, *it) }
	) {
		override fun toString(): String {
			return method.toString()
		}
	}
	open class FieldGetOverload(
		val owner: Any?,
		val field: Field
	): Overload(
		arrayOf(),
		field.type,
		{ field.get(owner) }
	) {
		override fun toString(): String {
			return "get${field.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class FieldSetOverload(
		val owner: Any?,
		val field: Field
	): Overload(
		arrayOf(field.type),
		Unit::class.java,
		{ field.set(owner, it[0]) }
	) {
		override fun toString(): String {
			return "set${field.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KLambdaOverload(
		val lambda: (Array<out Any?>) -> Any?
	): Overload(
		arrayOf(Array<Any?>::class.java),
		Any::class.java,
		{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args) }
	) {
		override fun toString(): String {
			return lambda.toString()
		}
	}
	open class KFunctionOverload<R>(
		val owners: Array<Any?>,
		val function: KFunction<R>,
		val kParameters: Array<KParameter>
	): Overload(
		kParameters.filter { it.kind != KParameter.Kind.INSTANCE }.map { it.type.jvmErasure.java }.toTypedArray(),
		function.returnType.jvmErasure.java,
		{
			val args = HashMap<KParameter, Any?>();
			var offset = 0
			kParameters.forEach { v ->
				if(v.kind != KParameter.Kind.INSTANCE) args[v] = it[v.index - offset]
				else args[v] = owners[offset++]
			}
			function.callBy(args)
		}
	) {
		override fun toString(): String {
			val stringBuilder = StringBuilder()
			with(stringBuilder) {
				append("fun ").append(function.name).append("(")
				var count = 0
				for(param in kParameters) {
					if(param.kind == KParameter.Kind.INSTANCE) continue
					append(param.type).append(", ")
					count++
				}
				if(count != 0)
					setLength(length - 2)
				append(")").append(": ")
				append(function.returnType.toString())
			}
			return stringBuilder.toString()
		}
	}
	open class KProperty0Overload<V>(
		val property: KProperty0<V>
	): Overload(
		arrayOf(),
		property.returnType.jvmErasure.java,
		{ property.get() }
	) {
		override fun toString(): String {
			return "get${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KMutableProperty0Overload<V>(
		val property: KMutableProperty0<V>
	): Overload(
		arrayOf(property.returnType.jvmErasure.java),
		Unit::class.java,
		{ property.set(it[0] as V) }
	) {
		override fun toString(): String {
			return "set${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KProperty1Overload<T, V>(
		val owner: T,
		val property: KProperty1<T, V>
	): Overload(
		arrayOf(),
		property.returnType.jvmErasure.java,
		{ property.get(owner) }
	) {
		override fun toString(): String {
			return "get${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KMutableProperty1Overload<T, V>(
		val owner: T,
		val property: KMutableProperty1<T, V>
	): Overload(
		arrayOf(property.returnType.jvmErasure.java),
		Unit::class.java,
		{ property.set(owner, it[0] as V) }
	) {
		override fun toString(): String {
			return "set${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KProperty2Overload<D, E, V>(
		val owner1: D,
		val owner2: E,
		val property: KProperty2<D, E, V>
	): Overload(
		arrayOf(),
		property.returnType.jvmErasure.java,
		{ property.get(owner1, owner2) }
	) {
		override fun toString(): String {
			return "get${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}
	open class KMutableProperty2Overload<D, E, V>(
		val owner1: D,
		val owner2: E,
		val property: KMutableProperty2<D, E, V>
	): Overload(
		arrayOf(property.returnType.jvmErasure.java),
		Unit::class.java,
		{ property.set(owner1, owner2, it[0] as V) }
	) {
		override fun toString(): String {
			return "set${property.toString().replaceFirstChar { c -> c.uppercase() }}"
		}
	}

	companion object KotlinClosureUtils {
		@JvmStatic private var cache: GroovyKotlinCache<KotlinClosureUtils>? = null

		@JvmStatic
		fun construct() {
			cache = prepareGroovyKotlinCache(KotlinClosureUtils)
			addInjectScript(cache!!)
		}
		@JvmStatic
		fun destruct() {
			removeInjectScript(cache!!)
			cache = null
		}

		@ExportGradle @JvmStatic
		fun <R> getKFunctionOverloads(owners: Array<Any?>, function: KFunction<R>): Array<Overload> {
			val result = ArrayList<KFunctionOverload<out R>>()
			val parameters = function.parameters
			val lastOptionalParameters = arrayListOf(*parameters.toTypedArray())
			result += KFunctionOverload(owners, function, lastOptionalParameters.toTypedArray())
			for(i in (parameters.size - 1) downTo 0) {
				val parameter = parameters[i]
				if(parameter.kind == KParameter.Kind.INSTANCE) continue
				// Optional parameters in the middle is a bit confusing
				if(!parameter.isOptional) break
				lastOptionalParameters -= parameter
				result += KFunctionOverload(owners, function, lastOptionalParameters.toTypedArray())
			}
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun <V> getKProperty0Overloads(property: KProperty0<V>): Array<Overload> {
			val result = ArrayList<Overload>()
			result += KProperty0Overload(property)
			if(property is KMutableProperty0)
				result += KMutableProperty0Overload(property)
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun <T, V> getKProperty1Overloads(owner: T, property: KProperty1<T, V>): Array<Overload> {
			val result = ArrayList<Overload>()
			result += KProperty1Overload(owner, property)
			if(property is KMutableProperty1)
				result += KMutableProperty1Overload(owner, property)
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun <D, E, V> getKProperty2Overloads(owner1: D, owner2: E, property: KProperty2<D, E, V>): Array<Overload> {
			val result = ArrayList<Overload>()
			result += KProperty2Overload(owner1, owner2, property)
			if(property is KMutableProperty2)
				result += KMutableProperty2Overload(owner1, owner2, property)
			return result.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun typeClashing(types: Array<out Class<*>>): Class<*> {
			if(types.isEmpty()) return Any::class.java
			var result: Class<*> = types[0]
			for(i in 1 until types.size) {
				val type = types[i]
				if(result.isAssignableFrom(type)) continue
				if(type.isAssignableFrom(result)) { result = type; continue }
				result = Any::class.java; break
			}
			return result
		}
		@ExportGradle @JvmStatic
		fun parameterTypesClashing(argumentTypes: Array<Array<out Class<*>>>): Array<out Class<*>> {
			val distinctLength = argumentTypes.map { it.size }.distinct()
			if(distinctLength.isEmpty()) return arrayOf()
			if(distinctLength.size > 1) return arrayOf(Array<Any?>::class.java)
			val length = distinctLength[0]
			return arrayOfNulls<Array<Class<*>>>(length)
				.mapIndexed { i, _ -> argumentTypes.map { it[i] }.toTypedArray() }
				.map { typeClashing(it) }.toTypedArray()
		}
		@ExportGradle @JvmStatic
		fun flattenVararg(args: Array<out Any?>): Array<out Any?> {
			if(args.isEmpty()) return args
			val last = args.last()!!
			if(!last.javaClass.isArray) return args
			return arrayOf(*args.copyOfRange(0, args.size - 1), *(last as Array<Any?>))
		}
		@ExportGradle @JvmStatic
		inline fun <reified T> kotlinReifiedType(type: KClass<*>): Class<*> {
			val typeJava = type.java
			if(null is T || (type as? KType)?.isMarkedNullable == true) return typeJava
			return boxedToPrimitive[typeJava] ?: typeJava
		}
		@JvmStatic val emptyArr = emptyArray<Any?>()
		// https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.3
		// Some rules are based on docs, but there are some cases that have been modified
		// See further comments for details.
		@ExportGradle @JvmStatic
		fun doOverloading(overloads: List<Overload>, args: Array<out Any?>): Pair<Overload, Array<Any?>> {
			data class CallData(
				var overload: Overload,
				var args: Array<Any?> = emptyArr,
				var error: String? = null,
				var changedArgsCount: Int = 0,
				var notExactMatchCount: Int = 0
			)
			/* Length analysis
			 * n-1, if last param is vararg, then that vararg's length is 0
			 * n  , equal length, need to check more about the inheritance
			 * n.., vararg argument
			 */
			val filteredOverloads = mutableListOf<CallData>()
			for(overload in overloads) {
				val types = overload.parameterTypes
				// empty arguments
				if(types.isEmpty()) {
					if(args.isEmpty())
						filteredOverloads += CallData(overload)
					continue
				}
				val lastParam = types.last()
				// vararg type
				if(lastParam.isArray) {
					// vararg length may vary from 0...n
					if(args.size >= types.size - 1) {
						filteredOverloads += CallData(overload)
						continue
					}
				}
				// if length at least the same
				if(types.size == args.size)
					filteredOverloads += CallData(overload)
			}
			/* Type analysis
			 * every parameter will be checked if it's assignable from args
			 */
			val pushArg: (CallData, Int, Any?) -> Unit = { data, i, arg ->
				if(data.args == emptyArr)
					data.args = arrayOfNulls(data.overload.parameterCount)
				data.args[i] = arg
			}
			val tryChangeArg: (CallData, Any?, Class<*>) -> Any? = { data, arg, type ->
				var result = arg
				if(arg is Closure<*> && Function::class.java.isAssignableFrom(type)) {
					result = closureToLambda(arg, type as Class<Function<*>>)
					data.changedArgsCount++
				}
				result
			}
			Outer@for(data in filteredOverloads) {
				val overload = data.overload
				val types = overload.parameterTypes
				for(i in types.indices) {
					val type = types[i]
					val arg = tryChangeArg(data, if(i < args.size) args[i] else null, type)
					val argType = arg?.javaClass
					val primitiveArgType = if(argType != null) if(argType.isPrimitive) argType else boxedToPrimitive[argType] else null
					// Strict primitive checking
					if(type.isPrimitive) {
						if(argType == null) {
							data.error = "Cannot cast null to $type (primitive)"
							continue@Outer
						}
						if(primitiveArgType == null) {
							data.error = "Cannot cast $argType to $type (primitive)"
							continue@Outer
						}
						if(!primitiveWideningConversions[primitiveArgType]!!.contains(type)) {
							data.error = "Cannot widening conversion $argType to $type (primitive)"
							continue@Outer
						}
						if(primitiveArgType != type) {
							// TODO: The cost of widening conversions
							// Suppose this setup:
							// ```
							// fun test(Int)
							// fun test(Long)
							// test(Byte)
							// ```
							// `fun(Int)` will be called instead of `fun(Long)`. Because the cost of widening conversions is smaller.
							// In this algorithm, that call will be resulting in an error because of ambiguity. the cost of widening
							// conversions is not been accounted, yet.
							data.changedArgsCount++
							val widenedConversion = boxedWideningConversion(arg, type)
							pushArg(data, i, widenedConversion)
						} else
							pushArg(data, i, arg)
						continue
					}
					// Empty vararg
					if(type.isArray && i == args.size) {
						val componentType = type.componentType
						data.notExactMatchCount++
						val vararg = java.lang.reflect.Array.newInstance(componentType, 0)
						pushArg(data, i, vararg)
						continue
					}
					// Non-empty vararg
					if(type.isArray) {
						val componentType = type.componentType
						// `null` is the last arguments
						if(argType == null && i == args.size - 1) {
							if(!componentType.isPrimitive) {
								// Can call with null member to vararg, by making the array explicitly.
//								data.error = "Ambiguous cast null to $type, unclear if a varargs or non-varargs call is desired"
								data.notExactMatchCount++
								pushArg(data, i, null)
								continue
							} else {
								// Plain old java will make this call ambiguous, but since the component type can't be null,
								// it does make sense that the array is null. But I might change this later.
								data.notExactMatchCount++
								pushArg(data, i, null)
								continue
							}
						}
						if(if(componentType.isPrimitive) {
							if(primitiveArgType == null) false
							else componentType.isAssignableFrom(primitiveArgType) || primitiveWideningConversions[primitiveArgType]!!.contains(componentType)
						} else argType == null || componentType.isAssignableFrom(argType)) {
							val vararg = java.lang.reflect.Array.newInstance(componentType, args.size - i)
							for(j in i until args.size) {
								val varargv = args[j]
								val varargvType = varargv?.javaClass
								val primitiveVarargvType = if(varargvType != null) if(varargvType.isPrimitive) varargvType else boxedToPrimitive[varargvType] else null
								if(componentType.isPrimitive) {
									if(varargvType == null) {
										data.error = "Cannot cast null to $componentType (primitive) [vararg $j]"
										continue@Outer
									}
									if(primitiveVarargvType == null) {
										data.error = "Cannot cast $varargvType to $componentType (primitive) [vararg $j]"
										continue@Outer
									}
									if(!primitiveWideningConversions[primitiveVarargvType]!!.contains(componentType)) {
										data.error = "Cannot widening conversion $varargvType to $componentType (primitive) [vararg $j]"
										continue@Outer
									}
									if(primitiveVarargvType != componentType) {
										data.changedArgsCount++
										val widenedConversion = boxedWideningConversion(varargv, componentType)
										java.lang.reflect.Array.set(vararg, j - i, widenedConversion)
									} else
										java.lang.reflect.Array.set(vararg, j - i, varargv)
									continue
								}
								if(varargvType == null || componentType.isAssignableFrom(varargvType)) {
									if(varargvType != null && componentType != varargvType)
										data.notExactMatchCount++
									java.lang.reflect.Array.set(vararg, j - i, varargv)
									continue
								}
								data.error = "Cannot cast $varargvType to $componentType [vararg $j]"
								continue@Outer
							}
							data.changedArgsCount++
							pushArg(data, i, vararg)
							continue
						}
					}
					// Inheritance
					if(argType == null || type.isAssignableFrom(argType)) {
						if(argType != null && type != argType)
							data.notExactMatchCount++
						pushArg(data, i, arg)
						continue
					}
					data.error = "Cannot cast $argType to $type"
					continue@Outer
				}
			}
			val notError = filteredOverloads.filter { it.error == null }
			if(notError.isEmpty())
				throw IllegalStateException("No matched method definition for [${args.joinToString(", ") { if(it != null) it::class.java.toString() else "NULL" }}]\n" +
						"\t\twith values [${args.joinToString(", ") { it.toString() }}]\n" +
						"Filtered overloads: \n${filteredOverloads.joinToString("\n") { "\t- ${it.overload} (${it.error})" }}\n" +
						"Non-filtered overloads: \n${overloads.filter { o -> filteredOverloads.find { it.overload == o } == null }.joinToString("\n") { "\t- $it" }}")
			if(notError.size > 1) {
				/* Prefer args not changed
				 */
				val minChangedArgsCount = notError.minOf { it.changedArgsCount }
				val notChangedArgs = notError.filter { it.changedArgsCount == minChangedArgsCount }
				if(notChangedArgs.size == 1) {
					val first = notChangedArgs.first()
					return Pair(first.overload, first.args)
				}
				/* Prefer not exact minimum
				 */
				val minNotExactMatchCount = notChangedArgs.minOf { it.notExactMatchCount }
				val notExactMatch = notChangedArgs.filter { it.notExactMatchCount == minNotExactMatchCount }
				if(notExactMatch.size == 1) {
					val first = notExactMatch.first()
					return Pair(first.overload, first.args)
				}
				/* Prefer exact maximum
				 */
				val maxExactCount = notExactMatch.maxOf { it.args.size }
				val maxExactArgs = notExactMatch.filter { it.args.size == maxExactCount }
				if(maxExactArgs.size == 1) {
					val first = maxExactArgs.first()
					return Pair(first.overload, first.args)
				}
				/* Priorites
				 */
				val maxPriorityValue = maxExactArgs.maxOf { it.overload.priority }
				val maxPriority = maxExactArgs.filter { it.overload.priority == maxPriorityValue }
				if(maxPriority.size == 1) {
					val first = maxPriority.first()
					return Pair(first.overload, first.args)
				}
				throw IllegalStateException("Ambiguous overloads call for [${args.joinToString(", ") { if(it != null) it::class.java.toString() else "NULL" }}]\n" +
						"\t\twith values [${args.joinToString(", ") { it.toString() }}]\n" +
						"Matched overloads: \n${notError.joinToString("\n") { "\t- ${it.overload} (Not Exact: ${it.notExactMatchCount}, Changed Args: ${it.changedArgsCount}, Priority: ${it.overload.priority})" }}")
			}
			val first = notError.first()
			return Pair(first.overload, first.args)
		}
	}
}
