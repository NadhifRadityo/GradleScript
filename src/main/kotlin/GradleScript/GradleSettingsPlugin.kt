package GradleScript

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.apply

class GradleSettingsPlugin: Plugin<Settings> {
	override fun apply(target: Settings) {
		val gradle = target.gradle
		gradle.allprojects {
			it.apply(plugin="GradleScript.GradleProjectPlugin")
		}
	}
}
