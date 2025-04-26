package com.example.filmoteq.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.filmoteka.data.SampleFilmRepository
import java.util.UUID
import com.example.filmoteq.model.*

class FilmViewModel : ViewModel() {
    private var _films = mutableStateListOf<Film>()

    var selectedCategory by mutableStateOf<Category?>(null)
    var selectedStatus by mutableStateOf<Status?>(null)

    init {
        _films.addAll(SampleFilmRepository.getSampleFilms())
    }

    fun getFilteredFilms(): List<Film> {
        return _films
            .filter { selectedCategory == null || it.category == selectedCategory }
            .filter { selectedStatus == null || it.status == selectedStatus }
            .sortedBy { it.releaseDate }
    }

    fun addOrUpdateFilm(film: Film) {
        val index = _films.indexOfFirst { it.id == film.id }
        if (index != -1) {
            val existingFilm = _films[index]
            existingFilm.title = film.title
            existingFilm.releaseDate = film.releaseDate
            existingFilm.category = film.category
            existingFilm.status = film.status
            existingFilm.rating = film.rating
            existingFilm.posterUri = film.posterUri
        } else {
            _films.add(film)
        }
    }

    fun deleteFilm(film: Film) {
        _films.remove(film)
    }

    fun getFilmById(id: UUID): Film? {
        return _films.find { it.id == id }
    }

    fun clearFilters() {
        selectedCategory = null
        selectedStatus = null
    }
}
