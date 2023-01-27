package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.ShuttleDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.Shuttle;
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
public interface ShuttleMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, route, startTime, startStation, endStation, shuttleCount, working);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Shuttle> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ShuttleResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="route", property="route", jdbcType=JdbcType.SMALLINT),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_station", property="startStation", jdbcType=JdbcType.VARCHAR),
        @Result(column="end_station", property="endStation", jdbcType=JdbcType.VARCHAR),
        @Result(column="shuttle_count", property="shuttleCount", jdbcType=JdbcType.INTEGER),
        @Result(column="working", property="working", jdbcType=JdbcType.CHAR)
    })
    List<Shuttle> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ShuttleResult")
    Optional<Shuttle> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, shuttle, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, shuttle, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(Shuttle row) {
        return MyBatis3Utils.insert(this::insert, row, shuttle, c ->
            c.map(route).toProperty("route")
            .map(startTime).toProperty("startTime")
            .map(startStation).toProperty("startStation")
            .map(endStation).toProperty("endStation")
            .map(shuttleCount).toProperty("shuttleCount")
            .map(working).toProperty("working")
        );
    }

    default int insertSelective(Shuttle row) {
        return MyBatis3Utils.insert(this::insert, row, shuttle, c ->
            c.map(route).toPropertyWhenPresent("route", row::getRoute)
            .map(startTime).toPropertyWhenPresent("startTime", row::getStartTime)
            .map(startStation).toPropertyWhenPresent("startStation", row::getStartStation)
            .map(endStation).toPropertyWhenPresent("endStation", row::getEndStation)
            .map(shuttleCount).toPropertyWhenPresent("shuttleCount", row::getShuttleCount)
            .map(working).toPropertyWhenPresent("working", row::getWorking)
        );
    }

    default Optional<Shuttle> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, shuttle, completer);
    }

    default List<Shuttle> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, shuttle, completer);
    }

    default List<Shuttle> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, shuttle, completer);
    }

    default Optional<Shuttle> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, shuttle, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Shuttle row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(route).equalTo(row::getRoute)
                .set(startTime).equalTo(row::getStartTime)
                .set(startStation).equalTo(row::getStartStation)
                .set(endStation).equalTo(row::getEndStation)
                .set(shuttleCount).equalTo(row::getShuttleCount)
                .set(working).equalTo(row::getWorking);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Shuttle row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(route).equalToWhenPresent(row::getRoute)
                .set(startTime).equalToWhenPresent(row::getStartTime)
                .set(startStation).equalToWhenPresent(row::getStartStation)
                .set(endStation).equalToWhenPresent(row::getEndStation)
                .set(shuttleCount).equalToWhenPresent(row::getShuttleCount)
                .set(working).equalToWhenPresent(row::getWorking);
    }

    default int updateByPrimaryKey(Shuttle row) {
        return update(c ->
            c.set(route).equalTo(row::getRoute)
            .set(startTime).equalTo(row::getStartTime)
            .set(startStation).equalTo(row::getStartStation)
            .set(endStation).equalTo(row::getEndStation)
            .set(shuttleCount).equalTo(row::getShuttleCount)
            .set(working).equalTo(row::getWorking)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(Shuttle row) {
        return update(c ->
            c.set(route).equalToWhenPresent(row::getRoute)
            .set(startTime).equalToWhenPresent(row::getStartTime)
            .set(startStation).equalToWhenPresent(row::getStartStation)
            .set(endStation).equalToWhenPresent(row::getEndStation)
            .set(shuttleCount).equalToWhenPresent(row::getShuttleCount)
            .set(working).equalToWhenPresent(row::getWorking)
            .where(id, isEqualTo(row::getId))
        );
    }
}