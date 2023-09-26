package com.example.rick_morty

import androidx.paging.PagingSource
import androidx.paging.PagingState

class ResultPagingSource(
    private val characterApi: CharacterApi
): PagingSource<Int, Result>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        try {
            // Ваши операции для загрузки данных
            val pageNumber = params.key ?: 1 // Получаем номер страницы
            val pageSize = params.loadSize // Получаем размер страницы

            // Загрузка данных (например, из сети или базы данных)

            val data = fetchDataFromDataSource(pageNumber, pageSize)

//            val data = fetchDataFromDataSource(pageNumber, pageSize)

            // Рассчитываем предыдущий и следующий ключи (страницы)
            val prevKey = if (pageNumber == 1) null else pageNumber - 1
            val nextKey = if (data.isEmpty()) null else pageNumber + 1

            // Возвращаем результат в PagingSource.LoadResult
            return LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            // Обработка ошибок
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        // Этот метод возвращает ключ для обновления данных (например, после обновления)
        // Возвращаем null, чтобы использовать загрузку с ключом null
        return null
    }

    private suspend fun fetchDataFromDataSource(pageNumber: Int, pageSize: Int): List<Result> {
        // Здесь должен быть ваш код для загрузки данных с учетом номера страницы и размера страницы
        // Верните список объектов Names, например, из сети или базы данных
        // В этом примере просто возвращаем фиктивные данные
        val start = (pageNumber - 1) * pageSize
        val end = start + pageSize
        return (start until end).map { index ->
            characterApi.getAllCharacters().results[index]
        }
    }
}