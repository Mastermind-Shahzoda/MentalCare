package mening.dasturim.technovationchalange.data.module

class MessageItems {
    var messageId: String? = null
    var message: String? = null
    var senderId : String? = null
    var imageUrl: String? =null
    var timeStamp : Long = 0

    constructor(){}
    constructor(
        message: String?,
        senderId : String?,
        timeStamp : Long
    ){
        this.message = message
        this.senderId = senderId
        this.timeStamp = timeStamp
    }
}