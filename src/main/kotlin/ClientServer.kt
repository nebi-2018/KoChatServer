import kotlinx.io.IOException
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Companion.stringify
import java.io.PrintWriter
import java.lang.Exception
import java.net.Socket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * this  class inherit runnable for the thread and implement
 * UpdateChatConnector interface for getting  notification  whenever a new message is sent
 * after the client is accepted each thread will run by this class
 *  any user/chatConnector  can be online and send the message and the message
 *  will be forwarded , but as long as it is not registerd to the chatHistory class/observable
 *  it will not be notified and get message from  other online  users
 */
class ClientServer(val socket: Socket, val input: Scanner, val output: PrintWriter) : Runnable, ChatHistoryObserver {

    private var userName = ""
    private var userInput = ""

    @ImplicitReflectionSerializer
    @Throws(IOException::class)
    override fun run() {
        // notify the user that the connection is stabilised
        val welcomeMessage = ChatMessage("chatRoom", "connection stabilised ", "",LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),1)
        newIncomingMessage(welcomeMessage) // send the welcome message as a notification

        try {
            while (true) {
                userInput = input.nextLine()
                val chatMessage = Json.parse(ChatMessage.serializer(), userInput) // create a jason format message object
                chatMessage.time=  LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                println(chatMessage.time)
                println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                commandInterpreter(chatMessage)
            }
        } catch (e: Exception) {
            println("error trowed from the run method")
        } finally {
            // if the thread suddenly failed to to run it will be removed from the user list and the socket will be closed
            unRegisterChatClient(userName)
            socket.close()
        }
    }

   // this fun  pass  the new message from the chatHistory class
   // every message will be stringfied before printed
    override fun newIncomingMessage(message: ChatMessage) {
       val chatMessage = stringify(ChatMessage.serializer(), message)
       output.println(chatMessage)
    }
    private fun unRegisterChatClient(userName:String){
        User.removeUser(userName)
        ChatHistory.unregisterUser(this)
    }
    private fun registerChatClient(userName: String){
        ChatHistory.registerUser(this)
        User.addUser(userName)
    }

    // after a new message is created it will pass through this fun for interpreting the command in the message
    @ImplicitReflectionSerializer
    fun commandInterpreter(chatmessage: ChatMessage) {

        try {
            when {
                chatmessage.command == "send" ->  ChatHistory.newMessage(chatmessage)
                chatmessage.command == "history"-> newIncomingMessage(ChatMessage("Server",ChatHistory.getChatHistory().toString(),"","",2))
                chatmessage.command == "unregister" -> unRegisterChatClient(chatmessage.userName)
                chatmessage.command == "users" -> newIncomingMessage(ChatMessage("Server","online user  ${User.userList}","users","",3))
                chatmessage.command == "close"-> { unRegisterChatClient(chatmessage.userName)
                                                if (this.userName==userName) socket.close()
                }
                chatmessage.command == "register" -> {
                                  if (User.userList.contains(chatmessage.userName)) newIncomingMessage(ChatMessage("server","user name already " +
                                 "exist please enter a new name","","",4))

                                  else { this.userName = chatmessage.userName
                                       registerChatClient(chatmessage.userName)
                                      newIncomingMessage(ChatMessage("Server","$userName is registerd","users",LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),5))
                                  }
                }
                // exceptional commands print only to the console and ignore the user command
                // too much feedback can be annoying for the user
                else -> println("User ${userName} typed wrong command ${chatmessage.command}")
            }
        } catch (e: Exception) {
            println("error")
        }

    }


}