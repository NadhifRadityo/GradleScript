package GradleScript.TestFixtures.Extensions

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL

abstract class BaseExtension: Extension, BeforeAllCallback, ExtensionContext.Store.CloseableResource {

	final override fun beforeAll(context: ExtensionContext) {
		val globalStore = context.root.getStore(GLOBAL)
		val id = this.javaClass.name
		val value = globalStore.get(id)
		if(value == null) {
			globalStore.put(id, this)
			construct()
		}
	}
	final override fun close() {
		destruct()
	}

	abstract fun construct()
	abstract fun destruct()
}
