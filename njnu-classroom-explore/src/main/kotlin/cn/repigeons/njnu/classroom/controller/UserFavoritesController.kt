package cn.repigeons.njnu.classroom.controller


import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.PortalClient
import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.mbg.mapper.UserFavoritesDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.UserFavoritesMapper
import cn.repigeons.njnu.classroom.mbg.model.UserFavorites
import cn.repigeons.njnu.classroom.model.UserFavoritesDTO
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrElse

/**
 * 用户收藏
 */
@RestController
@RequestMapping("user")
class UserFavoritesController(
    private val userFavoritesMapper: UserFavoritesMapper,
    private val portalClient: PortalClient,
) {
    /**
     * 查询用户收藏
     */
    @GetMapping("favorites")
    fun getFavorites(): CommonResult<*> {
        val openid = portalClient.token2openid().data
            ?: return CommonResult.unauthorized()
        val records = userFavoritesMapper.select {
            it.where(UserFavoritesDynamicSqlSupport.openid, isEqualTo(openid))
        }
        val data = records.map { record ->
            UserFavoritesDTO(
                id = record.id,
                title = record.title,
                weekday = Weekday.valueOf(record.weekday),
                jcKs = record.ksjc,
                jcJs = record.jsjc,
                place = record.place,
                color = record.color,
                remark = GsonUtils.fromJson(record.remark),
            )
        }
        return CommonResult.success(data)
    }

    /**
     * 新增/修改用户收藏
     */
    @PostMapping("favorites/{id}")
    fun saveFavorites(
        @PathVariable id: Long,
        @RequestBody payload: UserFavoritesDTO
    ): CommonResult<*> {
        val openid = portalClient.token2openid().data
            ?: return CommonResult.unauthorized()
        val record = UserFavorites().apply {
            this.id = id
            this.openid = openid
            this.title = payload.title
            this.weekday = payload.weekday.name
            this.ksjc = payload.jcKs
            this.jsjc = payload.jcJs
            this.place = payload.place
            this.color = payload.color
            this.remark = GsonUtils.toJson(payload.remark)
        }
        if (record.id == null)
            userFavoritesMapper.insert(record)
        else
            userFavoritesMapper.updateByPrimaryKey(record)
        return CommonResult.success(
            mapOf(
                "id" to record.id
            )
        )
    }

    @PostMapping("favorites")
    fun saveFavoritesTODO(
        @RequestBody payload: UserFavoritesDTO
    ): CommonResult<*> {
        val openid = portalClient.token2openid().data
            ?: return CommonResult.unauthorized()
        val record = UserFavorites().apply {
            this.id = payload.id
            this.openid = openid
            this.title = payload.title
            this.weekday = payload.weekday.name
            this.ksjc = payload.jcKs
            this.jsjc = payload.jcJs
            this.place = payload.place
            this.color = payload.color
            this.remark = GsonUtils.toJson(payload.remark)
        }
        if (record.id == null)
            userFavoritesMapper.insert(record)
        else
            userFavoritesMapper.updateByPrimaryKey(record)
        return CommonResult.success(
            mapOf(
                "id" to record.id
            )
        )
    }

    /**
     * 删除用户收藏
     */
    @DeleteMapping("favorites/{id}")
    fun deleteFavorites(
        @PathVariable id: Long,
    ): CommonResult<*> {
        val openid = portalClient.token2openid().data
            ?: return CommonResult.unauthorized()
        val record = userFavoritesMapper.selectByPrimaryKey(id)
            .getOrElse { return CommonResult.failed("记录不存在") }
        if (record.openid != openid)
            return CommonResult.forbidden()
        userFavoritesMapper.deleteByPrimaryKey(id)
        return CommonResult.success()
    }

    @Deprecated("不规范的接口")
    @DeleteMapping("favorites")
    fun deleteFavoritesTODO(
        @RequestBody map: Map<String, Long>,
    ): CommonResult<*> {
        val openid = portalClient.token2openid().data
            ?: return CommonResult.unauthorized()
        val id = map["id"]
        val record = userFavoritesMapper.selectByPrimaryKey(id)
            .getOrElse { return CommonResult.failed("记录不存在") }
        if (record.openid != openid)
            return CommonResult.forbidden()
        userFavoritesMapper.deleteByPrimaryKey(id)
        return CommonResult.success()
    }
}