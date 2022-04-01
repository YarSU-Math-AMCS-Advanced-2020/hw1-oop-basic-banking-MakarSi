object ConsoleInterface {
    private var clientIdList: MutableList<Int> = mutableListOf()
    private var accountIdList: MutableList<Int> = mutableListOf()
    private var cardIdList: MutableList<Int> = mutableListOf()
    private var transactionIdList: MutableList<Int> = mutableListOf()

    fun printClientInfo(){ for(id in clientIdList) println("${ClientStorage[id]}\n") }

    fun printAccountInfo(){ for(id in accountIdList) println("${AccountStorage[id]}\n") }

    fun printCardInfo(){ for(id in cardIdList) println("${CardStorage[id]}\n") }

    fun printTransactionInfo(){ for(id in transactionIdList) println("${TransactionStorage[id]}\n") }

    fun registerPerson(){
        println("Enter surname, name, patronymic (if exists) separated by a space")
        val (surname, name, patronymic) = readln().split(" ", limit = 3)
        println("Enter gender (MALE/FEMALE)")
        val sex = PersonalInfo.Sex.valueOf(readln())
        println("Enter the address of residence (state, city, street, house, apartment) separated by a space")
        val (state, city, street, house, apt) = readln().split(" ", limit = 5)
        println("Enter phone number without brackets, spaces, dashes")
        val phoneNum = readln()
        println("Enter passport details without spaces or separators")
        val passport = readln()
        val person = Person(PersonalInfo(name, surname, patronymic, sex),
            Address(state, city, street, house, apt), phoneNum, passport)
        ClientStorage.personRegister(person)
        clientIdList.add(person.id)
    }

    fun registerLegalEntity(){
        println("Enter the name of the organization")
        val name = readln()
        println("Enter TIN")
        val tin = readln()
        println("Enter the address (state, city, street, house, apartment) separated by a space")
        val (state, city, street, house, apt) = readln().split(" ", limit = 5)
        val legalEntity = LegalEntity(name, tin, Address(state, city, street, house, apt))
        ClientStorage.legalEntityRegister(legalEntity)
        clientIdList.add(legalEntity.id)
    }

    fun openAccount(){
        println("Введите id клиента")
        val id = readln().toInt()
        println("Введите валюту счета (RUB/USD/EUR)")
        val currency = Currency.valueOf(readln())
        val debitAccount = DebitAccount(id, currency)
        try {
            AccountStorage.openDebitAccount(debitAccount)
            accountIdList.add(debitAccount.id)
        } catch (e: Exception){
            println(e)
        }
    }

    fun closeAccount(){
        println("Enter account id to close")
        val id = readln().toInt()
        println("Select closing method: with transfer to another account, with cash withdrawal (t/w)")
        val method = readln()
        if (method.lowercase() == "t"){
            println("Enter account id for transfer")
            val idTo = readln().toInt()
            try{
                AccountStorage.closeAccount(id, idTo)
                accountIdList.remove(id)
            } catch (e: Exception){
                println(e)
            }
        }
        if (method.lowercase() == "w"){
            println("Enter your withdrawal address")
            val placeToWithdraw = readln()
            println("Enter ATM number")
            val atmNum = readln()
            try{
                AccountStorage.closeAccount(id, placeToWithdraw, atmNum)
                accountIdList.remove(id)
            } catch (e: Exception){
                println(e)
            }
        }
    }

    fun setLimit(){
        println("Set a withdrawal limit on a card or account? (c/a)")
        val limitType = readln()
        try{
            if (limitType == "a") {
                println("Enter account id")
                val id = readln().toInt()
                println("Enter a new withdrawal limit")
                val limit = readln().toDouble()
                AccountStorage[id].limit = limit
            }
            if (limitType == "c"){
                println("Enter card id")
                val id = readln().toInt()
                println("Enter a new withdrawal limit")
                val limit = readln().toDouble()
                CardStorage[id].limit = limit
            }
        } catch (e: Exception){
            println(e)
        }
    }

    fun openCard(){
        println("Enter account id")
        val id = readln().toInt()
        println("Enter the type of payment system")
        val paySystem = Card.PaySystem.valueOf(readln())
        val card = Card(id, paySystem)
        try{
            CardStorage.openCard(card)
            cardIdList.add(card.id)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun closeCard(){
        println("Enter card id")
        val id = readln().toInt()
        try{
            CardStorage.closeCard(id)
            cardIdList.remove(id)
        } catch (e: Exception){
            println(e)
        }
    }

    fun cardRebinding(){
        println("Enter card id")
        val id = readln().toInt()
        println("Enter account id for linking")
        val accId = readln().toInt()
        try{
            CardStorage[id].rebinding(accId)
        } catch (e: Exception){
            println(e)
        }
    }

    fun transferMoney(){
        println("Transfer between accounts or cards? (a/c)")
        val transferType = readln()
        if (transferType == "a"){
            println("Enter account id to withdraw")
            val idFrom = readln().toInt()
            println("Enter account id for replenishment")
            val idTo = readln().toInt()
            println("Enter transfer amount")
            val amount = readln().toDouble()
            try {
                val trans = TransactionBWClients(amount, AccountStorage[idFrom], AccountStorage[idTo])
                TransactionStorage[trans.id] = trans
                transactionIdList.add(trans.id)
            } catch (e: Exception){
                println(e)
            }
        }
        if (transferType == "c"){
            println("Enter card id to withdraw")
            val idFrom = readln().toInt()
            println("Enter card id to deposit")
            val idTo = readln().toInt()
            println("Enter transfer amount")
            val amount = readln().toDouble()
            try {
                val cardFrom = CardStorage[idFrom]
                val cardTo = CardStorage[idTo]
                val trans = TransactionBWClients(amount, AccountStorage[cardFrom.accountId],
                    AccountStorage[cardTo.accountId], cardFrom, cardTo)
                TransactionStorage[trans.id] = trans
                transactionIdList.add(trans.id)
            } catch (e: Exception){
                println(e)
            }
        }
    }

    fun cashTransaction(){
        println("Enter account id")
        val id = readln().toInt()
        println("Enter transaction amount")
        val amount = readln().toDouble()
        println("Enter operation type (DEPOSIT, WITHDRAWAL)")
        val type = CashTransaction.TransType.valueOf(readln())
        println("Enter your withdrawal address")
        val placeToWithdraw = readln()
        println("Enter ATM number")
        val atmNum = readln()
        try {
            val acc = AccountStorage[id]
            val trans = CashTransaction(acc, amount, type, placeToWithdraw, atmNum, false)
            TransactionStorage[trans.id] = trans
            transactionIdList.add(trans.id)
        } catch(e: Exception){
            println(e)
        }
    }
}