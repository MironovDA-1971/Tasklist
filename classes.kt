package tasklist
import kotlinx.datetime.*
import java.time.LocalTime

data class PrintMessage(var maxNumber: Int = 0) {
    val start = "Input an action (add, print, edit, delete, end):"
    val msgEnd = "Tasklist exiting!"
    val msgAdd = "Input a new task (enter a blank line to end):"
    val invalidInput = "The input action is invalid"
    val printNoTask = "No tasks have been input"
    val taskIsBlank = "The task is blank"
    val add = "add"
    val print = "print"
    val end = "end"
    // val newLine = "\n"
    // val threeSpaces = "   "
    val taskPriority = "Input the task priority (C, H, N, L):"
    val inputDate = "Input the date (yyyy-mm-dd):"
    val inputTime = "Input the time (hh:mm):"
    val priorityList = listOf("C", "H", "N", "L")
    val errorDate = "The input date is invalid"
    val errorTime = "The input time is invalid"
    val delete = "delete"
    fun numDel() = "Input the task number (1-$maxNumber):"
    val invalidTaskNumber = "Invalid task number"
    val taskDel = "The task is deleted"
    val inpFieldToEdit = "Input a field to edit (priority, date, time, task):"
    val invalidField = "Invalid field"
    val okChanged = "The task is changed"
    val edit = "edit"
    val delim = "+----+------------+-------+---+---+--------------------------------------------+"
    val headDelim = """| N  |    Date    | Time  | P | D |                   Task                     |"""
    val halfDelim = """|    |            |       |   |   |"""
    val priorityColor = mapOf("C" to "\u001B[101m \u001B[0m",
                              "H" to "\u001B[102m \u001B[0m",
                              "N" to "\u001B[103m \u001B[0m",
                              "L" to "\u001B[104m \u001B[0m")
    val dueTagColor = mapOf("I" to "\u001B[102m \u001B[0m",
                            "T" to "\u001B[103m \u001B[0m",
                            "O" to "\u001B[101m \u001B[0m")
}

fun curDate(data: String?): String {
    var period = "O"
    val date = data?.split("-")?.toMutableList()
    val taskDate = LocalDate(date?.get(0)!!.toInt(), date[1].toInt(), date[2].toInt())
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+4")).date
    val numberOfDays = taskDate.let { currentDate.daysUntil(it) }
    if (numberOfDays == 0) period = "T"
    else if (numberOfDays > 0) period = "I"
    return period
}

fun inputToString(message: PrintMessage, taskList: MutableList<MutableMap<String, String>>) {
    val mapList = mutableMapOf<String, String>()
    mapList["priority"] = inputPriority(message)     // Priority
    mapList["date"] = inputDate(message)             // Date
    mapList["time"] = inputTime(message)             // Time
    val key: Boolean = stringTask(message, mapList)  // Task
    if (key) taskList.add(mapList)
}

fun stringTask(message: PrintMessage, mapList: MutableMap<String, String>): Boolean {
    var str = ""
    var key = false
    println(message.msgAdd)
    while (true) {
        val input = readln().trim()
        if (input.isEmpty()) break
        // TODO() проверить количество символов в строке и если оно привышает 44 символа
        //  разбить строку переводами строки - \n
        str += input + "\n" //+ message.threeSpaces
    }
    if (str.isEmpty()) println(message.taskIsBlank) else {
        mapList["task"] = str //.dropLast(3)      Tasks
        key = true
    }
    return key
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
        var date: String
        try {
            val (y, m, d) = readln().split("-")
            date = LocalDate(y.toInt(), m.toInt(), d.toInt()).toString()
            return date
        } catch (e: Exception) {
            println(message.errorDate)
        }
    }
}

fun inputTime(message: PrintMessage): String {
    while (true) {
        println(message.inputTime)
        try {
            val (h, m) = readln().split(":")
            val time = LocalTime.of(h.toInt(), m.toInt())
            return time.toString()
        } catch (e: Exception) {
            println(message.errorTime)
        }
    }
}

fun printTaskList(message: PrintMessage, taskList: MutableList<MutableMap<String, String>>): Int {
    var num = 0
    if (taskList.isNotEmpty()) {
        println(message.delim) // table header output
        println(message.headDelim)
        println(message.delim)
        for (i in 0..taskList.lastIndex) {
            val prColor = message.priorityColor[taskList[i]["priority"]]
            val dtColor = message.dueTagColor[curDate(taskList[i]["date"])]
            val date = taskList[i]["date"]
            val time = taskList[i]["time"]
            val tmpTaskList = taskList[i]["task"]?.split("\n")
            val strNum = (++num).toString().padEnd(3)

            for (i in 0 ..tmpTaskList?.lastIndex!!) {
                val taskString = tmpTaskList[i].padEnd(44) + "|"
                if (i == 0) println("| $strNum| $date | $time | $prColor | $dtColor |$taskString")
                else println(message.halfDelim + taskString)
            }
            println(message.delim)
        }
    }
    else println(message.printNoTask)
    return num
}

fun deleteTask(message: PrintMessage, taskList: MutableList<MutableMap<String, String>>) {
    message.maxNumber = printTaskList(message, taskList)
    while (message.maxNumber > 0) {
        println(message.numDel())
        try {
            taskList.removeAt(readln().toInt() - 1)
            println(message.taskDel)
            break
        } catch (e: Exception) {
            println(message.invalidTaskNumber)
        }
    }
}

fun editTask(message: PrintMessage, taskList: MutableList<MutableMap<String, String>>) {
    message.maxNumber  = printTaskList(message, taskList)
    while (message.maxNumber > 0) {
        println(message.numDel())
        try {
            val edtTask = taskList[readln().toInt() - 1]
            th@while (true) {
                println(message.inpFieldToEdit)
                when (readln()) {
                    "time" -> { edtTask["time"] = inputTime(message); break@th }
                    "date" -> { edtTask["date"] = inputDate(message); break@th }
                    "priority" -> { edtTask["priority"] = inputPriority(message); break@th }
                    "task" -> { stringTask(message, edtTask); break@th }
                    else -> { println(message.invalidField); continue@th }
                }
            }
            println(message.okChanged)
            break
            } catch (e: Exception) {
                println(message.invalidTaskNumber)
        }
    }
}