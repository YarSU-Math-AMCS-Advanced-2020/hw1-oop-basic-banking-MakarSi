abstract class Client{
    var accounts_map = mutableMapOf<Int, Account>()
    abstract val id: Int

    abstract override fun toString(): String

    fun open_debit_account(currency: Currency): DebitAccount{
        val debit_account = DebitAccount(currency)
        accounts_map[debit_account.hashCode()] = debit_account
        AccountStorage.add_account(debit_account)
        return debit_account
    }

    fun close_account(account_to_del: Account, account_to_rm: Account): Boolean{
        val transaction = TransactionBWClients(account_to_del.balance, account_to_del, account_to_rm)
        TransactionStorage.add_transaction(transaction)
        return if(transaction.status == TransactionBWClients.Status.COMPLETED) {
            accounts_map.remove(account_to_del.id, account_to_del)
            AccountStorage.del_account(account_to_del)
            true
        }
        else false
    }

    fun close_account(account: Account, place_to_withdraw: String, ATM_num: String): Boolean{
        val transaction = CashTransaction(account, account.balance,
            CashTransaction.TransType.WITHDRAWAL, place_to_withdraw, ATM_num, true)
        TransactionStorage.add_transaction(transaction)
        return if(transaction.status == CashTransaction.Status.ACCEPTED){
            accounts_map.remove(account.id, account)
            AccountStorage.del_account(account)
            true
        }
        else false
    }
}
