import cn.repigeons.commons.redisTemplate.Repigeons2JsonRedisSerializer
import cn.repigeons.njnu.classroom.CoreApplication
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableMapper
import cn.repigeons.njnu.classroom.model.EmptyClassroomVO
import org.junit.Test
import org.junit.runner.RunWith
import org.mybatis.dynamic.sql.util.kotlin.elements.isIn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.nio.charset.StandardCharsets

@ActiveProfiles("local")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [CoreApplication::class])
class EmptyClassroomsTests {
    @Autowired
    private lateinit var timetableMapper: TimetableMapper

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Test
    fun testSerializer() {
        val pairs = timetableMapper.select {
            it.where(TimetableDynamicSqlSupport.zylxdm, isIn("00", "10", "11"))
        }
            .groupBy {
                "${it.jxlmc}:${it.weekday}"
            }
            .map { (key, records) ->
                key to records.map { record ->
                    EmptyClassroomVO(
                        jasdm = record.jasdm,
                        jsmph = record.jsmph,
                        skzws = record.skzws,
                        jcKs = record.jcKs,
                        jcJs = record.jcJs,
                        zylxdm = record.zylxdm,
                    )
                }
            }

        @Suppress("UNCHECKED_CAST")
        val serializer = redisTemplate.hashValueSerializer as Repigeons2JsonRedisSerializer<Any>
        val bytes = serializer.serialize(pairs.first().second)
        println(bytes.toString(StandardCharsets.UTF_8))
    }
}
