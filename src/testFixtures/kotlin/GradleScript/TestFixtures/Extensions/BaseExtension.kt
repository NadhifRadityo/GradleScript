package GradleScript.TestFixtures.Extensions

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL

abstract class BaseExtension: Extension, BeforeAllCallback, AfterAllCallback {

	companion object {
		val <T: BaseExtension> T.id: String
			get() = this.javaClass.name
		val <T: BaseExtension> T.namespace: ExtensionContext.Namespace
			get() = ExtensionContext.Namespace.create(id)

		fun <T: BaseExtension> T.getCurrentStore(context: ExtensionContext): ExtensionContext.Store {
			return context.root.getStore(namespace)
		}
		fun <T: BaseExtension> T.getGlobalStore(context: ExtensionContext): ExtensionContext.Store {
			return context.root.getStore(GLOBAL)
		}
	}

	final override fun beforeAll(context: ExtensionContext) {
		val globalStore = getGlobalStore(context)
		if(globalStore.get(id) != null) return
		globalStore.put(id, this)
		construct(context)
	}
	final override fun afterAll(context: ExtensionContext) {
		val globalStore = getGlobalStore(context)
		if(globalStore.get(id) == null) return
		destruct(context)
		globalStore.put(id, null)
	}

	abstract fun construct(context: ExtensionContext)
	abstract fun destruct(context: ExtensionContext)
}
