package at.wavywalk.simpler.orm.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class HasOne(
        val model: KClass<*>,
        val fieldOnThis: String,
        val fieldOnThat: String,
        val onDelete: String = "none"
)