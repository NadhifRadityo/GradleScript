package GradleScript.TestFixtures

import GradleScript.Common.constructNoContext
import GradleScript.Common.destructNoContext
import GradleScript.Strategies.FileUtils
import GradleScript.Strategies.FileUtils.file
import GradleScript.Strategies.FileUtils.fileExists
import GradleScript.Strategies.FileUtils.fileRelative
import GradleScript.Strategies.LoggerUtils
import GradleScript.Strategies.LoggerUtils.lwarn
import GradleScript.Strategies.RuntimeUtils.env_getFile
import GradleScript.Strategies.StringUtils.randomString
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.UnexpectedBuildFailure
import org.gradle.testkit.runner.UnexpectedBuildSuccess
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.StandardCopyOption

val GRADLE_BUILD_PATH: File = run {
	val buildPath = env_getFile("GRADLEUTILS_PROJECTPATH", File(System.getProperty("user.dir")))
	if(buildPath == null || !buildPath.exists())
		throw Error("Gradle build path does not exists! Try reconfigure gradle environment variable set/export GRADLEUTILS_PROJECTPATH=\"...\"")
	val buildFiles = fileExists(buildPath, "build.gradle", "build.gradle.kts")
	if(buildFiles.size != 1)
		throw Error("Gradle build path is not a valid project! The directory must contains exactly one \"build.gradle(.kts)\"")
	val settingsFiles = fileExists(buildPath, "settings.gradle", "settings.gradle.kts")
	if(settingsFiles.size != 1)
		throw Error("Gradle build path is not a valid project! The directory must contains exactly one \"settings.gradle(.kts)\"")
	buildPath
}
val `$` = '$'

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

