package GradleScript.GroovyKotlinInteroperability

annotation class ExportGradle(
	val names: Array<String> = [], // FIELD, METHOD
	val allowSet: Boolean = false, // FIELD
	val allowGet: Boolean = true, // FIELD
	val asMode: Int = 0, // FIELD, METHOD; 0=default, 1=property, 2=method
	val additionalOverloads: Int = 1 // FIELD, METHOD; 0=disabled, 1=IgnoreSelfOverload, 2=WithSelfOverload
)
