import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionBWClients(private val amount: Double, private val accountFrom: Account, private val accountTo: Account,
                           private var cardFrom: Card? = null, private var cardTo: Card? = null): Transaction(){

    enum class Status { FOR_PROCESSING, PROCESSING, COMPLETED, REJECTED }

    var status = Status.FOR_PROCESSING
    private val time = LocalDateTime.now()
    private val currency = accountFrom.currency
    private var limit: Double = cardFrom?.limit ?: accountFrom.limit ?: 10e9
    override val id = hashCode()

    init {
        status = Status.PROCESSING
        status = if (accountFrom.currency == accountTo.currency &&
            limit >= amount && accountFrom.withdrawal(amount, currency)){
            accountTo.deposit(amount, currency)
            Status.COMPLETED
        } else
            Status.REJECTED
    }

    override fun toString(): String {
        var string = if (cardFrom != null && cardTo != null)
            "Card from: $cardFrom, card to: $cardTo\n"
        else "Account from: ${accountFrom.id}, account to: ${accountTo.id}\n"
        string += "Time: ${time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}" +
                "\nAmount: $amount\nCurrency: $currency\nStatus: $status\nId: $id"
        return string
    }
}