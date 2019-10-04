package fr.ribesg.kita.server

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import fr.ribesg.kita.server.feature.media.MediaRepository
import fr.ribesg.kita.server.feature.media.MediaRepositoryImpl
import fr.ribesg.kita.server.feature.media.MediaService
import fr.ribesg.kita.server.feature.media.MediaServiceImpl
import fr.ribesg.kita.server.metadata.musicbrainz.MusicBrainzApi
import fr.ribesg.kita.server.metadata.musicbrainz.MusicBrainzApiImpl
import fr.ribesg.kita.server.metadata.tmdb.TmdbApi
import fr.ribesg.kita.server.metadata.tmdb.TmdbApiImpl
import fr.ribesg.kita.server.metadata.tvdb.TvdbApi
import fr.ribesg.kita.server.metadata.tvdb.TvdbApiImpl
import org.koin.core.module.Module

fun Module.components() {

    single {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        Database(driver)
    }

    single<MusicBrainzApi> { MusicBrainzApiImpl() }
    single<TmdbApi> { TmdbApiImpl() }
    single<TvdbApi> { TvdbApiImpl() }

    single<MediaRepository> { MediaRepositoryImpl(get()) }

    single<MediaService> { MediaServiceImpl(get()) }

}
