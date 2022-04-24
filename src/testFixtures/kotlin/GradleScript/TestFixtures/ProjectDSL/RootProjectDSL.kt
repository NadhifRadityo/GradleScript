package GradleScript.TestFixtures.ProjectDSL

import GradleScript.TestFixtures.FileDSL.FileDSLGenerator
import java.io.File

typealias RootProjectDSLExpression<RECEIVER, FILE_RECEIVER, RESULT> = ProjectDSLExpression<RootProjectDSL<RECEIVER, FILE_RECEIVER>, FILE_RECEIVER, RESULT>
typealias RootProjectDSLGenerator<RECEIVER, FILE_RECEIVER> = ProjectDSLGenerator<RootProjectDSL<RECEIVER, FILE_RECEIVER>, FILE_RECEIVER>
interface RootProjectDSL<RECEIVER: RootProjectDSL<RECEIVER, FILE_RECEIVER>, FILE_RECEIVER: ProjectFileDSL<FILE_RECEIVER>>: ProjectDSL<RootProjectDSL<RECEIVER, FILE_RECEIVER>, FILE_RECEIVER> {
	val __project_dsl_root_project: RootProject

	val builds: List<RootProject>
	val children: MutableList<Project>

	var settingsFileDSL: DSL
	val settingsFile: File

	fun <RESULT> withSettingsSource(expression: ProjectFileDSLExpression<FILE_RECEIVER, RESULT>): RESULT
}
open class RootProjectDSLImpl<RECEIVER: RootProjectDSL<RECEIVER, FILE_RECEIVER>, FILE_RECEIVER: ProjectFileDSL<FILE_RECEIVER>>(
	__project_dsl_generator: RootProjectDSLGenerator<RECEIVER, FILE_RECEIVER>,
	__project_dsl_project: RootProject,
	__file_dsl_generator: FileDSLGenerator<ProjectFileDSL<FILE_RECEIVER>>
): RootProjectDSL<RECEIVER, FILE_RECEIVER>, ProjectDSL<RootProjectDSL<RECEIVER, FILE_RECEIVER>, FILE_RECEIVER> by ProjectDSLImpl(__project_dsl_generator, __project_dsl_project, __file_dsl_generator) {
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

	override fun <RESULT> withSettingsSource(expression: ProjectFileDSLExpression<FILE_RECEIVER, RESULT>): RESULT {
		return __project_dsl_root_project.settingsFile.files {
			mkfile()
			files(expression)
		}
	}
}
