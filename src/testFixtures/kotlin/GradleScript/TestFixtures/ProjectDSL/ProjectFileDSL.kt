package GradleScript.TestFixtures.ProjectDSL

import GradleScript.TestFixtures.FileDSL.FileDSL
import GradleScript.TestFixtures.FileDSL.FileDSLExpression
import GradleScript.TestFixtures.FileDSL.FileDSLGenerator
import GradleScript.TestFixtures.FileDSL.FileDSLImpl
import java.io.File

typealias ProjectFileDSLExpression<RECEIVER, RESULT> = FileDSLExpression<ProjectFileDSL<RECEIVER>, RESULT>
typealias ProjectFileDSLGenerator<RECEIVER> = FileDSLGenerator<ProjectFileDSL<RECEIVER>>
@Deprecated("Use ProjectFileNodeDirectoryDSL/ProjectFileNodeFileDSL/ProjectFileNodeGenericDSL")
interface ProjectFileDSL<RECEIVER: ProjectFileDSL<RECEIVER>>: FileDSL<ProjectFileDSL<RECEIVER>> {

}
open class ProjectFileDSLImpl<RECEIVER: ProjectFileDSL<RECEIVER>>(
	__file_dsl_generator: ProjectFileDSLGenerator<RECEIVER>,
	__file_dsl_file: File
):
	ProjectFileDSL<RECEIVER>, FileDSL<ProjectFileDSL<RECEIVER>>
		by FileDSLImpl(__file_dsl_generator, __file_dsl_file)
{ }
