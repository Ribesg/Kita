package fr.ribesg.kita.server

import java.time.Duration

interface EnvProperties {
    val jwtAudience: String?
    val jwtIssuer: String?
    val jwtRealm: String?
    val jwtSecret: String?
    val jwtTokenDurationAccess: Duration?
    val jwtTokenDurationRefresh: Duration?
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

    override val jwtTokenDurationAccess: Duration?
        get() = System.getProperty(JWT_TOKEN_DURATION_ACCESS)
            ?.toLong()?.let { Duration.ofMillis(it) }

    override val jwtTokenDurationRefresh: Duration?
        get() = System.getProperty(JWT_TOKEN_DURATION_REFRESH)
            ?.toLong()?.let { Duration.ofMillis(it) }

}
