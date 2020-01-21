
/**
 * this class will is an observer of the chatHistory class  and when it
 * get notified it will calculate the number of message send by the user and calculate the
 * top 4 users based on the number of ,message they send
 */
class TopChater : ChatHistoryObserver {

    private var messageCounter: MutableMap<String, Int> = mutableMapOf()
    override fun newIncomingMessage(message: ChatMessage) {
       when{
           messageCounter.containsKey(message.userName)->{
               var messageCount = messageCounter.getValue(message.userName)
               messageCount++
               messageCounter[message.userName] = messageCount
           }
           else -> {
               messageCounter.put(message.userName, 1)
           }
       }
        calculateTopChater()
    }

    private fun calculateTopChater() {
        // sort the messageCounter value and create a new map
      val resultMap = messageCounter.toList().sortedBy { (_, value) -> value}.reversed().toMap()
       var counter =0
        when {
            resultMap.size<5-> println("TopChaters $resultMap")
            else -> {
                val newList= resultMap.toList()
                println("Top 4 Chaters")
                while (counter<4){
                    println(" ${newList[counter]} ")
                    counter++
                }
            }
        }
    }
}


