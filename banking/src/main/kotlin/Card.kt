import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Card(var accountId: Int, var paySystem: PaySystem): IEnableToTransfer {
    enum class PaySystem{ Lisa, MasterBart, HoMir }
    var limit: Double? = AccountStorage[accountId].limit
        set(value){
            if (value != null){
                if (value >= 0)
                    field = value
            }
            else field = value
        }
    val id = hashCode()
    private val expirationDate = LocalDateTime.now().plusYears(4)

    init{
        val account = AccountStorage[accountId]
        if (!account.card)
            account.card = true
        else throw Exception("Card was already opened")
    }

    fun rebinding(accountToId: Int){
    val accountTo = AccountStorage[accountToId]
        if (!accountTo.card) {
            accountTo.card = true
            AccountStorage[accountId].card = false
            accountId = accountToId
        }
        else throw Exception("Card for account already exists")
    }

    override fun getIdToTransferFrom(): Int = accountId

    override fun getLimit(): Double = limit ?: 10e9

    override fun toString(): String = "Linked account:$accountId\nPaySystem$paySystem\n" +
            "Expiration date:\n${expirationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}" +
            "\nLimit:$limit"
}
