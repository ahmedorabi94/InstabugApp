package com.ahmedorabi.instabugapp.data.api

import android.util.Log
import com.ahmedorabi.instabugapp.data.domain.Word
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val BASE_URL = "https://instabug.com"

class SafeApiCallHelper {

    fun getWordsList(): ResultWrapper<List<Word>> {
        var reader: BufferedReader? = null
        var responseCode = -1
        return try {
            val url = URL(BASE_URL)
            val conn = url.openConnection() as HttpURLConnection

            responseCode = conn.responseCode
            val sb = StringBuilder()
            reader = BufferedReader(InputStreamReader(conn.inputStream))

            var line: String?

            while (reader.readLine().also { line = it } != null) {
                sb.append(line + "\n")
            }

            ResultWrapper.Success(getWords(sb.toString()))
        } catch (e: Throwable) {
            when (e) {
                is IOException -> {
                    ResultWrapper.NetworkError
                }
                is KotlinNullPointerException -> {
                    ResultWrapper.NoContentError
                }
                else -> {
                    ResultWrapper.Error(null, null)
                }
            }

            ResultWrapper.Error(responseCode, ErrorResponse(e.localizedMessage ?: ""))

        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("getWordsListApi", e.message ?: "")
                }
            }
        }
    }

    private fun getWords(response: String): List<Word> {
        val map = mutableMapOf<String, Int>()

        val text = Jsoup.parse(response).body().text()

        text.trim().split(" ").forEach {
            if (it.isNotEmpty()){
                val regex = "[^A-Za-z]".toRegex()
                map[regex.replace(it, "")] = map[it]?.plus(1) ?: 1

            }

        }
        return map.map {
            Word(name = it.key, total = it.value)
        }
    }


}

