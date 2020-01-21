import kotlinx.serialization.Serializable
// every message is a type of this class
@Serializable
class ChatMessage(val userName:String, val chatMessage:String, val command:String, var time:String,var id:Int) {

    override fun toString(): String {
        return "$userName $chatMessage $time  $id"

    }
}