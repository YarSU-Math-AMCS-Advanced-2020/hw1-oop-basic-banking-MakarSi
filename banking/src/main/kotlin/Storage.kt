object ClientStorage{
    private var clientList: MutableMap<Int, Client> = mutableMapOf()

    operator fun get(index: Int): Client {
        return clientList[index] ?: throw Exception("Client doesnt exists")
    }

    fun personRegister(person: Person){
        clientList[person.id] = person
    }

    fun legalEntityRegister(legalEntity: LegalEntity){
        clientList[legalEntity.id] = legalEntity
    }
}

object AccountStorage{
    private var accountList: MutableMap<Int, Account> = mutableMapOf()

    operator fun get(index: Int): Account {
        return accountList[index] ?: throw Exception("Account doesnt exists")
    }

    fun openDebitAccount(debitAccount: DebitAccount){
        accountList[debitAccount.id] = debitAccount
    }

    fun closeAccount(accountToDelId: Int, accountToRmId: Int){
        val transaction = TransactionBWClients(this[accountToDelId].balance, this[accountToDelId], this[accountToRmId])
        TransactionStorage[transaction.id] = transaction
        if (transaction.status == TransactionBWClients.Status.COMPLETED)
            accountList.remove(accountToDelId)
        else throw Exception("Transaction rejected")
    }

    fun closeAccount(accountId: Int, placeToWithdraw: String, ATM_num: String){
        val transaction = CashTransaction(this[accountId], this[accountId].balance,
            CashTransaction.TransType.WITHDRAWAL, placeToWithdraw, ATM_num, true)
        TransactionStorage[transaction.id] = transaction
        if(transaction.status == CashTransaction.Status.ACCEPTED)
            accountList.remove(accountId)
        else throw Exception("Transaction rejected")
    }
}

object CardStorage {
    private var cardList: MutableMap<Int, Card> = mutableMapOf()

    operator fun get(index: Int): Card{
        return cardList[index] ?: throw Exception("Client doesnt exists")
    }

    fun openCard(card: Card){
        cardList[card.id] = card
    }

    fun closeCard(card_id: Int){
       if (cardList.remove(card_id) == null)
           throw Exception("Card doesnt exists")
    }
}

object TransactionStorage{
    private var transactionList: MutableMap<Int, Transaction> = mutableMapOf()

    operator fun get(index: Int): Transaction{
        return transactionList[index] ?: throw Exception("Transaction doesnt exists")
    }

    operator fun set(index: Int, transaction: Transaction) {
        transactionList[index] = transaction
    }
}