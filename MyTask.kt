package tasklist

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.IOException

class TaskToJson(val taskList: MutableList<MutableMap<String, String>>)

class ReadSaveJoson {
    private val jsonFile = File("tasklist.json")
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val taskAdapter: JsonAdapter<TaskToJson> = moshi.adapter(TaskToJson::class.java)


    fun saveJson(jsonList: TaskToJson) {
        jsonFile.writeText(taskAdapter.toJson(jsonList))
    }

    fun readJson(jsonList: TaskToJson): MutableList<MutableMap<String, String>> {
        return try {
            taskAdapter.fromJson(jsonFile.readText())?.taskList!!
        } catch (_: IOException) {
            jsonList.taskList
        }
    }

}