package cn.repigeons.njnu.classroom.controller


import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.model.RequestPayload
import cn.repigeons.njnu.classroom.commons.util.JwtUtil
import cn.repigeons.njnu.classroom.mbg.mapper.*
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
    private val userFavoritesMapper: UserFavoritesMapper
) {
    /**
     * 查询用户收藏
     */
    @GetMapping("favorites")
    fun getFavorites(
        @RequestHeader("Authorization") token: String
    ): CommonResponse<*> {
        val openid = token2openid(token)
            ?: return CommonResponse.unauthorized()
        val records = userFavoritesMapper.select {
            it.where(UserFavoritesDynamicSqlSupport.openid, isEqualTo(openid))
        }
        val data = records.map { record ->
            UserFavoritesDTO(
                id = record.id,
                weekday = Weekday.valueOf(record.weekday),
                jcKs = record.ksjc,
                jcJs = record.jsjc,
                place = record.place,
                color = record.color,
                remark = GsonUtils.fromJson(record.remark),
            )
        }
        return CommonResponse.success(data)
    }

    /**
     * 新增/修改用户收藏
     */
    @PostMapping("favorites")
    fun saveFavorites(
        @RequestHeader("Authorization") token: String,
        @RequestBody payload: UserFavoritesDTO
    ): CommonResponse<*> {
        val openid = token2openid(token)
            ?: return CommonResponse.unauthorized()
        val record = UserFavorites().apply {
            this.id = payload.id
            this.openid = openid
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
        return CommonResponse.success(
            mapOf(
                "id" to record.id
            )
        )
    }

    /**
     * 删除用户收藏
     */
    @DeleteMapping("favorites")
    fun deleteFavorites(
        @RequestHeader("Authorization") token: String,
        @RequestBody payload: RequestPayload<Long>
    ): CommonResponse<*> {
        val openid = token2openid(token)
            ?: return CommonResponse.unauthorized()
        val id = requireNotNull(payload["id"]) { "请求参数缺失：id" }
        val record = userFavoritesMapper.selectByPrimaryKey(id)
            .getOrElse { return CommonResponse.failed("记录不存在") }
        if (record.openid != openid)
            return CommonResponse.forbidden()
        userFavoritesMapper.deleteByPrimaryKey(id)
        return CommonResponse.success()
    }

    private fun token2openid(token: String) =
        token.takeIf { token.startsWith(JwtUtil.TOKEN_HEAD) }
            ?.let { JwtUtil.parse(it) }
            ?.subject
}