typealias FileDSLExpression<RECEIVER, RESULT> = RECEIVER.() -> RESULT
typealias FileDSLGenerator<RECEIVER> = (File, FileDSL<RECEIVER>) -> RECEIVER
open class FileDSL<RECEIVER: FileDSL<RECEIVER>>(val generator: FileDSLGenerator<RECEIVER>, val root: File) {
	internal val _instance: RECEIVER
		get() = this as RECEIVER
	open operator fun String.not(): File {
		return if(!this.startsWith("/")) mkfile(this)
			else mkdir(this)
	}
	open operator fun <RESULT> String.times(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT {
		return generator(!this, _instance).expression()
	}
	// Technically same as `"/testDir" * `
	open operator fun <RESULT> String.div(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT {
		return generator(!"/$this", _instance).expression()
	}

	open fun <RESULT> files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT =
		generator(root, _instance).expression()
	open fun <RESULT> File.files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT =
		generator(this, _instance).expression()

	open fun file(vararg name: String): File {
		return file(root, *name)
	}
	open fun fileRelative(file: File): String {
		return fileRelative(root, file)
	}
	open fun fileCopy(from: File, vararg name: String): File {
		return FileUtils.fileCopy(from, file(*name), StandardCopyOption.REPLACE_EXISTING)
	}
	open fun fileCopyDir(from: File, dir: String, vararg what: String): Array<File> {
		return FileUtils.fileCopyDir(from, file(dir), what, StandardCopyOption.REPLACE_EXISTING)
	}
	open fun mkfile(vararg name: String): File {
		return FileUtils.mkfile(file(*name))
	}
	open fun mkdir(vararg name: String): File {
		return FileUtils.mkdir(file(*name))
	}
	open fun delfile(vararg name: String): File {
		return FileUtils.delfile(file(*name))
	}
	open fun readBytes(vararg name: String): ByteArray {
		return FileUtils.fileBytes(file(*name))
	}
	open fun readString(vararg name: String): String {
		return FileUtils.fileString(file(*name))
	}
	open fun writeBytes(content: ByteArray) {
		FileUtils.writeFileBytes(file(), content)
	}
	open fun writeString(content: String) {
		FileUtils.writeFileString(file(), content)
	}

	// "test.txt" * { -"replace content" }
	open operator fun String.unaryMinus() {
		writeString(this)
	}
	// "test.txt" * { +"append content" }
	open operator fun String.unaryPlus() {
		val old = readString()
		writeString(old + this)
	}
	// "test.txt" * { -"replace content".encodeToByteArray() }
	open operator fun ByteArray.unaryMinus() {
		writeBytes(this)
	}
	// "test.txt" * { +"append content".encodeToByteArray() }
	open operator fun ByteArray.unaryPlus() {
		val old = readBytes()
		writeBytes(old + this)
	}
	// "test.txt" -= "replace content"
	open operator fun String.minusAssign(content: String) {
		this * { -content }
	}
	// "test.txt" += "append content"
	open operator fun String.plusAssign(content: String) {
		this * { +content }
	}
	// "test.txt" % "replace content".encodeToByteArray()
	open operator fun String.rem(content: ByteArray) {
		this * { -content }
	}
	// "test.txt" .. "append content".encodeToByteArray()
	open operator fun String.rangeTo(content: ByteArray) {
		this * { +content }
	}
}

typealias DefaultFileDSLExpression<RESULT> = FileDSLExpression<DefaultFileDSL, RESULT>
typealias DefaultFileDSLGenerator = FileDSLGenerator<DefaultFileDSL>
fun DefaultFileDSLGenerator(): DefaultFileDSLGenerator { lateinit var generator: DefaultFileDSLGenerator;
	generator = { file, _ -> DefaultFileDSL(file, generator) }; return generator }
open class DefaultFileDSL(root: File, generator: DefaultFileDSLGenerator = DefaultFileDSLGenerator()): FileDSL<DefaultFileDSL>(generator, root) { }

fun <T> File.files(expression: DefaultFileDSLExpression<T>) {
	DefaultFileDSL(this).expression()
}

open class AbstractProjectTest {
	@TempDir
	lateinit var testDir: File
	protected var currentProject: Project? = null
	val currentProjectDir: File
		get() = currentProject?.directory ?: testDir

	fun withRootProject(name: String = "RootProject_${randomString()}", builds: List<RootProject> = listOf(), callback: RootProject.() -> Unit): RootProject {
		val rootProjectDir = file(testDir, "/${name}")
		val rootProject = RootProject(rootProjectDir, name, builds)
		val oldCurrentProject = currentProject
		try {
			currentProject = rootProject
			rootProject.callback()
			return rootProject
		} finally {
			currentProject = oldCurrentProject
		}
	}
	fun ProjectFileDSL.withProject(name: String = "Project_${randomString()}", callback: Project.() -> Unit): Project {
		var parent: Project? = project
		while(parent != null && parent !is RootProject)
			parent = parent.parent
		if(parent == null || parent !is RootProject)
			throw IllegalStateException("Cannot find parent project!")
		val projectDir = file(root, "/${name}")
		val project = Project(parent, projectDir, name)
		parent.children += project
		val oldCurrentProject = currentProject
		try {
			currentProject = project
			project.callback()
			return project
		} finally {
			currentProject = oldCurrentProject
		}
	}
	fun RootProject.withDefaultSettingsSource() {
		withSettingsSource {
			-ROOTPROJECT_SETTINGS(name, directory, builds.isEmpty(), children)
		}
	}
	fun RootProject.withDefaultBuildSource() {
		withBuildSource {
			-ROOTPROJECT_BUILD(name)
		}
	}

	fun Project.newScript(name: String = randomString(10), expression: ProjectFileDSLExpression<Unit>): File {
		name * {
			-"""
				context(this) {
					scriptApply()
				}
			"""
			expression()
		}
		return !name
	}

	fun run(rootProject: RootProject, expectSucceed: Boolean = true): BuildResult {
		var throwable: Throwable? = null
		var result: BuildResult? = null
		try {
			result = rootProject.run(expectSucceed)
		} catch(e: Throwable) {
			when(e) {
				is UnexpectedBuildFailure -> throwable = e
				is UnexpectedBuildSuccess -> throwable = e
				else -> throw e
			}
		} finally {
			try {
				// TODO: Manual destruct call, see Bugs#2
				if(expectSucceed && throwable != null)
					lwarn("Was expecting to not fail but failed, will manually call destruct")
				if(!expectSucceed || throwable != null)
					manualDestruct()
			} catch(e2: Throwable) {
				if(throwable == null)
					throwable = e2
				else {
					e2.addSuppressed(throwable)
					throwable = e2
				}
			}
			if(throwable != null)
				throw throwable
		}
		return result!!
	}
	fun manualDestruct() {
		LoggerUtils.lwarn("Manual destructing")
		withRootProject {
			withDefaultSettingsSource()
			withBuildSource {
				-"""
				import GradleScript.Common
				
				plugins {
					id 'java'
				}
				
				Common.context(this) {
					if(Common.currentSession() != null)				
						Common.destruct()
				}
				"""
			}
			run()
		}
	}

	companion object {
		@BeforeAll @JvmStatic
		fun beforeAll() {
			constructNoContext()
		}

		@AfterAll @JvmStatic
		fun afterAll() {
			destructNoContext()
		}
	}
}
