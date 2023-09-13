package cn.repigeons.njnu.classroom.commons.utils

/**
 * 自定义线程池工具
 */
object ThreadPoolUtils : AbstractThreadPool(
    corePoolSize = availableProcessors + 1,
    maximumPoolSize = availableProcessors * 2,
)