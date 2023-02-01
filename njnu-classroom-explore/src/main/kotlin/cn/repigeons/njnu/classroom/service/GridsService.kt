package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.model.GridVO

interface GridsService {
    fun getGrids(): List<GridVO>
    fun flushGrids(): List<GridVO>
}