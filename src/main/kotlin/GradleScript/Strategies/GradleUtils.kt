package GradleScript.Strategies

import GradleScript.Common.groovyKotlinCaches
import GradleScript.Common.lastContext
import GradleScript.Common.lastContext0
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.Strategies.ClassUtils.metaClassFor
import GradleScript.Strategies.UnsafeUtils.unsafe
import GradleScript.Strategies.Utils.__invalid_type
import GradleScript.Strategies.Utils.__must_not_happen
import GradleScript.Strategies.Utils.__unimplemented
import groovy.lang.Closure
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.StartParameter
import org.gradle.api.*
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.ArtifactHandler
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.DependencyLockingHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.component.SoftwareComponentContainer
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.file.*
import org.gradle.api.initialization.IncludedBuild
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.dsl.ScriptHandler
import org.gradle.api.internal.GradleInternal
import org.gradle.api.internal.plugins.PluginManagerInternal
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.invocation.Gradle
import org.gradle.api.logging.Logger
import org.gradle.api.logging.LoggingManager
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.*
import org.gradle.api.provider.Provider
import org.gradle.api.provider.ProviderFactory
import org.gradle.api.resources.ResourceHandler
import org.gradle.api.services.BuildServiceRegistry
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.WorkResult
import org.gradle.groovy.scripts.BasicScript
import org.gradle.internal.composite.IncludedBuildInternal
import org.gradle.internal.extensibility.DefaultExtraPropertiesExtension
import org.gradle.kotlin.dsl.gradleKotlinDsl
import org.gradle.kotlin.dsl.support.DefaultKotlinScript
import org.gradle.kotlin.dsl.support.KotlinScriptHost
import org.gradle.launcher.daemon.server.scaninfo.DaemonScanInfo
import org.gradle.normalization.InputNormalizationHandler
import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec
import org.gradle.process.JavaExecSpec
import java.io.File
import java.lang.management.ManagementFactory
import java.lang.reflect.Field
import java.net.URI
import java.util.concurrent.Callable

