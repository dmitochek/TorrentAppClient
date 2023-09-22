package com.example.torrentclient.domain.usecase
import android.content.Context
import com.example.torrentclient.domain.repository.SendLink
import com.example.torrentclient.pres.MainActivity

class SendLinkUseCase(private val sendLink: SendLink) {
    fun execute(link: String, context: Context)
    {
        return sendLink.sendLink(link = link, context)
    }
}