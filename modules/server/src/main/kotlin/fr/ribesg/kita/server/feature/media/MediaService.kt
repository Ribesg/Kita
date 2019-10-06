package fr.ribesg.kita.server.feature.media

import fr.ribesg.kita.server.Database

interface MediaService

class MediaServiceImpl(database: Database) : MediaService {

    private val mediaQueries = database.mediaQueries

}
