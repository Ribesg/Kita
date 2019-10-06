package fr.ribesg.kita.server

interface EnvProperties {
    val jwtAudience: String?
    val jwtIssuer: String?
    val jwtRealm: String?
    val jwtSecret: String?
    val jwtTokenDurationAccess: Long?
    val jwtTokenDurationRefresh: Long?
}

class EnvPropertiesImpl : EnvProperties {

    companion object {

        private const val JWT_AUDIENCE = "JWT_AUDIENCE"
        private const val JWT_ISSUER = "JWT_ISSUER"
        private const val JWT_REALM = "JWT_REALM"
        private const val JWT_SECRET = "JWT_SECRET"

        private const val JWT_TOKEN_DURATION_ACCESS = "JWT_TOKEN_DURATION_ACCESS"
        private const val JWT_TOKEN_DURATION_REFRESH = "JWT_TOKEN_DURATION_REFRESH"

    }

    override val jwtAudience: String?
        get() = System.getProperty(JWT_AUDIENCE)

    override val jwtIssuer: String?
        get() = System.getProperty(JWT_ISSUER)

    override val jwtRealm: String?
        get() = System.getProperty(JWT_REALM)

    override val jwtSecret: String?
        get() = System.getProperty(JWT_SECRET)

    override val jwtTokenDurationAccess: Long?
        get() = System.getProperty(JWT_TOKEN_DURATION_ACCESS)?.toLong()

    override val jwtTokenDurationRefresh: Long?
        get() = System.getProperty(JWT_TOKEN_DURATION_REFRESH)?.toLong()

}
