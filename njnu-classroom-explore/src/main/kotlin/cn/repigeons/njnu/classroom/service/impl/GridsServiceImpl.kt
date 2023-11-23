package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.model.GridVO
import cn.repigeons.njnu.classroom.mybatis.model.Grids
import cn.repigeons.njnu.classroom.service.GridsService
import com.mybatisflex.core.query.QueryWrapper
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class GridsServiceImpl : GridsService, cn.repigeons.njnu.classroom.mybatis.service.GridsService() {
    @Cacheable("grids")
    override fun getGrids() = flushGrids()

    @CachePut("grids")
    override fun flushGrids(): List<GridVO> {
        val records = list(
            QueryWrapper()
                .eq(Grids::getActive, true)
        )
        return records.map { GridVO(it) }
    }
}