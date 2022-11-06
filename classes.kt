package tasklist
import kotlinx.datetime.*

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
    val taskPriority = "Input the task priority (C, H, N, L):"
    val inputDate = "Input the date (yyyy-mm-dd):"
    val inputTime = "Input the time (hh:mm):"
    val priorityList = listOf("C", "H", "N", "L")
    val errorDate = "The input date is invalid"
    val errorTime = "The input time is invalid"
}

fun inputToString(message: PrintMessage, taskList: MutableList<String>) {
    var str = ""
    val priority = inputPriority(message)     // Priority
    val date = inputDate(message)             // Date
    val time = inputTime(message)             // Time
    val dateTimePriority = "$date $time $priority${message.newLine}${message.threeSpaces}"  // Date Time Priority
    println(message.msgAdd)
    while (true) {
        val input = readln().trim()
        if (input.isEmpty()) break
        str += input + message.newLine + message.threeSpaces
    }
    if (str.isEmpty()) println(message.taskIsBlank) else taskList.add(dateTimePriority + str.dropLast(3))
}

fun inputPriority(message: PrintMessage): String {
    var priority = ""
    while (true){
        if (priority !in message.priorityList) {
            println(message.taskPriority)
            priority = readln().trim().uppercase()
        } else return priority
    }
}

fun inputDate(message: PrintMessage): String {
    while (true) {
        println(message.inputDate)
        var date = ""
        try {
            val (y, m, d) = readln().split("-")
            date = LocalDate(y.toInt(), m.toInt(), d.toInt()).toString()
            return date
        } catch (e: Exception) {
            println(message.errorDate)
        }
    }
}
fun inputTime(message: PrintMessage): String  {
    while (true) {
        val hRange = 0..23
        val mRange = 0..59
        println(message.inputTime)
        try {
            val (h, m) = readln().split(":")
            if (h.toInt() !in hRange || m.toInt() !in mRange) 2/0
            return "$h:$m"
        } catch (e: Exception) {
            println(message.errorTime)
        }
    }
}

fun printTaskList(taskList: MutableList<String>) {
    for (i in 0..taskList.lastIndex) {
        println((i + 1).toString().padEnd(3) + taskList[i])
    }
}