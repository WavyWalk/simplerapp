package user

import account.Account
import at.wavywalk.simpler.orm.generated.user.UserRecord

object UserRepository {


    fun findById(userId: Long): User? {
        return UserRecord.GET()
            .whereIdEq(userId)
            .preload {
                it.account()
            }
            .executeGetFirstOrNull()
    }

}
