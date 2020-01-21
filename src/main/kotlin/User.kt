
/**
 * in this singleton class, clients will be registerd after they become subscribed to the chatHistory class
 *  we use this class to show currently online subscribed users
 *  user which are not registerd to the chatHistory class will not be added to user  list
 *  functions
 */
object User {
    val userList= mutableSetOf<String>()

    fun addUser(userName:String){
        userList.add(userName)
    }
    fun removeUser(userName:String) {
        userList.remove(userName)
    }


}


