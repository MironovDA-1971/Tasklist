package tasklist

data class PrintMessage(var arg: Int = 0) {
    val start = "Input an action (add, print, end):"
    val msgEnd = "Tasklist exiting!"
    val msgAdd = "Input a new task (enter a blank line to end):"
    val invalidInput = "The input action is invalid"
    val printNoTask = "No tasks have been input"
    val taskIsBlank = "The task is blank"
    val add = "add"
    val print = "print"
    val end = "end"
    val newLine = "\n"
    val threeSpaces = "   "
}

fun inputToString(message: PrintMessage, taskList: MutableList<String>) {
    var str = ""
    println(message.msgAdd)
    while (true) {
        val input = readln().trim()
        if (input.isEmpty()) break
        str += input + message.newLine + message.threeSpaces
    }
    if (str.isEmpty()) println(message.taskIsBlank) else taskList.add(str.dropLast(3))
}

fun printTaskList(taskList: MutableList<String>) {
    for (i in 0..taskList.lastIndex) {
        println((i + 1).toString().padEnd(3) + taskList[i])
    }
}