package chatui.viewmodel

/**
 * * Prevent client to misuse it or use it client own purpose
 */
data class SendAbleMessage internal constructor(
    val message: String,
    val receiverName:String,
    val receiverAddresses:String,
    val timestamp: Long=System.currentTimeMillis(),
)
