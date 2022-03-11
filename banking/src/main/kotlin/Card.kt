import java.time.LocalDateTime

class Card(var account: Account, var pay_system: PaySystem) {
    enum class PaySystem{
        Lisa, MasterBart, HoMir
    }
    var limit: Double? = null
    val id = hashCode()
    val expiration_date = LocalDateTime.now().plusYears(4)

    fun rebinding(account_to: Account): Boolean{
        return if (account_to.card == null) {
            account_to.card = this
            account.card = null
            account = account_to
            true
        } else false
    }
}
