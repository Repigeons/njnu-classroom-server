package cn.repigeons.njnu.classroom.commons.extension

import org.springframework.core.io.buffer.DataBuffer
import reactor.core.publisher.Flux

fun DataBuffer.toByteArray(release: Boolean = false) =
    asInputStream(release).use { inputStream -> inputStream.readBytes().also { inputStream.reset() } }

fun Flux<DataBuffer>.toByteArrayFlux(release: Boolean = false): ByteArrayFlux =
    map { it.toByteArray(release) }
