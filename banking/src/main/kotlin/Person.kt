class Person(
    var person: PersonalInfo,
    var address: Address,
    var phone_number: String,
    var passport: String
): Client(){
    override val id: Int = hashCode()
    override fun toString() = "$person\n$address\nPhone number: $phone_number\nPassport: $passport\nId: $id"
}