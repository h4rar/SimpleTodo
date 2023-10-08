//package h4rar.space.simpletodo.model
//
//import androidx.room.TypeConverter
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import java.lang.reflect.Type
//
//class DataConverter {
//    @TypeConverter
//    fun fromCountryLangList(note: List<Note?>?): String? {
//        if (note == null) {
//            return null
//        }
//        val gson = Gson()
//        val type: Type =
//            object : TypeToken<List<Note?>?>() {}.getType()
//        return gson.toJson(note, type)
//    }
//
//    @TypeConverter
//    fun toCountryLangList(noteString: String?): List<Note>? {
//        if (noteString == null) {
//            return null
//        }
//        val gson = Gson()
//        val type: Type =
//            object : TypeToken<List<Note?>?>() {}.getType()
//        return gson.fromJson(noteString, type)
//    }
//}