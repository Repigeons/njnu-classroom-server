package cn.repigeons.njnu.classroom.commons.api

import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper

data class CommonPage<T : Any>
internal constructor(
    val pageNum: Int,
    val pageSize: Int,
    val totalPage: Int,
    val total: Long,
    val list: List<T>
) {
    constructor(pageInfo: Page<*>, list: List<T>) : this(
        pageNum = pageInfo.pageNum,
        pageSize = pageInfo.pageSize,
        totalPage = pageInfo.pages,
        total = pageInfo.total,
        list = list,
    )

    fun <E : Any> map(block: (T) -> E) = CommonPage(
        pageNum = pageNum,
        pageSize = pageSize,
        totalPage = totalPage,
        total = total,
        list = list.map(block),
    )

    companion object {
        @JvmStatic
        fun <T : Any> query(pageNum: Int, pageSize: Int, select: () -> List<T>): CommonPage<T> =
            PageHelper.startPage<T>(pageNum, pageSize)
                .doSelectPage<T> { select() }
                .let { CommonPage(it, it.result) }
    }
}