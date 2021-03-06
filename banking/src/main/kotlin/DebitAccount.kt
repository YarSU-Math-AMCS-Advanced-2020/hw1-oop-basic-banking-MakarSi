class DebitAccount(client_id: Int, currency: Currency): Account(client_id, currency){
    override val id: Int = hashCode()

    override fun deposit(amount: Double, currency: Currency): Boolean{
        return if (this.currency != currency)
            false
        else{
            balance += amount
            true
        }
    }

    override fun withdrawal(amount: Double, currency: Currency): Boolean{
        return if (this.currency != currency || this.balance < amount)
            false
        else{
            balance -= amount
            true
        }
    }

    override fun getIdToTransferFrom(): Int = id

    override fun getLimit(): Double = limit ?: 10e9

    override fun toString() = "Balance: $balance, currency: $currency, id: $id, client id: $clientId"
}

//class CreditAccount(currency: Currency): Account(currency){
    //TODO: make credit account
//}