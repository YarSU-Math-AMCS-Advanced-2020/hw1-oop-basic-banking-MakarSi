import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Card(var account_id: Int, var pay_system: PaySystem) {
    enum class PaySystem{ Lisa, MasterBart, HoMir }
    var limit: Double? = null
    val id = hashCode()
    val expiration_date = LocalDateTime.now().plusYears(4)

    init{
        var account: Account? = AccountStorage[account_id]
        if (account != null){
            if (!account.card)
                account.card = true
            else throw Exception("Card was already opened")
        }
        else throw Exception("Account doesnt exists")
    }

    fun rebinding(account_to_id: Int){
        var account_to: Account? = AccountStorage[account_to_id]
        if (account_to != null){
            if (!account_to.card) {
                account_to.card = true
                AccountStorage[account_id]?.card = false
                account_id = account_to_id
            }
            else throw Exception("Card for account_to already exists")
        }
        else throw Exception("Account doesnt exists")
    }

    override fun toString(): String = "Linked account:$account_id\nPaySystem$pay_system\n" +
            "Expiration date:\n${expiration_date.format(DateTimeFormatter.ISO_DATE)}\nLimit:$limit"
}
