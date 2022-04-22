import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionBWClients(private val amount: Double): Transaction(){

    enum class Status { FOR_PROCESSING, PROCESSING, COMPLETED, REJECTED }

    var status = Status.FOR_PROCESSING
    private val time = LocalDateTime.now()
    private val currency = Currency.USD
    private var limit: Double = 10e9
    override val id = hashCode()
    private var accountFromId = -1
    private var accountToId = -1

    constructor(amount: Double, from: IEnableToTransfer, to: IEnableToTransfer) : this(amount){
        accountFromId = from.getIdToTransferFrom()
        accountToId = to.getIdToTransferFrom()
        limit = from.getLimit()
        processing(AccountStorage[accountFromId], AccountStorage[accountToId], limit)
    }

    private fun processing(accountFrom: Account, accountTo: Account, limit: Double){
        status = Status.PROCESSING
        status = if (accountFrom.currency == accountTo.currency &&
            limit >= amount && accountFrom.withdrawal(amount, currency)){
            accountTo.deposit(amount, currency)
            Status.COMPLETED
        } else
            Status.REJECTED
    }

    override fun toString(): String {
        var string = "Account from: ${accountFromId}, account to: ${accountToId}\n"
        string += "Time: ${time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}" +
                "\nAmount: $amount\nCurrency: $currency\nStatus: $status\nId: $id"
        return string
    }
}