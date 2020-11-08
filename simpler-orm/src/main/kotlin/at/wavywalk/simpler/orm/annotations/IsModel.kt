package at.wavywalk.simpler.orm.annotations

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class IsModel(val jooqTable: KClass<*>, val generateRequestParamsWrapper: Boolean = false)

