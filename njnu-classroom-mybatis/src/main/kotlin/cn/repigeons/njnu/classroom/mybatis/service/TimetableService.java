package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.dao.TimetableDAO;
import cn.repigeons.njnu.classroom.mybatis.mapper.TimetableMapper;
import cn.repigeons.njnu.classroom.mybatis.model.Timetable;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static cn.repigeons.njnu.classroom.mybatis.model.table.TimetableTableDef.TIMETABLE;

/**
 * 课程表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class TimetableService extends ServiceImpl<TimetableMapper, Timetable> implements ITimetableService {
    @Resource
    private TimetableDAO dao;

    public void truncate() {
        dao.truncate();
    }

    public void cloneFromKcb() {
        dao.cloneFromKcb();
    }

    @NotNull
    public Page<Timetable> query(
            @NotNull Short jcKs,
            @NotNull Short jcJs,
            @Nullable String weekday,
            @Nullable String jxlmc,
            @Nullable String zylxdm,
            @Nullable String keyword,
            @NotNull Page<Timetable> page
    ) {
        QueryWrapper wrapper = new QueryWrapper()
                .ge(Timetable::getJcKs, jcKs)
                .le(Timetable::getJcJs, jcJs);
        wrapper.eq(Timetable::getWeekday, weekday, weekday != null);
        wrapper.eq(Timetable::getJxlmc, jxlmc, jxlmc != null);
        wrapper.eq(Timetable::getZylxdm, zylxdm, zylxdm != null);
        if (StringUtils.hasText(keyword)) {
            keyword = "%" + keyword + "%";
            wrapper.and(TIMETABLE.KCM.like(keyword).or(TIMETABLE.JYYTMS.like(keyword)));
        }
        return page(page, wrapper);
    }
}