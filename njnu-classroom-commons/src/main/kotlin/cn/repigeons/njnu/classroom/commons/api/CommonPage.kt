package cn.repigeons.njnu.classroom.commons.api

import com.mybatisflex.core.paginate.Page
import io.swagger.v3.oas.annotations.media.Schema

data class CommonPage<T : Any>
internal constructor(
    @Schema(description = "分页页号", defaultValue = "1")
    val pageNum: Long,
    @Schema(description = "分页大小", defaultValue = "10")
    val pageSize: Long,
    @Schema(description = "总页数")
    val totalPage: Long,
    @Schema(description = "总记录数")
    val total: Long,
    @Schema(description = "当前页记录")
    val list: List<T>,
) {
    constructor(page: Page<T>) : this(
        pageNum = page.pageNumber,
        pageSize = page.pageSize,
        totalPage = page.totalPage,
        total = page.totalRow,
        list = page.records,
    )

    fun <E : Any> map(block: (T) -> E) = CommonPage(
        pageNum = pageNum,
        pageSize = pageSize,
        totalPage = totalPage,
        total = total,
        list = list.map(block),
    )

    companion object {
        fun <T : Any> Page<T>.toCommonPage() = CommonPage(this)
        fun <T : Any, E : Any> Page<T>.mapToCommonPage(block: (T) -> E) = CommonPage(this).map(block)
    }
}