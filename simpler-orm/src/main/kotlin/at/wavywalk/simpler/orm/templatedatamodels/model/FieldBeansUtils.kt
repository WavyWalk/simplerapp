package at.wavywalk.simpler.orm.templatedatamodels.model

object FieldBeansUtils {

    fun getFieldBeanByName(fieldBeans: MutableList<FieldBean>, fieldName: String): FieldBean {
        var requiredFieldBean: FieldBean? = null

        for (fieldBean in fieldBeans) {
            if (fieldBean.tableFieldName == fieldName) {
                requiredFieldBean = fieldBean
                break
            }
        }

        if (requiredFieldBean == null) {
            throw Throwable("no such fieldBean found: ${fieldName}")
        } else {
            return requiredFieldBean
        }
    }

}