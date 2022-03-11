abstract class Account(val currency: Currency){
    var card: Card? = null
    var limit: Double? = null
    var balance: Double = 0.0
    abstract val id: Int

    abstract fun deposit(amount: Double, currency: Currency): Boolean
    abstract fun withdrawal(amount: Double, currency: Currency): Boolean
}