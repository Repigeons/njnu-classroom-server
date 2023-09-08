package cn.repigeons.njnu.classroom.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClaims
import io.jsonwebtoken.security.Keys
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
private class JwtConfig(
    @Value("\${jwt.secret:}")
    val secret: String,
    @Value("\${jwt.expire:0}")
    val expire: Int,
) : InitializingBean {
    override fun afterPropertiesSet() {
        jwtConfig = this
    }
}

private lateinit var jwtConfig: JwtConfig

object JwtUtils {
    private const val TOKEN_HEAD = "Bearer"
    private val secretKey = Keys.hmacShaKeyFor(jwtConfig.secret.repeat(2).toByteArray())
    private val logger = LoggerFactory.getLogger(javaClass)

    fun generate(subject: String) = mono {
        val claims = DefaultClaims().setSubject(subject)
        val issuedAt = System.currentTimeMillis()
        val expireAt = issuedAt + jwtConfig.expire * 1000
        val builder = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date(issuedAt))
            .setExpiration(Date(expireAt))
            .signWith(secretKey, SignatureAlgorithm.HS256)
        builder.compact()
    }

    fun parse(token: String) = mono {
        try {
            val claimsJws = token.substringAfter(TOKEN_HEAD).trim()
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(claimsJws)
                .body
        } catch (e: Exception) {
            logger.error("token无效: {}", token)
            logger.error("token无效: {}", e.message, e)
            null
        }
    }
}
