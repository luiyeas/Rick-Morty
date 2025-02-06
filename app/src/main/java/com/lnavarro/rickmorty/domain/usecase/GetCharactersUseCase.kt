package com.lnavarro.rickmorty.domain.usecase

import androidx.paging.PagingData
import com.lnavarro.rickmorty.data.repository.CharacterRepository
import com.lnavarro.rickmorty.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(status: String?): Flow<PagingData<CharacterDomain>> {
        return repository.getCharactersPaged(status)
    }
}