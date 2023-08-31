package cn.repigeons.njnu.classroom.commons.httpTrace

import cn.repigeons.njnu.classroom.commons.extension.toByteArray
import org.reactivestreams.Publisher
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebExchangeDecorator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal class ExchangeTrace(
    exchange: ServerWebExchange
) : ServerWebExchangeDecorator(exchange) {
    private val requestTrace = RequestTrace(exchange.request)
    private val responseTrace = ResponseTrace(exchange.response)
    override fun getRequest() = requestTrace
    override fun getResponse() = responseTrace

    class RequestTrace(request: ServerHttpRequest) : ServerHttpRequestDecorator(request) {
        private val contentType = request.headers.contentType
        private val stringBuilder = StringBuilder()
        fun body() = stringBuilder.toString()
        private fun capture(buffer: DataBuffer) {
            if (contentType !in REQUEST_CONTENT_TYPE) return
            buffer.toByteArray()
                .toString(Charsets.UTF_8)
                .asSequence()
                .run(stringBuilder::append)
        }

        override fun getBody(): Flux<DataBuffer> =
            super.getBody()
                .doOnNext(this::capture)
    }

    class ResponseTrace(response: ServerHttpResponse) : ServerHttpResponseDecorator(response) {
        private val contentType = response.headers.contentType
        private val stringBuilder = StringBuilder()
        fun body() = stringBuilder.toString()
        private fun capture(buffer: DataBuffer) {
            if (contentType !in RESPONSE_CONTENT_TYPE) return
            buffer.toByteArray()
                .toString(Charsets.UTF_8)
                .asSequence()
                .run(stringBuilder::append)
        }

        override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> =
            Flux.from(body)
                .doOnNext(this::capture)
                .run { super.writeWith(this) }
    }
}