package account

import at.wavywalk.simpler.orm.generated.account.AccountRecord

object AccountRepository {

    fun findByEmail(email: String): Account? {
        val email = email.trim()
        val account = AccountRecord.GET()
            .whereEmailEq(email)
            .executeGetFirstOrNull()
        return account
    }


}
