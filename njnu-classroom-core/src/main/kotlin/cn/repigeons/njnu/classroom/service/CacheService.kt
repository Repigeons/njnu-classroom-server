package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.model.ClassroomVO
import cn.repigeons.njnu.classroom.model.PositionVO
import java.util.concurrent.CompletableFuture

interface CacheService {
    fun getClassrooms(): List<ClassroomVO>
    fun flushClassrooms(): List<ClassroomVO>

    fun getBuildingPositions(): List<PositionVO>
    fun flushBuildingPositions(): List<PositionVO>

    fun flushCache(): CompletableFuture<*>
}