
/**
 * an observable interface for ChatHistory class
 * register and deRegister chatHistoryObserver types to the chatHistory
 */
interface ChatHistoryMember {
    fun registerUser(client:ChatHistoryObserver)
    fun unregisterUser(client:ChatHistoryObserver)
    fun notifyClients(message: ChatMessage)
}