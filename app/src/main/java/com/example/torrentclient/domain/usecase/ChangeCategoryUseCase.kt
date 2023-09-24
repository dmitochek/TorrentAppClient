package com.example.torrentclient.domain.usecase

import com.example.torrentclient.domain.models.TorrentListInfo
import com.example.torrentclient.domain.repository.ChangeCategory

class ChangeCategoryUseCase(private val changeCategory: ChangeCategory) {
    fun execute(category: Int): Array<TorrentListInfo?>
    {
        return changeCategory.getTorrents(category = category)
    }
}