object GradleUtils {
	@JvmStatic private var cache: GroovyKotlinCache<GradleUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(GradleUtils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	// Gradle Object Conversion
	@ExportGradle @JvmStatic @JvmOverloads
	fun asProject0(project: Any? = null): Project? {
		if(project == null) return lastContext0()?.project
		if(project is Project) return project
		if(project is String) return lastContext0()?.project?.project(project)
		if(project is BasicScript) return project.scriptTarget as ProjectInternal
		if(project is DefaultKotlinScript) return kotlinScriptHostTarget(project)
		return null
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun asProject(project: Any? = null): Project {
		return asProject0(project) ?: throw __invalid_type()
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun asGradle0(project: Any? = null): Gradle? {
		return asProject0(project)?.gradle
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun asGradle(project: Any? = null): Gradle {
		return asGradle0(project) ?: throw __invalid_type()
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun asTask0(task: Any, project: Any? = null): Task? {
		if(task is Task) return task
		if(task is String) return asProject0(project)?.tasks?.getByName(task)
		return null
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun asTask(task: Any, project: Any? = null): Task {
		return asTask0(task, project) ?: throw __invalid_type()
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun <T> asService0(clazz: Class<T>, project: Any? = null): T? {
		return (asProject0(project) as? ProjectInternal)?.services?.get(clazz)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun <T> asService(clazz: Class<T>, project: Any? = null): T {
		return asService0(clazz, project) ?: throw __invalid_type()
	}
	@ExportGradle
	fun asBuildProject0(build: IncludedBuild): Project? {
		if(build is IncludedBuildInternal) return GradleInternalProject(build.target.build)
		return GradleBuildProject(build)
	}
	@ExportGradle
	fun asBuildProject(build: IncludedBuild): Project {
		return asBuildProject0(build) ?: throw __invalid_type()
	}
	@ExportGradle
	fun asScriptHandler0(self: Any): ScriptHandler? {
		if(self is ScriptHandler)
			return self
		val metaClass = metaClassFor(self)
		if(metaClass != null) {
			val PROPERTY_UNKNOWN_buildscript = metaClass.properties.firstOrNull() { it.name == "buildscript" }
			val buildscript = PROPERTY_UNKNOWN_buildscript?.getProperty(self) as? ScriptHandler
			if(buildscript != null) return buildscript
		}
		val FIELD_UNKNOWN_buildscript = self.javaClass.declaredFields.firstOrNull { it.name == "buildscript" }
		val buildscript = FIELD_UNKNOWN_buildscript?.get(self) as? ScriptHandler
		if(buildscript != null) return buildscript
		return null
	}
	@ExportGradle
	fun asScriptHandler(self: Any): ScriptHandler {
		return asScriptHandler0(self) ?: throw __invalid_type()
	}

	// Gradle Extra Properties
	@JvmStatic internal val AFIELD_DefaultExtraPropertiesExtension_storage: Long
	init {
		val FIELD_DefaultExtraPropertiesExtension_storage = DefaultExtraPropertiesExtension::class.java.getDeclaredField("storage");
		AFIELD_DefaultExtraPropertiesExtension_storage = unsafe.objectFieldOffset(FIELD_DefaultExtraPropertiesExtension_storage)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun globalExtraPropertiesHas(key: String, project: Any? = null): Boolean {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		return gradleExt.has(key)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun <T> globalExtraPropertiesGet(key: String, project: Any? = null): T? {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		return if(gradleExt.has(key)) gradleExt.get(key) as T? else null
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun <T> globalExtraPropertiesSet(key: String, obj: T, project: Any? = null) {
		val gradleExt = asGradle(project) as ExtraPropertiesExtension
		gradleExt.set(key, obj)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun extraPropertiesRemoveKey(key: String, extra: ExtraPropertiesExtension? = null) {
		val ext = extra ?: asGradle()
		if(ext is DefaultExtraPropertiesExtension) {
			val storage = unsafe.getObject(ext, AFIELD_DefaultExtraPropertiesExtension_storage) as HashMap<String, Object>
			storage.remove(key)
			return
		}
		__invalid_type()
	}

	// Gradle Task
	@ExportGradle @JvmStatic @JvmOverloads
	fun forwardTask(project: Any?, filter: (Task) -> Boolean, exec: (Task) -> Unit = {}) {
		val project0 = lastContext().project0
		val project1 = asProject(project)
		project1.tasks.filter(filter).map { task -> project0.task(task.name) {
			it.group = task.group; it.dependsOn(task) } }.forEach(exec)
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun runAfterAnotherTask(tasks: Collection<Any>, project: Any? = null) {
		val tasks0 = tasks.map { asTask(it, project) }
		for(i in 1 until tasks0.size) tasks0[i].mustRunAfter(tasks0[i - 1])
	}

	// Gradle Daemon
	@ExportGradle @JvmStatic @JvmOverloads
	fun isSingleUseDaemon(project: Any? = null): Boolean {
		return asService(DaemonScanInfo::class.java, project).isSingleUse
	}
	@ExportGradle @JvmStatic
	fun isRunningOnDebug(): Boolean {
		return ManagementFactory.getRuntimeMXBean().getInputArguments().stream()
			.filter { it.indexOf("-agentlib:jdwp") != -1 }.count() > 0
	}
	@ExportGradle @JvmStatic @JvmOverloads
	fun isDaemonProbablyUnstable(project: Any? = null): Boolean {
		return isSingleUseDaemon(project) || isRunningOnDebug()
	}

	// Kotlin DSL
	fun <T: Any> kotlinScriptHostTarget(script: DefaultKotlinScript): T? {
		var CLASS_currentClass: Class<*> = script.javaClass
		var FIELD_UNKNOWN_host: Field? = null
		do {
			try { FIELD_UNKNOWN_host = CLASS_currentClass.getDeclaredField("host")
			} catch(e: NoSuchFieldException) { CLASS_currentClass = CLASS_currentClass.superclass }
		} while(FIELD_UNKNOWN_host == null && CLASS_currentClass != Any::class.java)
		if(FIELD_UNKNOWN_host == null) throw __must_not_happen()
		if(!KotlinScriptHost::class.java.isAssignableFrom(FIELD_UNKNOWN_host.type)) throw __invalid_type()
		FIELD_UNKNOWN_host.isAccessible = true
		return (FIELD_UNKNOWN_host.get(script) as KotlinScriptHost<T>).target
	}
	@ExportGradle @JvmStatic
	fun gradleKotlinDsl(project: Any? = null): Dependency {
		return asProject(project).gradleKotlinDsl()
	}

	open class GradleBuildProject<T: IncludedBuild>(
		val build: T
	): UnimplementedProject() {
		override fun getName(): String = build.name
		override fun getProjectDir(): File = build.projectDir
		override fun getRootDir(): File = build.projectDir
	}
	open class GradleInternalProject<T: GradleInternal>(
		val internal: T
	): UnimplementedProject() {
		private val _parent = lazy { val parent = internal.parent; if(parent != null) GradleInternalProject(parent) else null }
		override fun getPlugins(): PluginContainer = internal.plugins
		override fun apply(closure: Closure<*>) = internal.apply(closure)
		override fun apply(action: Action<in ObjectConfigurationAction>) = internal.apply(action)
		override fun apply(options: MutableMap<String, *>) = internal.apply(options)
		override fun getPluginManager(): PluginManagerInternal = internal.pluginManager
		override fun getParent(): Project? = _parent.value
		override fun getRootProject(): ProjectInternal = internal.rootProject
		override fun getRootDir(): File = rootProject.rootDir
		override fun allprojects(action: Action<in Project>) = internal.allprojects(action)
		override fun getGradle(): Gradle = internal.gradle
	}
	open class UnimplementedProject: Project {
		override fun compareTo(other: Project?): Int = __unimplemented()
		override fun getExtensions(): ExtensionContainer = __unimplemented()
		override fun getPlugins(): PluginContainer = __unimplemented()
		override fun apply(closure: Closure<*>): Unit = __unimplemented()
		override fun apply(action: Action<in ObjectConfigurationAction>): Unit = __unimplemented()
		override fun apply(options: MutableMap<String, *>): Unit = __unimplemented()
		override fun getPluginManager(): PluginManager = __unimplemented()
		override fun getRootProject(): Project = __unimplemented()
		override fun getRootDir(): File = __unimplemented()
		override fun getBuildDir(): File = __unimplemented()
		override fun setBuildDir(path: File): Unit = __unimplemented()
		override fun setBuildDir(path: Any): Unit = __unimplemented()
		override fun getBuildFile(): File = __unimplemented()
		override fun getParent(): Project? = __unimplemented()
		override fun getName(): String = __unimplemented()
		override fun getDisplayName(): String = __unimplemented()
		override fun getDescription(): String? = __unimplemented()
		override fun setDescription(description: String?): Unit = __unimplemented()
		override fun getGroup(): Any = __unimplemented()
		override fun setGroup(group: Any): Unit = __unimplemented()
		override fun getVersion(): Any = __unimplemented()
		override fun setVersion(version: Any): Unit = __unimplemented()
		override fun getStatus(): Any = __unimplemented()
		override fun setStatus(status: Any): Unit = __unimplemented()
		override fun getChildProjects(): MutableMap<String, Project> = __unimplemented()
		override fun setProperty(name: String, value: Any?): Unit = __unimplemented()
		override fun getProject(): Project = __unimplemented()
		override fun getAllprojects(): MutableSet<Project> = __unimplemented()
		override fun getSubprojects(): MutableSet<Project> = __unimplemented()
		override fun task(name: String): Task = __unimplemented()
		override fun task(args: MutableMap<String, *>, name: String): Task = __unimplemented()
		override fun task(args: MutableMap<String, *>, name: String, configureClosure: Closure<*>): Task = __unimplemented()
		override fun task(name: String, configureClosure: Closure<*>): Task = __unimplemented()
		override fun task(name: String, configureAction: Action<in Task>): Task = __unimplemented()
		override fun getPath(): String = __unimplemented()
		override fun getDefaultTasks(): MutableList<String> = __unimplemented()
		override fun setDefaultTasks(defaultTasks: MutableList<String>): Unit = __unimplemented()
		override fun defaultTasks(vararg defaultTasks: String?): Unit = __unimplemented()
		override fun evaluationDependsOn(path: String): Project = __unimplemented()
		override fun evaluationDependsOnChildren(): Unit = __unimplemented()
		override fun findProject(path: String): Project? = __unimplemented()
		override fun project(path: String): Project = __unimplemented()
		override fun project(path: String, configureClosure: Closure<*>): Project = __unimplemented()
		override fun project(path: String, configureAction: Action<in Project>): Project = __unimplemented()
		override fun getAllTasks(recursive: Boolean): MutableMap<Project, MutableSet<Task>> = __unimplemented()
		override fun getTasksByName(name: String, recursive: Boolean): MutableSet<Task> = __unimplemented()
		override fun getProjectDir(): File = __unimplemented()
		override fun file(path: Any): File = __unimplemented()
		override fun file(path: Any, validation: PathValidation): File = __unimplemented()
		override fun uri(path: Any): URI = __unimplemented()
		override fun relativePath(path: Any): String = __unimplemented()
		override fun files(vararg paths: Any?): ConfigurableFileCollection = __unimplemented()
		override fun files(paths: Any, configureClosure: Closure<*>): ConfigurableFileCollection = __unimplemented()
		override fun files(paths: Any, configureAction: Action<in ConfigurableFileCollection>): ConfigurableFileCollection = __unimplemented()
		override fun fileTree(baseDir: Any): ConfigurableFileTree = __unimplemented()
		override fun fileTree(baseDir: Any, configureClosure: Closure<*>): ConfigurableFileTree = __unimplemented()
		override fun fileTree(baseDir: Any, configureAction: Action<in ConfigurableFileTree>): ConfigurableFileTree = __unimplemented()
		override fun fileTree(args: MutableMap<String, *>): ConfigurableFileTree = __unimplemented()
		override fun zipTree(zipPath: Any): FileTree = __unimplemented()
		override fun tarTree(tarPath: Any): FileTree = __unimplemented()
		override fun <T: Any?> provider(value: Callable<T>): Provider<T> = __unimplemented()
		override fun getProviders(): ProviderFactory = __unimplemented()
		override fun getObjects(): ObjectFactory = __unimplemented()
		override fun getLayout(): ProjectLayout = __unimplemented()
		override fun mkdir(path: Any): File = __unimplemented()
		override fun delete(vararg paths: Any?): Boolean = __unimplemented()
		override fun delete(action: Action<in DeleteSpec>): WorkResult = __unimplemented()
		override fun javaexec(closure: Closure<*>): ExecResult = __unimplemented()
		override fun javaexec(action: Action<in JavaExecSpec>): ExecResult = __unimplemented()
		override fun exec(closure: Closure<*>): ExecResult = __unimplemented()
		override fun exec(action: Action<in ExecSpec>): ExecResult = __unimplemented()
		override fun absoluteProjectPath(path: String): String = __unimplemented()
		override fun relativeProjectPath(path: String): String = __unimplemented()
		override fun getAnt(): AntBuilder = __unimplemented()
		override fun createAntBuilder(): AntBuilder = __unimplemented()
		override fun ant(configureClosure: Closure<*>): AntBuilder = __unimplemented()
		override fun ant(configureAction: Action<in AntBuilder>): AntBuilder = __unimplemented()
		override fun getConfigurations(): ConfigurationContainer = __unimplemented()
		override fun configurations(configureClosure: Closure<*>): Unit = __unimplemented()
		override fun getArtifacts(): ArtifactHandler = __unimplemented()
		override fun artifacts(configureClosure: Closure<*>): Unit = __unimplemented()
		override fun artifacts(configureAction: Action<in ArtifactHandler>): Unit = __unimplemented()
		override fun getConvention(): Convention = __unimplemented()
		override fun depthCompare(p0: Project): Int = __unimplemented()
		override fun getDepth(): Int = __unimplemented()
		override fun getTasks(): TaskContainer = __unimplemented()
		override fun subprojects(action: Action<in Project>): Unit = __unimplemented()
		override fun subprojects(configureClosure: Closure<*>): Unit = __unimplemented()
		override fun allprojects(action: Action<in Project>): Unit = __unimplemented()
		override fun allprojects(configureClosure: Closure<*>): Unit = __unimplemented()
		override fun beforeEvaluate(action: Action<in Project>): Unit = __unimplemented()
		override fun beforeEvaluate(closure: Closure<*>): Unit = __unimplemented()
		override fun afterEvaluate(action: Action<in Project>): Unit = __unimplemented()
		override fun afterEvaluate(closure: Closure<*>): Unit = __unimplemented()
		override fun hasProperty(propertyName: String): Boolean = __unimplemented()
		override fun getProperties(): MutableMap<String, *> = __unimplemented()
		override fun property(propertyName: String): Any? = __unimplemented()
		override fun findProperty(propertyName: String): Any? = __unimplemented()
		override fun getLogger(): Logger = __unimplemented()
		override fun getGradle(): Gradle = __unimplemented()
		override fun getLogging(): LoggingManager = __unimplemented()
		override fun configure(`object`: Any, configureClosure: Closure<*>): Any = __unimplemented()
		override fun configure(objects: MutableIterable<*>, configureClosure: Closure<*>): MutableIterable<*> = __unimplemented()
		override fun <T: Any?> configure(objects: MutableIterable<T>, configureAction: Action<in T>): MutableIterable<T> = __unimplemented()
		override fun getRepositories(): RepositoryHandler = __unimplemented()
		override fun repositories(configureClosure: Closure<*>): Unit = __unimplemented()
		override fun getDependencies(): DependencyHandler = __unimplemented()
		override fun dependencies(configureClosure: Closure<*>): Unit = __unimplemented()
		override fun getBuildscript(): ScriptHandler = __unimplemented()
		override fun buildscript(configureClosure: Closure<*>): Unit = __unimplemented()
		override fun copy(closure: Closure<*>): WorkResult = __unimplemented()
		override fun copy(action: Action<in CopySpec>): WorkResult = __unimplemented()
		override fun copySpec(closure: Closure<*>): CopySpec = __unimplemented()
		override fun copySpec(action: Action<in CopySpec>): CopySpec = __unimplemented()
		override fun copySpec(): CopySpec = __unimplemented()
		override fun sync(action: Action<in CopySpec>): WorkResult = __unimplemented()
		override fun getState(): ProjectState = __unimplemented()
		override fun <T: Any?> container(type: Class<T>): NamedDomainObjectContainer<T> = __unimplemented()
		override fun <T: Any?> container(type: Class<T>, factory: NamedDomainObjectFactory<T>): NamedDomainObjectContainer<T> = __unimplemented()
		override fun <T: Any?> container(type: Class<T>, factoryClosure: Closure<*>): NamedDomainObjectContainer<T> = __unimplemented()
		override fun getResources(): ResourceHandler = __unimplemented()
		override fun getComponents(): SoftwareComponentContainer = __unimplemented()
		override fun getNormalization(): InputNormalizationHandler = __unimplemented()
		override fun normalization(configuration: Action<in InputNormalizationHandler>): Unit = __unimplemented()
		override fun dependencyLocking(configuration: Action<in DependencyLockingHandler>): Unit = __unimplemented()
		override fun getDependencyLocking(): DependencyLockingHandler = __unimplemented()
	}
	open class UnimplementedGradle: Gradle {
		override fun getPlugins(): PluginContainer = __unimplemented()
		override fun apply(closure: Closure<*>) = __unimplemented<Unit>()
		override fun apply(action: Action<in ObjectConfigurationAction>) = __unimplemented<Unit>()
		override fun apply(options: MutableMap<String, *>) = __unimplemented<Unit>()
		override fun getPluginManager(): PluginManager = __unimplemented()
		override fun getGradleVersion(): String = __unimplemented()
		override fun getGradleUserHomeDir(): File = __unimplemented()
		override fun getGradleHomeDir(): File? = __unimplemented()
		override fun getParent(): Gradle? = __unimplemented()
		override fun getRootProject(): Project = __unimplemented()
		override fun rootProject(action: Action<in Project>) = __unimplemented<Unit>()
		override fun allprojects(action: Action<in Project>) = __unimplemented<Unit>()
		override fun getTaskGraph(): TaskExecutionGraph = __unimplemented()
		override fun getStartParameter(): StartParameter = __unimplemented()
		override fun addProjectEvaluationListener(listener: ProjectEvaluationListener): ProjectEvaluationListener = __unimplemented()
		override fun removeProjectEvaluationListener(listener: ProjectEvaluationListener) = __unimplemented<Unit>()
		override fun beforeProject(closure: Closure<*>) = __unimplemented<Unit>()
		override fun beforeProject(action: Action<in Project>) = __unimplemented<Unit>()
		override fun afterProject(closure: Closure<*>) = __unimplemented<Unit>()
		override fun afterProject(action: Action<in Project>) = __unimplemented<Unit>()
		override fun beforeSettings(closure: Closure<*>) = __unimplemented<Unit>()
		override fun beforeSettings(action: Action<in Settings>) = __unimplemented<Unit>()
		override fun settingsEvaluated(closure: Closure<*>) = __unimplemented<Unit>()
		override fun settingsEvaluated(action: Action<in Settings>) = __unimplemented<Unit>()
		override fun projectsLoaded(closure: Closure<*>) = __unimplemented<Unit>()
		override fun projectsLoaded(action: Action<in Gradle>) = __unimplemented<Unit>()
		override fun projectsEvaluated(closure: Closure<*>) = __unimplemented<Unit>()
		override fun projectsEvaluated(action: Action<in Gradle>) = __unimplemented<Unit>()
		override fun buildFinished(closure: Closure<*>) = __unimplemented<Unit>()
		override fun buildFinished(action: Action<in BuildResult>) = __unimplemented<Unit>()
		override fun addBuildListener(buildListener: BuildListener) = __unimplemented<Unit>()
		override fun addListener(p0: Any) = __unimplemented<Unit>()
		override fun removeListener(p0: Any) = __unimplemented<Unit>()
		override fun useLogger(p0: Any) = __unimplemented<Unit>()
		override fun getGradle(): Gradle = __unimplemented()
		override fun getSharedServices(): BuildServiceRegistry = __unimplemented()
		override fun getIncludedBuilds(): MutableCollection<IncludedBuild> = __unimplemented()
		override fun includedBuild(p0: String): IncludedBuild = __unimplemented()
	}
}
