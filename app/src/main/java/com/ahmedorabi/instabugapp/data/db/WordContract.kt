package com.ahmedorabi.instabugapp.data.db

import android.provider.BaseColumns

object  WordContract {

    object WordEntry : BaseColumns {
        const val TABLE_NAME = "words"
        const val COLUMN_Word_NAME = "name"
        const val COLUMN_WORD_TOTAL = "total"
    }

     const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${WordEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${WordEntry.COLUMN_Word_NAME} TEXT," +
                "${WordEntry.COLUMN_WORD_TOTAL} TEXT)"

     const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${WordEntry.TABLE_NAME}"
}