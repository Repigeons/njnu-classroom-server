package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.model.TimetableVO

interface OverviewService {
    suspend fun getOverview(jasdm: String): List<TimetableVO>
}