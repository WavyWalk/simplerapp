package account

import at.wavywalk.simpler.orm.annotations.BelongsTo
import at.wavywalk.simpler.orm.annotations.IsModel
import at.wavywalk.simpler.orm.annotations.IsPrimaryKey
import at.wavywalk.simpler.orm.annotations.TableField
import at.wavywalk.simpler.orm.generated.account.AccountRecord
import org.jooq.generated.tables.Accounts
import user.User

@IsModel(jooqTable = Accounts::class)
class Account {

    val record by lazy { AccountRecord(this) }

    @IsPrimaryKey
    @TableField("ID")
    var id: Long? = null

    @TableField("USER_ID")
    var userId: Long? = null

    @TableField("EMAIL")
    var email: String? = null

    @TableField("PASSWORD")
    var password: String? = null

    @BelongsTo(model = User::class, fieldOnThis = "USER_ID", fieldOnThat = "ID")
    var user: User? = null

    companion object {

        val table = Accounts.ACCOUNTS!!

    }

}