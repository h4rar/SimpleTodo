package h4rar.space.simpletodo.utils

import h4rar.space.simpletodo.database.DatabaseRepository

lateinit var REPOSITORY: DatabaseRepository


object Constants {
    object Keys {
        const val NOTE_DATABASE = "notes_database"
        const val NOTES_TABLE = "notes_table"
        const val TABS_TABLE = "tabs_table"
        const val ADD_NEW_TASK = "Add new task"
        const val ADD_NEW_TAB = "Add new tab"
        const val OK = "Ok"
        const val EMPTY = ""
        const val UPDATE_NOTE = "Update note"
        const val TASK = "Task"
        const val CREATE_TAB = "Create tab"
        const val UPDATE_TAB = "Update tab"
        const val DELETE_TAB = "The tab will be deleted, you want to continue"
    }

    object Screens {
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"
    }
}