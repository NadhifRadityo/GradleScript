package GradleScript.TestFixtures.ProjectDSL

import java.io.File

typealias RootProjectDSLExpression<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT> = ProjectDSLExpression<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>
typealias RootProjectDSLGenerator<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> = ProjectDSLGenerator<RootProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>
interface RootProjectDSL<
	RECEIVER: RootProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>: ProjectDSL<RootProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> {
	val __project_dsl_root_project: RootProject

	val builds: List<RootProject>
	val children: MutableList<Project>

	var settingsFileDSL: DSL
	val settingsFile: File

	fun <RESULT> withSettingsSource(expression: ProjectFileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
}
open class RootProjectDSLImpl<
	RECEIVER: RootProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>(
	__project_dsl_generator: RootProjectDSLGenerator<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	__project_dsl_project: RootProject,
	__file_dsl_file_generator: ProjectFileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_directory_generator: ProjectFileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_generic_generator: ProjectFileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
):
	RootProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	ProjectDSL<RootProjectDSL<RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>
		by ProjectDSLImpl(__project_dsl_generator, __project_dsl_project, __file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator) {

	override val __project_dsl_instance: RECEIVER
		get() = this as RECEIVER
	override val __project_dsl_root_project: RootProject
		get() = __project_dsl_project as RootProject

	override val builds: List<RootProject>
		get() = __project_dsl_root_project.builds
	override val children: MutableList<Project>
		get() = __project_dsl_root_project.children

	override var settingsFileDSL
		get() = __project_dsl_root_project.settingsFileDSL
		set(value) { __project_dsl_root_project.settingsFileDSL = value }
	override val settingsFile
		get() = __project_dsl_root_project.settingsFile

	override fun <RESULT> withSettingsSource(expression: ProjectFileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT {
		return __project_dsl_root_project.settingsFile.files {
			asNodeFile {
				mkfile()
				expression()
			}
		}
	}
}
