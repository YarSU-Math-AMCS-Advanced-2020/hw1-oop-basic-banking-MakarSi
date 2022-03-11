object ClientStorage{
    var client_base: MutableMap<Int, Client> = mutableMapOf()
    fun add_client(client: Client){
        client_base[client.id] = client
    }
}

object AccountStorage{
    var account_base: MutableMap<Int, Account> = mutableMapOf()
    fun add_account(account: Account){
        account_base[account.id] = account
    }
    fun del_account(account: Account){
        account_base.remove(account.id)
    }
}

object CardStorage{
    var card_base: MutableMap<Int, Card> = mutableMapOf()

    fun open_card(account: Account, pay_system: Card.PaySystem){
        var card = Card(account, pay_system)
        account.card = null
        card_base[card.id] = card
    }

    fun close_card(card: Card){
        card.account.card = null
        card_base.remove(card.id)
    }
}

object TransactionStorage{
    var transaction_base: MutableMap<Int, Transaction> = mutableMapOf()
    fun add_transaction(transaction: Transaction){
        transaction_base[transaction.id] = transaction
    }
}