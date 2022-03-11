class Person(
    var phone_number: String,
    var address: Address,
    var person: PersonalInfo,
    var passport: String,
): Client(){
    override val id: Int = hashCode()
    override fun toString() = "$person\n$address\nPhone number: $phone_number\nPassport: $passport"
}