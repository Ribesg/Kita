package fr.ribesg.kita.server.util

import org.bouncycastle.crypto.generators.SCrypt
import java.security.SecureRandom

object Crypto {

    private const val N = 32768
    private const val r = 8
    private const val p = 1
    private const val dkLen = 512

    private val random = SecureRandom()

    fun hashNewPassword(password: String): SaltAndHash {
        val salt = ByteArray(64)
        random.nextBytes(salt)
        val hash = scrypt(password.toByteArray(), salt)
        return SaltAndHash(salt, hash)
    }

    fun check(password: String, salt: ByteArray, hash: ByteArray): Boolean =
        scrypt(password.toByteArray(), salt).contentEquals(hash)

    private fun scrypt(passphrase: ByteArray, salt: ByteArray) =
        SCrypt.generate(passphrase, salt, N, r, p, dkLen)!!

    data class SaltAndHash(val salt: ByteArray, val hash: ByteArray) {
        override fun equals(other: Any?) = throw UnsupportedOperationException()
        override fun hashCode() = throw UnsupportedOperationException()
    }

}
