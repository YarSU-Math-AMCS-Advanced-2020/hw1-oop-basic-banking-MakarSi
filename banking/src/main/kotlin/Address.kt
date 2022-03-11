data class Address(
    var apt: String,
    var House: String,
    var street: String,
    var city: String,
    var state: String?,
    var zip: String){
    override fun toString(): String{
        var string = ""
        if (state != null) string = "State: $state\n"
        string += "City: $city\nStreet: $street\nHouse: $House\nApartment: $apt\nZIP: $zip"
        return string
    }
}