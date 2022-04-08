package com.ahmedorabi.instabugapp.features.words_list.repo

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.ahmedorabi.instabugapp.data.db.WordContract
import com.ahmedorabi.instabugapp.data.db.WordDbHelper
import com.ahmedorabi.instabugapp.data.domain.Word

class DbManager(context: Context) : WordsListLocalDataSource {

    private var dbHelper: WordDbHelper = WordDbHelper(context)

    override fun getWordsList(): List<Word> {
        val newWordList = ArrayList<Word>()
        try {


            val db = dbHelper.readableDatabase
            val projection = arrayOf(
                BaseColumns._ID,
                WordContract.WordEntry.COLUMN_Word_NAME,
                WordContract.WordEntry.COLUMN_WORD_TOTAL
            )

            val cursor = db.query(
                WordContract.WordEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
            )


            with(cursor) {
                while (moveToNext()) {
                    val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    val itemName =
                        getString(getColumnIndexOrThrow(WordContract.WordEntry.COLUMN_Word_NAME))
                    val itemTotal =
                        getString(getColumnIndexOrThrow(WordContract.WordEntry.COLUMN_WORD_TOTAL))
                    newWordList.add(Word(itemId, itemName, itemTotal.toInt()))
                }
            }
            cursor.close()

        } catch (e: Exception) {
            Log.e("DbManager", e.message ?: "")
        }
        return newWordList
    }

    override fun insertAllWords(words: List<Word>) {
        try {
            val db = dbHelper.writableDatabase

            val values = ContentValues()

            words.forEach {
                values.put(WordContract.WordEntry.COLUMN_Word_NAME, it.name)
                values.put(WordContract.WordEntry.COLUMN_WORD_TOTAL, it.total)

                //Insert the new row, returning the primary key value of the new row
                val newRowId = db?.insert(WordContract.WordEntry.TABLE_NAME, null, values)
                Log.e("DbManagerWrite", newRowId.toString())
            }


        } catch (e: Exception) {
            Log.e("DbManagerWrite", e.message ?: "")
        }
    }

    override fun deleteAllWords() {
        try {
            val db = dbHelper.writableDatabase

            val deletedRows =
                db.delete(WordContract.WordEntry.TABLE_NAME, null, null)
            Log.e("DbManagerDelete", deletedRows.toString())

        } catch (e: Exception) {
            Log.e("DbManagerDelete", e.message ?: "")

        }
    }

    fun closeDb(){
        dbHelper.close()
    }

}