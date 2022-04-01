class LegalEntity(
    var name: String,
    var tin: String,
    var legalAddress: Address
): Client(){
    override val id: Int = hashCode()
    override fun toString(): String = "Name: $name\nTIN: $tin\nLegal Address:\n $legalAddress\nId: $id"
}