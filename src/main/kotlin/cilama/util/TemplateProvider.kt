package cilama.util

import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets
import javax.annotation.processing.FilerException
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.StandardLocation

class TemplateProvider {

    fun readTemplate(templates: List<String>, processingEnv: ProcessingEnvironment?): String {

        if (processingEnv == null) {
            return readDefaultTemplate()
        }

        for (template in templates) {
            try {
                val templateFileObject = processingEnv.filer.getResource(StandardLocation.CLASS_OUTPUT, "", template)
                return templateFileObject.getCharContent(true).toString()
            } catch (e: FilerException) {
                // TODO: handle.. or log..
            }
        }

        return readDefaultTemplate()
    }

    private fun readDefaultTemplate(): String {
        val resource = javaClass.classLoader.getResource("templates/md/gtd-template.md")
        return IOUtils.toString(resource, StandardCharsets.UTF_8)
    }
}