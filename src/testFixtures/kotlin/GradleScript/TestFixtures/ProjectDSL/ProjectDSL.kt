package GradleScript.TestFixtures.ProjectDSL

import org.gradle.testkit.runner.GradleRunner
import java.io.File

@DslMarker
annotation class ProjectDSLMarker

typealias ProjectDSLExpression<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT> = RECEIVER.() -> RESULT
typealias ProjectDSLGenerator<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> = (ProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, Project) -> RECEIVER
@ProjectDSLMarker
interface ProjectDSL<
	RECEIVER: ProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER> {
	val __project_dsl_generator: ProjectDSLGenerator<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>
	val __project_dsl_project: Project
	val __project_dsl_instance: RECEIVER

	fun <RESULT> projects(expression: ProjectDSLExpression<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
	fun <RESULT> Project.projects(expression: ProjectDSLExpression<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT

	val parent: Project?
	val directory: File
	val name: String

	var buildFileDSL: DSL
	val buildFile: File

	val configureCallbacks: MutableList<GradleRunner.(GradleRunner) -> Unit>
	fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit)

	fun <RESULT> withBuildSource(expression: ProjectFileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
}
open class ProjectDSLImpl<
	RECEIVER: ProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>(
	override val __project_dsl_generator: ProjectDSLGenerator<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	override val __project_dsl_project: Project,
	__file_dsl_file_generator: ProjectFileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_directory_generator: ProjectFileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_generic_generator: ProjectFileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
):
	ProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
		by ProjectFileNodeDirectoryDSLImpl(__file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator, __project_dsl_project.directory) {

	override val __project_dsl_instance: RECEIVER
		get() = this as RECEIVER

	override fun <RESULT> projects(expression: ProjectDSLExpression<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT =
		__project_dsl_generator(__project_dsl_instance, __project_dsl_project).expression()
	override fun <RESULT> Project.projects(expression: ProjectDSLExpression<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT =
		__project_dsl_generator(__project_dsl_instance, this).expression()

	override val parent: Project?
		get() = __project_dsl_project.parent
	override val directory: File
		get() = __project_dsl_project.directory
	override val name: String
		get() = __project_dsl_project.name

	override var buildFileDSL
		get() = __project_dsl_project.buildFileDSL
		set(value) { __project_dsl_project.buildFileDSL = value }
	override val buildFile
		get() = __project_dsl_project.buildFile

	override val configureCallbacks
		get() = __project_dsl_project.configureCallbacks
	override fun configure(configureCallbacks: GradleRunner.(GradleRunner) -> Unit) = __project_dsl_project.configure(configureCallbacks)

	override fun <RESULT> withBuildSource(expression: ProjectFileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT {
		return __project_dsl_project.buildFile.files {
			asNodeFile {
				mkfile()
				expression()
			}
		}
	}
}
