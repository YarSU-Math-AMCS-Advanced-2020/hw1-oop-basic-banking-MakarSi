class LegalEntity(
    var name: String,
    var tin: String,
    var legal_address: Address
): Client(){
    override val id: Int = hashCode()
    override fun toString(): String = "Name: $name\nTIN: $tin\nLegal Address: $legal_address"
}