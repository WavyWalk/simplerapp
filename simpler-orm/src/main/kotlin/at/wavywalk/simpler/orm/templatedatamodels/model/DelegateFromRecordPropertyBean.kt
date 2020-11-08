package at.wavywalk.simpler.orm.templatedatamodels.model

import javax.lang.model.element.Element

class DelegateFromRecordPropertyBean
constructor(
        val delegatesType: String,
        val element: Element,
        val property: String,
        val type: String,
        val nonNullableType: String
) {
    var parameters = mutableMapOf<String,String>()
        get(){
            if (delegatesType != "FUNCTION") {
                throw IllegalStateException()
            }
            return field
        }
}