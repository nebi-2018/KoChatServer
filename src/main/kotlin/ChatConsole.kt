

/**
 * this is a memeber of chatHistory class
 * when get notified print every message to the console
 */
class ChatConsole:ChatHistoryObserver{

    override fun newIncomingMessage(message: ChatMessage) {
     println(message)
    }




}