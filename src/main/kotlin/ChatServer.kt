import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlinx.io.IOException as IOException1


/**
 * in this class the server socket is created and wait for the client request, then the client will be
 * accepted if the server socket  get a request
 * each socket will have a thread
 */
class ChatServer {
    private val port: Int = 40000
    @Throws(IOException1::class)
    fun serve() {
        try {
            val serverSocket = ServerSocket(port, 2)
            println("accepting")
            while (true) {
                val socket: Socket = serverSocket.accept()
                println("accepted")
                val input = Scanner(socket.getInputStream())
                val output = PrintWriter(socket.getOutputStream(), true)

                 Thread(ClientServer(socket, input, output)).start()
            }
        }
        catch (e:kotlinx.io.IOException){
            println("error thrown from chatserver class ")
        }
    }
}

fun main() {

    ChatHistory.registerUser(ChatConsole())
    ChatHistory.registerUser(TopChater())
    ChatServer().serve()


}