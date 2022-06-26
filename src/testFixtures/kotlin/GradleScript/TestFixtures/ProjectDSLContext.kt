package GradleScript.TestFixtures

import GradleScript.Strategies.FileUtils.file
import GradleScript.Strategies.StringUtils.randomString
import GradleScript.TestFixtures.ProjectDSL.*
import java.io.File

interface ProjectDSLContext: BaseContextTest {
	var projectDir: File
	var currentProject: Project?
	val currentProjectDir: File
		get() = currentProject?.directory ?: projectDir

	fun <T> withRootProject(name: String = "RootProject_${randomString()}", builds: List<RootProject> = listOf(), expression: DefaultRootProjectDSLExpression<T>): RootProject {
		val rootProjectDir = file(projectDir, "/${name}")
		val rootProject = RootProject(rootProjectDir, name, builds)
		val oldCurrentProject = currentProject
		try {
			currentProject = rootProject
			DefaultRootProjectDSL(rootProject).expression()
			return rootProject
		} finally {
			currentProject = oldCurrentProject
		}
	}
	fun <T> ProjectDSL<*, *, *, *>.withProject(name: String = "Project_${randomString()}", expression: DefaultProjectDSLExpression<T>): Project {
		var parent: Project? = __project_dsl_project
		while(parent != null && parent !is RootProject)
			parent = parent.parent
		if(parent == null || parent !is RootProject)
			throw IllegalStateException("Cannot find parent project!")
		val projectDir = file(__file_dsl_file, "/${name}")
		val project = Project(parent, projectDir, name)
		parent.children += project
		val oldCurrentProject = currentProject
		try {
			currentProject = project
			DefaultProjectDSL(project).expression()
			return project
		} finally {
			currentProject = oldCurrentProject
		}
	}
	fun RootProjectDSL<*, *, *, *>.withDefaultSettingsSource() {
		withSettingsSource {
			-ROOTPROJECT_SETTINGS(
				this@withDefaultSettingsSource.name, this@withDefaultSettingsSource.directory,
				this@withDefaultSettingsSource.builds.isEmpty(), this@withDefaultSettingsSource.children)
		}
	}
	fun ProjectDSL<*, *, *, *>.withDefaultBuildSource() {
		withBuildSource {
			-ROOTPROJECT_BUILD(this@withDefaultBuildSource.name)
		}
	}
	fun <FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, *, *>> ProjectDSL<*, FILE_RECEIVER, *, *>.newScript(name: String = randomString(10) + ".gradle", expression: ProjectFileNodeFileDSLExpression<FILE_RECEIVER, *, *, *>): File {
		name * {
			-SCRIPT_INITIAL(name)
			expression()
		}
		return !name
	}

	fun ROOTPROJECT_SETTINGS(name: String, directory: File, rootBuild: Boolean, children: List<Project>): String {
		return if(rootBuild) """
		plugins {
			id 'GradleScript.GradleSettingsPlugin'
		}
		
		rootProject.name = '${name}'
		"""
		else """
		rootProject.name = '${name}'
		"""
	}
	fun ROOTPROJECT_BUILD(name: String): String {
		return """
		plugins {
			id 'java'
		}
		"""
	}
	fun SCRIPT_INITIAL(name: String): String {
		return """
		context(this) {
			scriptApply()
		}
		"""
	}
}
