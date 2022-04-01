import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CashTransaction(private var account: Account,
                      private val amount: Double,
                      private val type: TransType,
                      private val placeOfTransaction: String,
                      private val atmNum: String,
                      ignoreLimit: Boolean): Transaction(){

    enum class TransType{ DEPOSIT, WITHDRAWAL }

    enum class Status{ ACCEPTED, REJECTED }

    private val time: LocalDateTime = LocalDateTime.now()
    val status: Status
    override val id = hashCode()

    init{
        status = if (type == TransType.WITHDRAWAL){
            if (amount > account.balance && !ignoreLimit || (account.limit ?: Double.MAX_VALUE) <= amount)
                Status.REJECTED
            else {
                account.withdrawal(amount, account.currency)
                Status.ACCEPTED
            }
        } else{
            account.deposit(amount, account.currency)
            Status.ACCEPTED
        }
    }

    override fun toString(): String {
        var string = "Account: ${account.id}\nTime: " +
                "${time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}\nAmount: " +
                "$amount\nCurrency: ${account.currency}\n"

        string += if (type == TransType.WITHDRAWAL) "Place of withdraw: $placeOfTransaction\n"
        else "Place of deposit: $placeOfTransaction\n"

        string += "ATM number: $atmNum\nStatus: $status\nId: $id"
        return string
    }
}