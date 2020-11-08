package at.wavywalk.simpler.orm.templatedatamodels.model

class PackagesBean
(
        val baseGenerated: String,
        val jooqGeneratedTable: String,
        val model: String,
        val fieldTypes: MutableSet<String>,
        val jooqTablesRoot: String
)
{
    lateinit var associatedModelTypesToImport: MutableSet<String>
    val jooqGeneratedtableSimpleName = jooqGeneratedTable.split('.').last()

}