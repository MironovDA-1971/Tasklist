package tasklist

fun main() {
    val message = PrintMessage()
    val file = ReadSaveJson()
    val taskList = file.readJson()

    while (true) {
        println(message.start)
        when (readln().trim().lowercase()) {
            message.add -> inputToString(message, taskList)
            message.print -> printTaskList(message, taskList)
            message.delete -> deleteTask(message, taskList)
            message.edit -> editTask(message, taskList)
            message.end -> break
            else -> println(message.invalidInput)
        }
    }

    file.saveJson(taskList)
    println(message.msgEnd)
}
