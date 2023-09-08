package cn.repigeons.njnu.classroom.commons.api

import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import io.swagger.v3.oas.annotations.media.Schema

data class CommonPage<T : Any>
internal constructor(
    @Schema(description = "分页页号", defaultValue = "1")
    val pageNum: Int,
    @Schema(description = "分页大小", defaultValue = "10")
    val pageSize: Int,
    @Schema(description = "总页数")
    val totalPage: Int,
    @Schema(description = "总记录数")
    val total: Long,
    @Schema(description = "当前页记录")
    val list: List<T>,
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