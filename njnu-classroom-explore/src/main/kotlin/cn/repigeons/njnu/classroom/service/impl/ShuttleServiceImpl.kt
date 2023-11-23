package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.utils.EmailUtils
import cn.repigeons.njnu.classroom.model.PositionVO
import cn.repigeons.njnu.classroom.model.ShuttleRoute
import cn.repigeons.njnu.classroom.mybatis.model.Positions
import cn.repigeons.njnu.classroom.mybatis.service.IPositionsService
import cn.repigeons.njnu.classroom.mybatis.service.IShuttleService
import cn.repigeons.njnu.classroom.service.ShuttleService
import com.mybatisflex.core.query.QueryWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class ShuttleServiceImpl(
    private val positionsService: IPositionsService,
    private val shuttleService: IShuttleService,
) : ShuttleService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Cacheable("shuttle-stations-position")
    override fun getStationPosition() = flushStationPosition()

    @CachePut("shuttle-stations-position")
    override fun flushStationPosition(): List<PositionVO> {
        return positionsService.list(
            QueryWrapper()
                .eq(Positions::getKind, 2)
        ).map { record ->
            PositionVO(
                name = record.name,
                position = listOf(record.latitude, record.longitude)
            )
        }
    }

    @Cacheable("shuttle-route", key = "#weekday + '::' + #route")
    override fun getRoute(weekday: Weekday, route: Short): List<ShuttleRoute> {
        val shuttleRoute = shuttleService.getRoute(weekday.ordinal + 1, route)
        return shuttleRoute.map {
            val item = ShuttleRoute(
                startTime = it.startTime,
                startStation = it.startStation,
                endStation = it.endStation,
            )
            (1..it.shuttleCount).map { item }
        }.flatten()
    }

    override suspend fun sendShuttleImage(file: MultipartFile) {
        val filename = file.originalFilename
        logger.info("upload shuttle image: {}", filename)
        val extension = filename?.split('.')?.lastOrNull()
        val attachment = withContext(Dispatchers.IO) {
            File.createTempFile("shuttle_", extension).apply {
                deleteOnExit()
                file.transferTo(this)
            }
        }
        logger.info("send shuttle image file: {}", attachment.name)
        EmailUtils.sendFile(
            nickname = "南师教室",
            subject = "【南师教室】有人上传校车时刻表.${extension}",
            content = "${filename}\n${attachment.name}",
            attachments = arrayOf(attachment),
        )
    }
}