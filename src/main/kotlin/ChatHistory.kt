
/**
 * this is an observable singleton class, every message from the chatConnector/client pass to this class
 * take the message and notify each members
 */
object ChatHistory : ChatHistoryMember {
    private var chatMessageId =10
    //create a list of memebers
    private val memberList = mutableListOf<ChatHistoryObserver>()
    //record all chat messages form all available users
    private val chatHistoryRecord = mutableListOf<ChatMessage>()

    fun newMessage(message: ChatMessage) {
        chatMessageId++
        message.id= chatMessageId
        updateChatHistory(message)
        notifyClients(message)
    }
    private fun updateChatHistory(message: ChatMessage) {
        chatHistoryRecord.add(message)
    }
    fun getChatHistory():MutableList<ChatMessage>{
        return chatHistoryRecord
    }
    // this function notify only members when it get a new message
    override fun notifyClients(message: ChatMessage) {
        memberList.forEach {
            it.newIncomingMessage(message) }
    }
    override fun registerUser(client: ChatHistoryObserver) {
        memberList.add(client)
    }
    override fun unregisterUser(client: ChatHistoryObserver) {
        memberList.remove(client)
    }


}
