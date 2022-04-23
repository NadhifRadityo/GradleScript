plugins {
    id("java")
    id("java-gradle-plugin")
    id("java-test-fixtures")
    kotlin("jvm") version "1.6.0"
}

group = "io.github.NadhifRadityo"
version = "LATEST"

repositories {
    mavenCentral()
}

val unitTestSourceSet: SourceSet = sourceSets.getByName("test")
val integrationTestSourceSet: SourceSet = sourceSets.create("integrationTest")
val functionalTestSourceSet: SourceSet = sourceSets.create("functionalTest")

fun addTestSourceSets(sourceSet: SourceSet, fromSourceSet: SourceSet) {
    sourceSet.compileClasspath += fromSourceSet.compileClasspath
    sourceSet.runtimeClasspath += fromSourceSet.runtimeClasspath
}
fun DependencyHandlerScope.testsCompileOnly(dependencyNotation: Any) {
    "testCompileOnly"(dependencyNotation)
    "testFixturesCompileOnly"(dependencyNotation)
}
fun DependencyHandlerScope.testsRuntimeOnly(dependencyNotation: Any) {
    "testRuntimeOnly"(dependencyNotation)
    "testFixturesRuntimeOnly"(dependencyNotation)
}
fun DependencyHandlerScope.removeRuntime(dependencyNotation: Any) {
    val dependency = create(dependencyNotation)
    configurations.implementation.get().dependencies.remove(dependency)
    configurations.runtimeOnly.get().dependencies.remove(dependency)
    configurations.api.get().dependencies.remove(dependency)
}

dependencies {
    compileOnly(gradleApi())
    compileOnly(gradleKotlinDsl())
    compileOnly(localGroovy())

    addTestSourceSets(integrationTestSourceSet, unitTestSourceSet)
    addTestSourceSets(functionalTestSourceSet, integrationTestSourceSet)
    testsCompileOnly(gradleTestKit())
    testsCompileOnly("org.junit.jupiter:junit-jupiter:5.8.2")

    testsRuntimeOnly(gradleApi())
    testsRuntimeOnly(gradleKotlinDsl())
    testsRuntimeOnly(localGroovy())
    testsRuntimeOnly(gradleTestKit())
    testsRuntimeOnly("org.junit.jupiter:junit-jupiter:5.8.2")

    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.google.code.gson:gson:2.8.9")

    // Automatically added by java-gradle-plugin, we don't want this.
    removeRuntime(gradleApi())
    removeRuntime(gradleKotlinDsl())
    removeRuntime(localGroovy())
    removeRuntime(gradleTestKit())
}

fun newTestTask(sourceSet: SourceSet, runAfter: TaskProvider<Test>? = null): TaskProvider<Test> {
    return tasks.register<Test>(sourceSet.name) {
        group = "verification"
        description = "Runs the ${sourceSet.name} tests."
        testClassesDirs = sourceSet.output.classesDirs
        classpath = sourceSet.runtimeClasspath
        if(runAfter != null)
            shouldRunAfter(runAfter)
    }
}

val integrationTestTask = newTestTask(integrationTestSourceSet, tasks.named<Test>("test"))
val functionalTestTask = newTestTask(functionalTestSourceSet, integrationTestTask)
tasks.check {
    dependsOn(integrationTestTask)
    dependsOn(functionalTestTask)
}

tasks.withType<Test> {
    useJUnitPlatform {
        if(System.getProperty("DEBUG", "false") == "true") {
            jvmArgs("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005")
        }
    }
    testLogging {
        events("PASSED", "SKIPPED", "FAILED")
    }
}
tasks.withType<Jar> {
    archiveFileName.convention(provider {
        var name = archiveBaseName.getOrNull() ?: ""
        val classifier = archiveClassifier.getOrNull()
        val extension = archiveExtension.getOrNull()
        if(GUtil.isTrue(classifier)) name += "-$classifier"
        if(GUtil.isTrue(extension)) name += ".$extension"
        name
    })
}
tasks.getByName<Jar>("jar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(configurations.runtimeClasspath.get().files.map { if(it.isDirectory) it else zipTree(it) })
}

gradlePlugin {
    plugins {
        // These plugins won't be published, so those plugin IDs are going to be fine.
        create("GradleSettingsPlugin") {
            id = "GradleScript.GradleSettingsPlugin"
            implementationClass = "GradleScript.GradleSettingsPlugin"
        }
        create("GradleProjectPlugin") {
            id = "GradleScript.GradleProjectPlugin"
            implementationClass = "GradleScript.GradleProjectPlugin"
        }
    }
}
