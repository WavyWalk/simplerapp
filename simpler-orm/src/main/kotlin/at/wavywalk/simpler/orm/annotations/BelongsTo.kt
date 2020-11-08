package at.wavywalk.simpler.orm.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class BelongsTo(
        val model: KClass<*>,
        val fieldOnThis: String,
        val fieldOnThat: String
)