package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.UserTimetableDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.UserTimetable;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.CommonCountMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonDeleteMapper;
import org.mybatis.dynamic.sql.util.mybatis3.CommonUpdateMapper;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface UserTimetableMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, openid, weekday, ksjc, jsjc, place, remark);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<UserTimetable> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="UserTimetableResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR),
        @Result(column="weekday", property="weekday", jdbcType=JdbcType.VARCHAR),
        @Result(column="ksjc", property="ksjc", jdbcType=JdbcType.SMALLINT),
        @Result(column="jsjc", property="jsjc", jdbcType=JdbcType.SMALLINT),
        @Result(column="place", property="place", jdbcType=JdbcType.VARCHAR),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<UserTimetable> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("UserTimetableResult")
    Optional<UserTimetable> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, userTimetable, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, userTimetable, completer);
    }

    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(UserTimetable row) {
        return MyBatis3Utils.insert(this::insert, row, userTimetable, c ->
            c.map(openid).toProperty("openid")
            .map(weekday).toProperty("weekday")
            .map(ksjc).toProperty("ksjc")
            .map(jsjc).toProperty("jsjc")
            .map(place).toProperty("place")
            .map(remark).toProperty("remark")
        );
    }

    default int insertSelective(UserTimetable row) {
        return MyBatis3Utils.insert(this::insert, row, userTimetable, c ->
            c.map(openid).toPropertyWhenPresent("openid", row::getOpenid)
            .map(weekday).toPropertyWhenPresent("weekday", row::getWeekday)
            .map(ksjc).toPropertyWhenPresent("ksjc", row::getKsjc)
            .map(jsjc).toPropertyWhenPresent("jsjc", row::getJsjc)
            .map(place).toPropertyWhenPresent("place", row::getPlace)
            .map(remark).toPropertyWhenPresent("remark", row::getRemark)
        );
    }

    default Optional<UserTimetable> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, userTimetable, completer);
    }

    default List<UserTimetable> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, userTimetable, completer);
    }

    default List<UserTimetable> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, userTimetable, completer);
    }

    default Optional<UserTimetable> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, userTimetable, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(UserTimetable row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(openid).equalTo(row::getOpenid)
                .set(weekday).equalTo(row::getWeekday)
                .set(ksjc).equalTo(row::getKsjc)
                .set(jsjc).equalTo(row::getJsjc)
                .set(place).equalTo(row::getPlace)
                .set(remark).equalTo(row::getRemark);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(UserTimetable row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(openid).equalToWhenPresent(row::getOpenid)
                .set(weekday).equalToWhenPresent(row::getWeekday)
                .set(ksjc).equalToWhenPresent(row::getKsjc)
                .set(jsjc).equalToWhenPresent(row::getJsjc)
                .set(place).equalToWhenPresent(row::getPlace)
                .set(remark).equalToWhenPresent(row::getRemark);
    }

    default int updateByPrimaryKey(UserTimetable row) {
        return update(c ->
            c.set(openid).equalTo(row::getOpenid)
            .set(weekday).equalTo(row::getWeekday)
            .set(ksjc).equalTo(row::getKsjc)
            .set(jsjc).equalTo(row::getJsjc)
            .set(place).equalTo(row::getPlace)
            .set(remark).equalTo(row::getRemark)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(UserTimetable row) {
        return update(c ->
            c.set(openid).equalToWhenPresent(row::getOpenid)
            .set(weekday).equalToWhenPresent(row::getWeekday)
            .set(ksjc).equalToWhenPresent(row::getKsjc)
            .set(jsjc).equalToWhenPresent(row::getJsjc)
            .set(place).equalToWhenPresent(row::getPlace)
            .set(remark).equalToWhenPresent(row::getRemark)
            .where(id, isEqualTo(row::getId))
        );
    }
}