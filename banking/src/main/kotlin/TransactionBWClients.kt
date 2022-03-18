import java.time.LocalDateTime

class TransactionBWClients(val amount: Double, val account_from: Account, val account_to: Account,
                           var card_from: Card? = null, var card_to: Card? = null): Transaction(){

    enum class Status { FOR_PROCESSING, PROCESSING, COMPLETED, REJECTED }

    var status = Status.FOR_PROCESSING
    val time = LocalDateTime.now()
    val currency = account_from.currency
    var limit: Double = card_from?.limit ?: account_from.limit ?: 10e9
    override val id = hashCode()

    init {
        status = Status.PROCESSING
        status = if (account_from.currency == account_to.currency &&
            limit >= amount && account_from.withdrawal(amount, currency)){
            account_to.deposit(amount, currency)
            Status.COMPLETED
        } else
            Status.REJECTED
    }

    override fun toString(): String {
        var string = if (card_from != null && card_to != null)
            "Card from: $card_from, card to: $card_to\n"
        else "Account from: ${account_from.id}, account to: ${account_to.id}\n"
        string += "Time: $time\nAmount: $amount\nCurrency: $currency\nStatus: $status"
        return string
    }
}