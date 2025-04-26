package com.example.filmoteka.data

import android.net.Uri
import java.time.LocalDate
import java.util.UUID
import com.example.filmoteq.model.*

object SampleFilmRepository {
    fun getSampleFilms(): List<Film> = listOf(
        Film(
            id = UUID.randomUUID(),
            title = "Symetria",
            releaseDate = LocalDate.of(2004, 2, 6),
            category = Category.Obyczajowy,
            status = Status.Obejrzany,
            rating = 8,
            posterUri = Uri.parse("https://fwcdn.pl/fpo/39/07/103907/7536865_2.3.jpg") // [1]
        ),
        Film(
            id = UUID.randomUUID(),
            title = "Mission: Impossible",
            releaseDate = LocalDate.of(1996, 5, 22),
            category = Category.Akcji,
            status = Status.Nieobejrzany,
            rating = null,
            posterUri = Uri.parse("https://fwcdn.pl/fpo/23/04/832304/8077948_2.3.jpg") // [7]
        ),
        Film(
            id = UUID.randomUUID(),
            title = "Wojna polsko-ruska",
            releaseDate = LocalDate.of(2009, 5, 22),
            category = Category.Obyczajowy,
            status = Status.Nieobejrzany,
            rating = null,
            posterUri = Uri.parse("https://fwcdn.pl/fpo/85/81/298581/7423399_2.3.jpg") // [8]
        ),
        Film(
            id = UUID.randomUUID(),
            title = "Czas surferów",
            releaseDate = LocalDate.of(2005, 8, 19),
            category = Category.Komedia,
            status = Status.Nieobejrzany,
            rating = null,
            posterUri = Uri.parse("https://fwcdn.pl/fpo/74/66/137466/7191535_2.3.jpg") // [9]
        ),
        Film(
            id = UUID.randomUUID(),
            title = "Kac Vegas",
            releaseDate = LocalDate.of(2009, 6, 5),
            category = Category.Komedia,
            status = Status.Obejrzany,
            rating = 8,
            posterUri = Uri.parse("https://fwcdn.pl/fpo/72/11/487211/7258810_2.3.jpg") // [10]
        ),
        Film(
            id = UUID.randomUUID(),
            title = "Incepcja",
            releaseDate = LocalDate.of(2010, 7, 16),
            category = Category.Przygodowy,
            status = Status.Obejrzany,
            rating = 8,
            posterUri = Uri.parse("https://i.iplsc.com/-/00048PJO017VSFO7-C460.jpg") // [11]
        )
    )
}
