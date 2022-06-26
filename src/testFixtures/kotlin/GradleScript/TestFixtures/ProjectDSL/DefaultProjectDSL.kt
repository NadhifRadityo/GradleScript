package GradleScript.TestFixtures.ProjectDSL

typealias DefaultProjectDSLExpression<RESULT> = ProjectDSLExpression<DefaultProjectDSL, DefaultProjectFileNodeFileDSL, DefaultProjectFileNodeDirectoryDSL, DefaultProjectFileNodeGenericDSL, RESULT>
typealias DefaultProjectDSLGenerator = ProjectDSLGenerator<DefaultProjectDSL, DefaultProjectFileNodeFileDSL, DefaultProjectFileNodeDirectoryDSL, DefaultProjectFileNodeGenericDSL>
fun DefaultProjectDSLGenerator(): DefaultProjectDSLGenerator {
	return { dsl, project -> DefaultProjectDSL(project, dsl.__project_dsl_generator, dsl.__file_dsl_file_generator, dsl.__file_dsl_directory_generator, dsl.__file_dsl_generic_generator) }
}
open class DefaultProjectDSL(
	__project_dsl_project: Project,
	__project_dsl_generator: DefaultProjectDSLGenerator = DefaultProjectDSLGenerator(),
	__file_dsl_file_generator: DefaultProjectFileNodeFileDSLGenerator = DefaultProjectFileNodeFileDSLGenerator(),
	__file_dsl_directory_generator: DefaultProjectFileNodeDirectoryDSLGenerator = DefaultProjectFileNodeDirectoryDSLGenerator(),
	__file_dsl_generic_generator: DefaultProjectFileNodeGenericDSLGenerator = DefaultProjectFileNodeGenericDSLGenerator()
):
	ProjectDSL<DefaultProjectDSL, DefaultProjectFileNodeFileDSL, DefaultProjectFileNodeDirectoryDSL, DefaultProjectFileNodeGenericDSL>
		by ProjectDSLImpl(__project_dsl_generator, __project_dsl_project, __file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator)
{ }
