package GradleScript

import GradleScript.Common.construct
import GradleScript.Common.context
import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleProjectPlugin: Plugin<Project> {
	override fun apply(target: Project) {
		context(target) {
			construct()
		}
	}
}
