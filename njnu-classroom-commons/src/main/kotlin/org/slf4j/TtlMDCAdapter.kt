package org.slf4j

import com.alibaba.ttl.TransmittableThreadLocal
import org.slf4j.spi.MDCAdapter
import java.util.*

object TtlMDCAdapter : MDCAdapter {
    private val logger = LoggerFactory.getLogger(javaClass)
    private const val WRITE_OPERATION = 1
    private const val MAP_COPY_OPERATION = 2
    private val copyOnInheritThreadLocal = TransmittableThreadLocal<MutableMap<String, String>>()
    private val lastOperation = ThreadLocal<Int>()

    private fun getAndSetLastOperation(op: Int): Int? =
        lastOperation.get().also { lastOperation.set(op) }

    private fun wasLastOpReadOrNull(lastOp: Int?): Boolean =
        lastOp == null || lastOp == MAP_COPY_OPERATION

    private fun duplicateAndInsertNewMap(oldMap: Map<String, String>?): MutableMap<String, String> {
        val newMap = Collections.synchronizedMap<String, String>(mutableMapOf())
        if (oldMap != null) synchronized(oldMap) { newMap.putAll(oldMap) }
        copyOnInheritThreadLocal.set(newMap)
        return newMap
    }

    override fun get(key: String) = copyOnInheritThreadLocal.get()?.get(key)
    override fun put(key: String, value: String) {
        val oldMap: MutableMap<String, String>? = copyOnInheritThreadLocal.get()
        val lastOp = getAndSetLastOperation(WRITE_OPERATION)
        if (oldMap != null && !wasLastOpReadOrNull(lastOp)) {
            oldMap[key] = value
        } else {
            val newMap = duplicateAndInsertNewMap(oldMap)
            newMap[key] = value
        }
    }

    override fun remove(key: String) {
        copyOnInheritThreadLocal.get()?.also { oldMap ->
            val lastOp = getAndSetLastOperation(WRITE_OPERATION)
            if (wasLastOpReadOrNull(lastOp)) {
                val newMap = duplicateAndInsertNewMap(oldMap)
                newMap.remove(key)
            } else {
                oldMap.remove(key)
            }
        }
    }

    override fun clear() {
        lastOperation.set(WRITE_OPERATION)
        copyOnInheritThreadLocal.remove()
    }

    val keys: Set<String> get() = propertyMap.keys
    private val propertyMap: Map<String, String>
        get() {
            lastOperation.set(MAP_COPY_OPERATION)
            return copyOnInheritThreadLocal.get()
        }

    override fun getCopyOfContextMap(): Map<String, String>? = copyOnInheritThreadLocal.get()?.let(::HashMap)
    override fun setContextMap(contextMap: Map<String, String>) {
        lastOperation.set(WRITE_OPERATION)
        val newMap = Collections.synchronizedMap<String, String>(mutableMapOf())
        newMap.putAll(contextMap)
        copyOnInheritThreadLocal.set(newMap)
    }

    init {
        logger.info("TtlMDCAdapter - Initializing...")
        MDC.mdcAdapter = this
        logger.info("TtlMDCAdapter - Initialized.")
    }

    override fun pushByKey(key: String, value: String) = Unit
    override fun popByKey(key: String): String? = null
    override fun getCopyOfDequeByKey(key: String): Deque<String>? = null
    override fun clearDequeByKey(key: String) = Unit
}