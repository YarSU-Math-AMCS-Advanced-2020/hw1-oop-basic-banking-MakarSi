abstract class Account(val clientId: Int, val currency: Currency){
    var limit: Double? = null
        set(value){
            if (value != null){
                if (value >= 0)
                    field = value
            }
            else field = value
        }
    var balance: Double = 0.0
    var card: Boolean = false
    abstract val id: Int

    abstract fun deposit(amount: Double, currency: Currency): Boolean
    abstract fun withdrawal(amount: Double, currency: Currency): Boolean
}