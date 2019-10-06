package fr.ribesg.kita.server

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import fr.ribesg.kita.server.feature.media.MediaService
import fr.ribesg.kita.server.feature.media.MediaServiceImpl
import fr.ribesg.kita.server.feature.user.UserService
import fr.ribesg.kita.server.feature.user.UserServiceImpl
import fr.ribesg.kita.server.metadata.musicbrainz.MusicBrainzApi
import fr.ribesg.kita.server.metadata.musicbrainz.MusicBrainzApiImpl
import fr.ribesg.kita.server.metadata.tmdb.TmdbApi
import fr.ribesg.kita.server.metadata.tmdb.TmdbApiImpl
import fr.ribesg.kita.server.metadata.tvdb.TvdbApi
import fr.ribesg.kita.server.metadata.tvdb.TvdbApiImpl
import fr.ribesg.kita.server.util.Jwt
import fr.ribesg.kita.server.util.JwtImpl
import org.koin.core.module.Module

fun Module.components() {

    single<MetaProperties> { MetaPropertiesImpl() }
    single<EnvProperties> { EnvPropertiesImpl() }

    single<Jwt> { JwtImpl(get(), get()) }

    single {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        Database.Schema.create(driver)
        Database(driver)
    }

    single<MusicBrainzApi> { MusicBrainzApiImpl(get()) }
    single<TmdbApi> { TmdbApiImpl() }
    single<TvdbApi> { TvdbApiImpl() }

    single<MediaService> { MediaServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get(), get()) }

}
