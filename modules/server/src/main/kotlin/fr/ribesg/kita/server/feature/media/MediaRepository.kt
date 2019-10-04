package fr.ribesg.kita.server.feature.media

import fr.ribesg.kita.server.Database

interface MediaRepository

class MediaRepositoryImpl(
    private val database: Database
) : MediaRepository
