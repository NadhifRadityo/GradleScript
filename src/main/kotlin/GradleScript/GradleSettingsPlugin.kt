package GradleScript

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.logging.configuration.ShowStacktrace
import org.gradle.kotlin.dsl.apply

class GradleSettingsPlugin: Plugin<Settings> {
	override fun apply(target: Settings) {
		val gradle = target.gradle
		gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS
		gradle.allprojects {
			it.apply(plugin="GradleScript.GradleProjectPlugin")
		}
	}
}
