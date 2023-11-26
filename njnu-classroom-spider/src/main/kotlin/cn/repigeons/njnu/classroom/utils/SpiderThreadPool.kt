package cn.repigeons.njnu.classroom.utils

import cn.repigeons.njnu.classroom.commons.utils.AbstractThreadPool

/**
 * 爬虫专用线程池工具
 */
object SpiderThreadPool : AbstractThreadPool(
    corePoolSize = 5,
    maximumPoolSize = 20,
)