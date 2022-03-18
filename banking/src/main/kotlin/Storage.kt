object ClientStorage{
    private var client_list: MutableMap<Int, Client> = mutableMapOf()

    operator fun get(index: Int): Client?{
        return client_list[index]
    }

    fun person_register(person: PersonalInfo, address: Address, phone_num: String, passport: String){
        var person = Person(person, address, phone_num, passport)
        client_list[person.id] = person
    }
}

object AccountStorage{
    private var account_list: MutableMap<Int, Account> = mutableMapOf()

    operator fun get(index: Int): Account?{
        return account_list[index]
    }

    fun open_debit_account(client_id: Int, currency: Currency){
        val debit_account = DebitAccount(client_id, currency)
        account_list[debit_account.id] = debit_account
    }

    fun close_account(account_to_del_id: Int, account_to_rm_id: Int): Boolean {
        val account_to_del = account_list[account_to_del_id]
        val account_to_rm = account_list[account_to_rm_id]
        if (account_to_del == null || account_to_rm == null)
            throw Exception("One of accounts doesnt exists")
        val transaction = TransactionBWClients(account_to_del.balance, account_to_del, account_to_rm)
        TransactionStorage[transaction.id] = transaction
        return if (transaction.status == TransactionBWClients.Status.COMPLETED) {
            account_list.remove(account_to_del.id)
            true
        } else false
    }

    fun close_account(account_id: Int, place_to_withdraw: String, ATM_num: String): Boolean{
        val account = account_list[account_id] ?: throw Exception("Account doesnt exists")
        val transaction = CashTransaction(account, account.balance,
            CashTransaction.TransType.WITHDRAWAL, place_to_withdraw, ATM_num, true)
        TransactionStorage[transaction.id] = transaction
        return if(transaction.status == CashTransaction.Status.ACCEPTED){
            account_list.remove(account_id)
            true
        }
        else false
    }

}

object CardStorage {
    private var card_list: MutableMap<Int, Card> = mutableMapOf()

    operator fun get(index: Int): Card?{
        return card_list[index]
    }

    fun open_card(account_id: Int, paySystem: Card.PaySystem){
        val account = AccountStorage[account_id] ?: throw Exception("Account doesnt exists")
        var card = Card(account_id, paySystem)
        card_list[card.id] = card
    }
}

object TransactionStorage{
    private var transaction_list: MutableMap<Int, Transaction> = mutableMapOf()

    operator fun get(index: Int): Transaction?{
        return transaction_list[index]
    }

    operator fun set(index: Int, transaction: Transaction) {
        transaction_list[index] = transaction
    }
}