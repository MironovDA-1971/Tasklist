package tasklist

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File
import java.io.IOException

class ReadSaveJson {
    private val file = File("tasklist.json")
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val type = Types.newParameterizedType(MutableList::class.java, MutableMap::class.java)
    private val taskListAdapter = moshi.adapter<MutableList<MutableMap<String, String>>>(type)


    fun saveJson(taskList: MutableList<MutableMap<String, String>>) {
        file.writeText(taskListAdapter.toJson(taskList))
    }

    fun readJson(): MutableList<MutableMap<String, String>> {
        return try {
            taskListAdapter.fromJson(file.readText())!!
        } catch (_: IOException) {
           mutableListOf()
        }
    }

}