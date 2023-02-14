package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.mbg.mapper.GridsDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.GridsMapper
import cn.repigeons.njnu.classroom.model.GridVO
import cn.repigeons.njnu.classroom.service.GridsService
import org.mybatis.dynamic.sql.util.kotlin.elements.isTrue
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
open class GridsServiceImpl(
    private val gridsMapper: GridsMapper
) : GridsService {
    @Cacheable("grids")
    override fun getGrids() = flushGrids()

    @CachePut("grids")
    override fun flushGrids(): List<GridVO> {
        val records = gridsMapper.select {
            it.where(GridsDynamicSqlSupport.active, isTrue())
        }
        return records.map { GridVO(it) }
    }
}