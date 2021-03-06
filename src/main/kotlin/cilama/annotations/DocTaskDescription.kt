package cilama.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class DocTaskDescription(
        val purpose: String,
        val name: String
)
