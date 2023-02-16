package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.util.EmailUtil
import cn.repigeons.njnu.classroom.mbg.dao.ShuttleDAO
import cn.repigeons.njnu.classroom.mbg.mapper.PositionsDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.PositionsMapper
import cn.repigeons.njnu.classroom.model.PositionVO
import cn.repigeons.njnu.classroom.model.ShuttleRoute
import cn.repigeons.njnu.classroom.service.ShuttleService
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.concurrent.CompletableFuture

@Service
@RefreshScope
open class ShuttleServiceImpl(
    private val shuttleDAO: ShuttleDAO,
    private val positionsMapper: PositionsMapper,
) : ShuttleService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Cacheable("shuttle-stations-position")
    override fun getStationPosition() = flushStationPosition()

    @CachePut("shuttle-stations-position")
    override fun flushStationPosition(): List<PositionVO> {
        return positionsMapper.select {
            it.where(PositionsDynamicSqlSupport.kind, isEqualTo(2))
        }.map { record ->
            PositionVO(
                name = record.name,
                position = listOf(record.latitude, record.longitude)
            )
        }
    }

    @Cacheable("shuttle-route", key = "#weekday + '::' + #route")
    override fun getRoute(weekday: Weekday, route: Short): List<ShuttleRoute> {
        val day = if (weekday.ordinal > 0) weekday.ordinal else 7
        val shuttleRoute = shuttleDAO.selectRoute(day, 1)
        return shuttleRoute.map {
            val item = ShuttleRoute(
                startTime = it.startTime,
                startStation = it.startStation,
                endStation = it.endStation,
            )
            (1..it.shuttleCount).map { item }
        }.flatten()
    }

    override fun sendShuttleImage(file: MultipartFile): CompletableFuture<*> =
        CompletableFuture.supplyAsync {
            val filename = file.originalFilename
            logger.info("upload shuttle image: {}", filename)
            val extension = filename?.split('.')?.lastOrNull()
            val attachment = File.createTempFile("shuttle_", extension).apply {
                deleteOnExit()
                file.transferTo(this)
            }
            logger.info("send shuttle image file: {}", attachment.name)
            EmailUtil.sendFile(
                nickname = "南师教室",
                subject = "【南师教室】有人上传校车时刻表.${extension}",
                content = "${filename}\n${attachment.name}",
                attachments = arrayOf(attachment),
            )
        }
}