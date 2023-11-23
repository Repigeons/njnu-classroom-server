package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.model.Timetable;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 课程表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface ITimetableService extends IService<Timetable> {
    void truncate();

    void cloneFromKcb();

    @NotNull
    Page<Timetable> query(
            @NotNull Short jcKs,
            @NotNull Short jcJs,
            @Nullable String weekday,
            @Nullable String jxlmc,
            @Nullable String zylxdm,
            @Nullable String keyword,
            @NotNull Page<Timetable> page
    );
}