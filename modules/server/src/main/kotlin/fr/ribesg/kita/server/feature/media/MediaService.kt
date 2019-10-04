package fr.ribesg.kita.server.feature.media

interface MediaService

class MediaServiceImpl(
    private val mediaRepository: MediaRepository
) : MediaService
