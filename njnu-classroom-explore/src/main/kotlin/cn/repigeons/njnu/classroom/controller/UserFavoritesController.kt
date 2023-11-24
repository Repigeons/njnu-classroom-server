package cn.repigeons.njnu.classroom.controller


import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.PortalClient
import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.model.UserFavoritesDTO
import cn.repigeons.njnu.classroom.mybatis.model.UserFavorites
import cn.repigeons.njnu.classroom.mybatis.service.IUserFavoritesService
import com.mybatisflex.core.query.QueryWrapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrElse

@Tag(name = "用户收藏")
@RestController
@RequestMapping("user")
class UserFavoritesController(
    private val userFavoritesService: IUserFavoritesService,
    private val portalClient: PortalClient,
) {
    @Operation(summary = "查询用户收藏")
    @GetMapping("favorites")
    suspend fun getFavorites(): CommonResult<List<UserFavoritesDTO>> {
        val openid = withContext(Dispatchers.IO) {
            portalClient.token2openid().data
        }
        val records = userFavoritesService.list(
            QueryWrapper()
                .eq(UserFavorites::getOpenid, openid)
        )
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

    @Operation(summary = "新增用户收藏")
    @PostMapping("favorites")
    suspend fun saveFavorites(
        @RequestBody payload: UserFavoritesDTO
    ): CommonResult<*> {
        val openid = withContext(Dispatchers.IO) {
            portalClient.token2openid().data
        }
        val record = UserFavorites().apply {
            this.openid = openid
            this.title = payload.title
            this.weekday = payload.weekday.name
            this.ksjc = payload.jcKs
            this.jsjc = payload.jcJs
            this.place = payload.place
            this.color = payload.color
            this.remark = GsonUtils.toJson(payload.remark)
        }
        userFavoritesService.save(record)
        return CommonResult.success(
            mapOf(
                "id" to record.id
            )
        )
    }

    @Operation(summary = "删除用户收藏")
    @Parameter(name = "id", description = "删除记录id")
    @DeleteMapping("favorites/{id}")
    suspend fun deleteFavorites(
        @PathVariable id: Long,
    ): CommonResult<Nothing> {
        val openid = withContext(Dispatchers.IO) {
            portalClient.token2openid().data
        }
        val record = userFavoritesService.getByIdOpt(id)
            .getOrElse { return CommonResult.failed("记录不存在") }
        if (record.openid != openid)
            return CommonResult.forbidden()
        userFavoritesService.removeById(id)
        return CommonResult.success()
    }
}