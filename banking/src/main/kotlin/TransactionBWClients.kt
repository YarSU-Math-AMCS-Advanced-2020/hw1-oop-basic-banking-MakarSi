import java.time.LocalDateTime
class TransactionBWClients(val amount: Double, val account_from: Account, val account_to: Account): Transaction(){

    enum class Status { FOR_PROCESSING, PROCESSING, COMPLETED, REJECTED }

    var status: Status = Status.FOR_PROCESSING
    val time: LocalDateTime = LocalDateTime.now()
    val currency: Currency = account_from.currency
    var limit: Double = account_from.limit ?: 1000000000.0
    var card_from: Card? = null
    var card_to: Card? = null
    override val id = hashCode()

    constructor(amount: Double, card_to: Card, card_from: Card):
            this(amount, card_from.account, card_to.account){
        this.card_from = card_from
        this.card_to = card_to
        this.limit = card_from.limit ?: limit
    }
    //TODO: limit - неверная последовательность присвоения(порядок вызова конструкторов/инициализации)
    init {
        if (account_from.currency == account_to.currency && limit > amount && account_from.withdrawal(amount, currency)) {
            status = Status.PROCESSING
            account_to.deposit(amount, currency)
            status = Status.COMPLETED
        } else
            status = Status.REJECTED
    }

    override fun toString(): String {
        var string = if (card_from != null && card_to != null)
            "Card from: $card_from, card to: $card_to\n"
        else "Account from: ${account_from.id}, account to: ${account_to.id}\n"
        string += "Time: $time\nAmount: $amount\nCurrency: $currency\nStatus: $status"
        return string
    }
}