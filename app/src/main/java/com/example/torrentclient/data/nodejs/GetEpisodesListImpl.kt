package com.example.torrentclient.data.nodejs

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.torrentclient.domain.repository.GetEpisodesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Base64


class GetEpisodesListImpl: GetEpisodesList {

    private suspend fun httpGet(myURL: String?): String? {

        val result = withContext(Dispatchers.IO) {
            val inputStream: InputStream
            val url = URL(myURL)
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

            conn.connect()

            inputStream = conn.inputStream

            if (inputStream != null)
                convertInputStreamToString(inputStream)
            else
                null
        }
        return result
    }

    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader: BufferedReader? = BufferedReader(InputStreamReader(inputStream))

        var line:String? = bufferedReader?.readLine()
        var result:String = ""

        while (line != null) {
            result += line
            line = bufferedReader?.readLine()
        }

        inputStream.close()
        return result
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getList(magnet: String?): String? {
        return httpGet("http://localhost:3000/add/" + Base64.getEncoder().encodeToString(magnet?.toByteArray()))
    }

}