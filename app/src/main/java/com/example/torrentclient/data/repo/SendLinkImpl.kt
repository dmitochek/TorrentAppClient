package com.example.torrentclient.data.repo
import android.os.StrictMode
import android.content.Context
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import com.example.torrentclient.data.apollo.ApolloServerInit
import com.example.torrentclient.domain.repository.OpenFile
import com.example.torrentclient.domain.repository.SendLink
import com.example.torrentclient.pres.MainActivity
import com.source.DownloadTorrentQuery
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels


class SendLinkImpl: SendLink {
    suspend fun getData(link: String): DownloadTorrentQuery.Data?{
        val response = ApolloServerInit().init().query(DownloadTorrentQuery(link)).execute()
        return response.data
    }
    override suspend fun sendLink(link: String?, context: Context): Unit = runBlocking{
        val response = link?.let { getData(it) }
            ?.downloadtorrent
        downloadFile(URL(response), URLUtil.guessFileName(response, null, null), context)
    }

    private fun downloadFile(url: URL, outputFileName: String, context: Context) {
        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                val letDirectory = File(context.filesDir, "torrents")
                letDirectory.mkdirs()
                val file = File(letDirectory, outputFileName)
                val flag = file.createNewFile()
                if (flag) {
                    FileOutputStream(file).use { fos ->
                        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                    }
                    OpenFileImpl.openFile(context, file)
                }
                else
                    Toast.makeText(context, "Error downloading torrent!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}