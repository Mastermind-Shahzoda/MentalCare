package mening.dasturim.technovationchalange.data.module

class User {
    var uid :String? =null
    var name:String? = null
    var nickname:String? =null
    var phoneNumber:String? = null
    var profileImage: String? = null
    constructor(){}
    constructor(
        uid:String?,
        name:String?,
        nickname:String?,
        phoneNumber:String?,
        profileImage:String?
    ){
        this.uid = uid
        this.name = name
        this.nickname = nickname
        this.phoneNumber = phoneNumber
        this.profileImage = profileImage
    }
}