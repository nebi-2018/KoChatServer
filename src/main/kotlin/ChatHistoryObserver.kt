
/**
 * an observer interface , any class which implement this class and register to the ChatHistory
 * will be notified while a new message received by a ChatHistory
 */
interface ChatHistoryObserver {
    fun newIncomingMessage(message: ChatMessage)
}