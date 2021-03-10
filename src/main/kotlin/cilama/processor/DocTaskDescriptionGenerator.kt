package cilama.processor


import cilama.annotations.DocTaskDescription
import cilama.util.TemplateProvider
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

const val MD_TEMPLATE = "gtd-template.md"

@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
class DocTaskDescriptionGenerator : AbstractProcessor() {

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(DocTaskDescription::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        roundEnv?.getElementsAnnotatedWith(DocTaskDescription::class.java)
                ?.forEach {
                    val className = it.simpleName.toString()
                    val template = TemplateProvider().readTemplate(mutableListOf(MD_TEMPLATE), processingEnv)

                    val pack = processingEnv.elementUtils.getPackageOf(it).toString()
                    replaceTokenInTemplate(className, pack)
                }
        return true
    }

    private fun replaceTokenInTemplate(className: String, pack: String) {

    }

}