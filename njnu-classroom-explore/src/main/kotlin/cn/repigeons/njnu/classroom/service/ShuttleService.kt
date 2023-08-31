package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.PositionVO
import cn.repigeons.njnu.classroom.model.ShuttleRoute
import org.springframework.web.multipart.MultipartFile

interface ShuttleService {
    fun getStationPosition(): List<PositionVO>
    fun flushStationPosition(): List<PositionVO>
    fun getRoute(weekday: Weekday, route: Short): List<ShuttleRoute>
    suspend fun sendShuttleImage(file: MultipartFile)
}