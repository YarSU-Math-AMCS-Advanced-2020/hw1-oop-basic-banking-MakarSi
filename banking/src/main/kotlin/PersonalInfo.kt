data class PersonalInfo(
    var name: String,
    var surname: String,
    var patronymic: String?,
    var sex: Sex) {

    enum class Sex{ MALE, FEMALE; }

    init{
        name = name.uppercase()
        surname = surname.uppercase()
        patronymic = patronymic?.uppercase()
    }

    override fun toString(): String{
        var string = "Name: $name\nSurname: $surname\n"
        if(patronymic != null) string += "Patronymic: $patronymic\n"
        string += "Sex: $sex"
        return string
    }
}