package fr.ribesg.kita.server.metadata.tmdb

import fr.ribesg.kita.common.model.SearchResponseMovie

suspend fun TmdbApi.toMovie(movie: TmdbApiMovie) =
    SearchResponseMovie(
        movie.title,
        movie.overview,
        movie.posterPath?.let { getImageFullPath(it, TmdbImageType.POSTER) }
    )

suspend fun TmdbApi.toMovie(movie: TmdbApiSearchMovieResult) =
    SearchResponseMovie(
        movie.title,
        movie.overview,
        movie.posterPath?.let { getImageFullPath(it, TmdbImageType.POSTER) }
    )
