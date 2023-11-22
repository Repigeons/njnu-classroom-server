package cn.repigeons.njnu.classroom.commons.utils

import com.alibaba.ttl.TtlCallable
import java.util.concurrent.*

/**
 * 自定义线程池
 */
abstract class AbstractThreadPool(
    /**
     * 核心线程数量
     */
    corePoolSize: Int,
    /**
     * 最大线程数量
     */
    maximumPoolSize: Int,
    /**
     * 空闲时长(seconds)
     */
    keepAliveTime: Long = 10_000,
    /**
     * 阻塞队列容量
     */
    blockingQueueCapacity: Int = 500,
) : ThreadPoolExecutor(
    corePoolSize,
    maximumPoolSize,
    keepAliveTime,
    TimeUnit.MILLISECONDS,
    LinkedBlockingQueue(blockingQueueCapacity),
    Executors.defaultThreadFactory(),
    DiscardPolicy()
) {
    fun <R> supplyAsync(block: () -> R): CompletableFuture<R> =
        CompletableFuture.supplyAsync(TtlCallable.get(block)!!::call, this)

    fun runAsync(block: () -> Unit): CompletableFuture<Void> =
        CompletableFuture.runAsync(TtlCallable.get(block)!!::call, this)

    companion object {
        val availableProcessors = Runtime.getRuntime().availableProcessors()
    }
}