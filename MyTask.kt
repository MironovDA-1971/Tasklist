package tasklist

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

class MyTask(var date: String,
              var time: String,
              var urgency: String, 
              var deadline: String,
              var taskList: Array<String>
              ) {
}
fun saveJson() {
    val jsonFile = File("tasklist.json")
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val businessAdapter = moshi.adapter(MyTask::class.java).indent(" ")
   /*
    arrayTasks = taskList.toTypedArray()

    // создадим очередной объект класса
    var i = 0
    while (i in 0 until arrayTasks.size) {
        // составим список дел в очередном задании
        var s = i + 1
        var nextTask = emptyArray<String>()
        while (arrayTasks[s].isNotEmpty()) {
            nextTask += arrayTasks[s]
            s++
        }
        s++
        val nextRecord =  MyTasks<String>(
            arrayTasks[i].substring(0, 10),   // дата
            arrayTasks[i].substring(11, 16), // время
            arrayTasks[i].substring(17, 18), // приоритет
            arrayTasks[i].substring(19, 20),  // срок
            nextTask)  // список дел в задании
        i = s
        jsonFile.appendText(businessAdapter.indent("  ").toJson(nextRecord))
    }
    */
}