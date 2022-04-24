package GradleScript.TestFixtures.ProjectDSL

typealias DefaultProjectDSLExpression<RESULT> = ProjectDSLExpression<DefaultProjectDSL, DefaultProjectFileDSL, RESULT>
typealias DefaultProjectDSLGenerator = ProjectDSLGenerator<DefaultProjectDSL, DefaultProjectFileDSL>
fun DefaultProjectDSLGenerator(): DefaultProjectDSLGenerator {
	return { dsl, project -> DefaultProjectDSL(project, dsl.__project_dsl_generator, dsl.__file_dsl_generator) }
}
open class DefaultProjectDSL(
	__project_dsl_project: Project,
	__project_dsl_generator: DefaultProjectDSLGenerator = DefaultProjectDSLGenerator(),
	__file_dsl_generator: DefaultProjectFileDSLGenerator = DefaultProjectFileDSLGenerator()
): ProjectDSL<DefaultProjectDSL, DefaultProjectFileDSL> by ProjectDSLImpl(__project_dsl_generator, __project_dsl_project, __file_dsl_generator) { }
