package com.example.torrentclient.domain.usecase
import com.example.torrentclient.domain.repository.SendLink

class SendLinkUseCase(private val sendLink: SendLink) {
    fun execute(link: String)
    {
        return sendLink.sendLink(link = link)
    }
}