package tasklist

fun main() {
    val message = PrintMessage()
    val taskList = mutableListOf<String>()

    while (true) {
        println(message.start)
        when (readln().trim().lowercase()) {
            message.add -> inputToString(message, taskList)
            message.print -> {
                if (taskList.isNotEmpty()) printTaskList(taskList)
                else println(message.printNoTask)
            }
            message.end -> break
            else -> println(message.invalidInput)
        }
    }
    println(message.msgEnd)

}


