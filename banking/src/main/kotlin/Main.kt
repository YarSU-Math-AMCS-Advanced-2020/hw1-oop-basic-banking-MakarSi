fun main() {
    var playMenu = true
    val listOfOptions = listOf(
        "Print information about all clients",
        "Print information about all accounts",
        "Print information about all cards",
        "Print information about all transactions",
        "Person registration",
        "Legal entity registration",
        "Account opening",
        "Account closure",
        "Card opening",
        "Card closure",
        "Limit setting",
        "Card rebinding",
        "Money transfer",
        "Cash Transaction",
        "Exit menu"
    )
    while (playMenu){
        listOfOptions.forEachIndexed{ index, value -> println("${index+1}. $value") }
        when (readln().toInt()){
            1 -> ConsoleInterface.printClientInfo()
            2 -> ConsoleInterface.printAccountInfo()
            3 -> ConsoleInterface.printCardInfo()
            4 -> ConsoleInterface.printTransactionInfo()
            5 -> ConsoleInterface.registerPerson()
            6 -> ConsoleInterface.registerLegalEntity()
            7 -> ConsoleInterface.openAccount()
            8 -> ConsoleInterface.closeAccount()
            9 -> ConsoleInterface.openCard()
            10 -> ConsoleInterface.closeCard()
            11 -> ConsoleInterface.setLimit()
            12 -> ConsoleInterface.cardRebinding()
            13 -> ConsoleInterface.transferMoney()
            14 -> ConsoleInterface.cashTransaction()
            15 -> playMenu = false
        }
        println("Press any key to continue")
        readln()
    }
}