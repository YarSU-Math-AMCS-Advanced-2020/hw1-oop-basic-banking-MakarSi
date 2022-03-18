data class Address(
    var state: String,
    var city: String,
    var street: String,
    var house: String,
    var apt: String){
    override fun toString(): String = "State: $state\nCity: $city\nStreet: $street\nHouse: $house\nApartment: $apt"
}