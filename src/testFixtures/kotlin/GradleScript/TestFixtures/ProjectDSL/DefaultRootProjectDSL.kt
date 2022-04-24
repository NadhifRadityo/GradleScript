package GradleScript.TestFixtures.RootProjectDSL

import GradleScript.TestFixtures.ProjectDSL.*

typealias DefaultRootProjectDSLExpression<RESULT> = RootProjectDSLExpression<DefaultRootProjectDSL, DefaultProjectFileDSL, RESULT>
typealias DefaultRootProjectDSLGenerator = RootProjectDSLGenerator<DefaultRootProjectDSL, DefaultProjectFileDSL>
fun DefaultRootProjectDSLGenerator(): DefaultRootProjectDSLGenerator {
	return { dsl, project -> DefaultRootProjectDSL(project as RootProject, dsl.__project_dsl_generator, dsl.__file_dsl_generator) }
}
open class DefaultRootProjectDSL(
	__project_dsl_project: RootProject,
	__project_dsl_generator: DefaultRootProjectDSLGenerator = DefaultRootProjectDSLGenerator(),
	__file_dsl_generator: DefaultProjectFileDSLGenerator = DefaultProjectFileDSLGenerator()
): RootProjectDSL<DefaultRootProjectDSL, DefaultProjectFileDSL> by RootProjectDSLImpl(__project_dsl_generator, __project_dsl_project, __file_dsl_generator) { }
