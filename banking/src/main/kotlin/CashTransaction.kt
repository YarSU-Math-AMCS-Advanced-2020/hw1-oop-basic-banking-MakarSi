import java.time.LocalDateTime

class CashTransaction(var account: Account,
                      val amount: Double,
                      val type: TransType,
                      val place_to_withdraw: String,
                      val ATM_num: String,
                      ignore_limit: Boolean): Transaction(){

    enum class TransType{ DEPOSIT, WITHDRAWAL }

    enum class Status{ ACCEPTED, REJECTED }

    val time: LocalDateTime = LocalDateTime.now()
    val status: Status
    override val id = hashCode()

    init{
        status = if (type == TransType.WITHDRAWAL){
            if (amount > account.balance && !ignore_limit || (account.limit ?: Double.MAX_VALUE) <= amount)
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

    override fun toString() = "Account: ${account.id}\nTime: $time\nAmount: $amount\nCurrency: " +
                "${account.currency}\nPlace of withdraw: $place_to_withdraw\n" +
                "ATM number: $ATM_num\nStatus: $status"
}
