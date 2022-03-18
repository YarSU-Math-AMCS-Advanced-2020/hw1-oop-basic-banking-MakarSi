abstract class Account(val client_id: Int, val currency: Currency){
    var limit: Double? = null
    var balance: Double = 0.0
    var card: Boolean = false
    abstract val id: Int

    abstract fun deposit(amount: Double, currency: Currency): Boolean
    abstract fun withdrawal(amount: Double, currency: Currency): Boolean
}