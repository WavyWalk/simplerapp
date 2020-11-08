package at.wavywalk.simpler.orm.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
annotation class BelongsToPolymorphic(
        val models: Array<KClass<*>>,
        val fieldOnThis: String,
        val fieldOnThat: String,
        val polymorphicTypeField: String
)