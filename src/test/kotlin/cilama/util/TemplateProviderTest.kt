package cilama.util

import cilama.processor.MD_TEMPLATE
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*
import javax.annotation.processing.Filer
import javax.annotation.processing.FilerException
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.*

class TemplateProviderTest {

    private val sut = TemplateProvider()

    @Test
    @DisplayName("Provide gtg-template.md")
    fun shouldProvideDefaultTemplate() {
        val template = sut.readTemplate(mutableListOf(MD_TEMPLATE), null)
        Assertions.assertTrue(template.contains("#### generated with gtg-template of gradle-task-documentation plugin"))
    }

    @Test
    @DisplayName("Provide template from processEnv")
    fun shouldProvideTemplateFromProcessEnv() {
        val mockedProcessingEnv = mockk<ProcessingEnv>()
        val mockedFiler = mockk<FilerImpl>()
        val mockedFileObject = mockk<SimpleJavaFileObject>()

        every { mockedFileObject.getCharContent(true) } returns "my-template-content"
        every { mockedFiler.getResource(StandardLocation.CLASS_OUTPUT, "", "my-template.md") } returns mockedFileObject
        every { mockedProcessingEnv.filer } returns mockedFiler

        val template = sut.readTemplate(mutableListOf("my-template.md"), mockedProcessingEnv)
        Assertions.assertEquals("my-template-content", template)
    }

    @Test
    @DisplayName("Provide default template as fallback")
    fun shouldReturnFallbackTemplateWhenDesiredTemplateNotFound() {
        val mockedProcessingEnv = mockk<ProcessingEnv>()
        val mockedFiler = mockk<FilerImpl>()
        val mockedFileObject = mockk<SimpleJavaFileObject>()

        every { mockedFileObject.getCharContent(true) } returns "my-template-content"
        every { mockedFiler.getResource(StandardLocation.CLASS_OUTPUT, "", "my-template.md") } returns mockedFileObject
        every { mockedFiler.getResource(StandardLocation.CLASS_OUTPUT, "", "my-not-existing-template.md") } throws FilerException("not found")
        every { mockedProcessingEnv.filer } returns mockedFiler

        val template = sut.readTemplate(mutableListOf("my-not-existing-template.md"), mockedProcessingEnv)
        Assertions.assertTrue(template.contains("#### generated with gtg-template of gradle-task-documentation plugin"))
    }

    class ProcessingEnv : ProcessingEnvironment {
        override fun getOptions(): MutableMap<String, String> {
            TODO("Not yet implemented")
        }

        override fun getMessager(): Messager {
            TODO("Not yet implemented")
        }

        override fun getFiler(): Filer {
            TODO("Not yet implemented")
        }

        override fun getElementUtils(): Elements {
            TODO("Not yet implemented")
        }

        override fun getTypeUtils(): Types {
            TODO("Not yet implemented")
        }

        override fun getSourceVersion(): SourceVersion {
            TODO("Not yet implemented")
        }

        override fun getLocale(): Locale {
            TODO("Not yet implemented")
        }

    }

    class FilerImpl : Filer {
        override fun createSourceFile(name: CharSequence?, vararg originatingElements: Element?): JavaFileObject {
            TODO("Not yet implemented")
        }

        override fun createClassFile(name: CharSequence?, vararg originatingElements: Element?): JavaFileObject {
            TODO("Not yet implemented")
        }

        override fun createResource(location: JavaFileManager.Location?, moduleAndPkg: CharSequence?, relativeName: CharSequence?, vararg originatingElements: Element?): FileObject {
            TODO("Not yet implemented")
        }

        override fun getResource(location: JavaFileManager.Location?, moduleAndPkg: CharSequence?, relativeName: CharSequence?): FileObject {
            return mockk<SimpleJavaFileObject>()
        }

    }
}