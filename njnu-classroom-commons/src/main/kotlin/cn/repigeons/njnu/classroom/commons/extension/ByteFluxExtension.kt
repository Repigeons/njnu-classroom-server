package cn.repigeons.njnu.classroom.commons.extension

import kotlinx.coroutines.reactor.awaitSingle
import reactor.core.publisher.Flux

typealias ByteFlux = Flux<Byte>
typealias ByteArrayFlux = Flux<ByteArray>

fun ByteArray.toByteFlux(): ByteFlux = Flux.fromIterable(asIterable())
fun ByteArrayFlux.toByteFlux(): ByteFlux = flatMapSequential { it.toByteFlux() }
fun ByteFlux.toByteArrayFlux(maxSize: Int = 8 * 1024): ByteArrayFlux = buffer(maxSize).map { it.toByteArray() }
fun ByteArray.toByteArrayFlux(): ByteArrayFlux = toByteFlux().toByteArrayFlux()

@JvmName("byteFluxToByteArray")
suspend fun ByteFlux.toByteArray(): ByteArray = collectList().map { it.toByteArray() }.awaitSingle()

@JvmName("byteArrayFluxToByteArray")
suspend fun ByteArrayFlux.toByteArray(): ByteArray = toByteFlux().toByteArray()
