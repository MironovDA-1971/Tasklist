package tasklist

fun main() {
    val message = PrintMessage()
    val jsonRead = TaskToJson(mutableListOf())
    val rsJoson = ReadSaveJoson()
    val taskList = rsJoson.readJson(jsonRead)

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

    val jsonSave = TaskToJson(taskList)
    rsJoson.saveJson(jsonSave)
    println(message.msgEnd)
}
