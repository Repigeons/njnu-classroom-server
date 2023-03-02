package cn.repigeons.njnu.classroom.mbg.mapper;

import cn.repigeons.njnu.classroom.mbg.model.UserFavorites;
import org.apache.ibatis.annotations.*;
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

import java.util.List;
import java.util.Optional;

import static cn.repigeons.njnu.classroom.mbg.mapper.UserFavoritesDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface UserFavoritesMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, openid, title, weekday, ksjc, jsjc, place, color, remark);

    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "row.id", before = false, resultType = Long.class)
    int insert(InsertStatementProvider<UserFavorites> insertStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "UserFavoritesResult", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "weekday", property = "weekday", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ksjc", property = "ksjc", jdbcType = JdbcType.SMALLINT),
            @Result(column = "jsjc", property = "jsjc", jdbcType = JdbcType.SMALLINT),
            @Result(column = "place", property = "place", jdbcType = JdbcType.VARCHAR),
            @Result(column = "color", property = "color", jdbcType = JdbcType.CHAR),
            @Result(column = "remark", property = "remark", jdbcType = JdbcType.LONGVARCHAR)
    })
    List<UserFavorites> selectMany(SelectStatementProvider selectStatement);

    static UpdateDSL<UpdateModel> updateAllColumns(UserFavorites row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(openid).equalTo(row::getOpenid)
                .set(title).equalTo(row::getTitle)
                .set(weekday).equalTo(row::getWeekday)
                .set(ksjc).equalTo(row::getKsjc)
                .set(jsjc).equalTo(row::getJsjc)
                .set(place).equalTo(row::getPlace)
                .set(color).equalTo(row::getColor)
                .set(remark).equalTo(row::getRemark);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(UserFavorites row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(openid).equalToWhenPresent(row::getOpenid)
                .set(title).equalToWhenPresent(row::getTitle)
                .set(weekday).equalToWhenPresent(row::getWeekday)
                .set(ksjc).equalToWhenPresent(row::getKsjc)
                .set(jsjc).equalToWhenPresent(row::getJsjc)
                .set(place).equalToWhenPresent(row::getPlace)
                .set(color).equalToWhenPresent(row::getColor)
                .set(remark).equalToWhenPresent(row::getRemark);
    }

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("UserFavoritesResult")
    Optional<UserFavorites> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, userFavorites, completer);
    }

    default int insert(UserFavorites row) {
        return MyBatis3Utils.insert(this::insert, row, userFavorites, c ->
                c.map(openid).toProperty("openid")
                        .map(title).toProperty("title")
                        .map(weekday).toProperty("weekday")
                        .map(ksjc).toProperty("ksjc")
                        .map(jsjc).toProperty("jsjc")
                        .map(place).toProperty("place")
                        .map(color).toProperty("color")
                        .map(remark).toProperty("remark")
        );
    }

    default int insertSelective(UserFavorites row) {
        return MyBatis3Utils.insert(this::insert, row, userFavorites, c ->
                c.map(openid).toPropertyWhenPresent("openid", row::getOpenid)
                        .map(title).toPropertyWhenPresent("title", row::getTitle)
                        .map(weekday).toPropertyWhenPresent("weekday", row::getWeekday)
                        .map(ksjc).toPropertyWhenPresent("ksjc", row::getKsjc)
                        .map(jsjc).toPropertyWhenPresent("jsjc", row::getJsjc)
                        .map(place).toPropertyWhenPresent("place", row::getPlace)
                        .map(color).toPropertyWhenPresent("color", row::getColor)
                        .map(remark).toPropertyWhenPresent("remark", row::getRemark)
        );
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, userFavorites, completer);
    }

    default int deleteByPrimaryKey(Long id_) {
        return delete(c ->
                c.where(id, isEqualTo(id_))
        );
    }

    default Optional<UserFavorites> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, userFavorites, completer);
    }

    default List<UserFavorites> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, userFavorites, completer);
    }

    default List<UserFavorites> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, userFavorites, completer);
    }

    default Optional<UserFavorites> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
                c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, userFavorites, completer);
    }

    default int updateByPrimaryKey(UserFavorites row) {
        return update(c ->
                c.set(openid).equalTo(row::getOpenid)
                        .set(title).equalTo(row::getTitle)
                        .set(weekday).equalTo(row::getWeekday)
                        .set(ksjc).equalTo(row::getKsjc)
                        .set(jsjc).equalTo(row::getJsjc)
                        .set(place).equalTo(row::getPlace)
                        .set(color).equalTo(row::getColor)
                        .set(remark).equalTo(row::getRemark)
                        .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(UserFavorites row) {
        return update(c ->
                c.set(openid).equalToWhenPresent(row::getOpenid)
                        .set(title).equalToWhenPresent(row::getTitle)
                        .set(weekday).equalToWhenPresent(row::getWeekday)
                        .set(ksjc).equalToWhenPresent(row::getKsjc)
                        .set(jsjc).equalToWhenPresent(row::getJsjc)
                        .set(place).equalToWhenPresent(row::getPlace)
                        .set(color).equalToWhenPresent(row::getColor)
                        .set(remark).equalToWhenPresent(row::getRemark)
                        .where(id, isEqualTo(row::getId))
        );
    }
}