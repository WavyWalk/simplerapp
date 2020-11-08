package user

import account.Account
import at.wavywalk.simpler.orm.annotations.*
import at.wavywalk.simpler.orm.generated.user.UserRecord
import org.jooq.generated.tables.Users

@IsModel(jooqTable = Users::class)
class User {

    val record: UserRecord by lazy { UserRecord(this) }

    @TableField(name = "ID")
    @IsPrimaryKey
    var id: Long? = null

    @HasOne(model = Account::class, fieldOnThat = "USER_ID", fieldOnThis = "ID")
    var account: Account? = null

    @TableField(name = "NAME")
    var name: String? = null

    companion object {
        val table = Users.USERS
    }

}