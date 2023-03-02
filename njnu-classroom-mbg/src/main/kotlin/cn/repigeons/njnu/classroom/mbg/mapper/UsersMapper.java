package cn.repigeons.njnu.classroom.mbg.mapper;

import cn.repigeons.njnu.classroom.mbg.model.Users;
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

import static cn.repigeons.njnu.classroom.mbg.mapper.UsersDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface UsersMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(openid, firstLoginTime, lastLoginTime);

    static UpdateDSL<UpdateModel> updateAllColumns(Users row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(openid).equalTo(row::getOpenid)
                .set(firstLoginTime).equalTo(row::getFirstLoginTime)
                .set(lastLoginTime).equalTo(row::getLastLoginTime);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Users row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(openid).equalToWhenPresent(row::getOpenid)
                .set(firstLoginTime).equalToWhenPresent(row::getFirstLoginTime)
                .set(lastLoginTime).equalToWhenPresent(row::getLastLoginTime);
    }

    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    int insert(InsertStatementProvider<Users> insertStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "UsersResult", value = {
            @Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "first_login_time", property = "firstLoginTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_login_time", property = "lastLoginTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<Users> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("UsersResult")
    Optional<Users> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, users, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, users, completer);
    }

    default int deleteByPrimaryKey(String openid_) {
        return delete(c ->
                c.where(openid, isEqualTo(openid_))
        );
    }

    default int insert(Users row) {
        return MyBatis3Utils.insert(this::insert, row, users, c ->
                c.map(openid).toProperty("openid")
                        .map(firstLoginTime).toProperty("firstLoginTime")
                        .map(lastLoginTime).toProperty("lastLoginTime")
        );
    }

    default int insertSelective(Users row) {
        return MyBatis3Utils.insert(this::insert, row, users, c ->
                c.map(openid).toPropertyWhenPresent("openid", row::getOpenid)
                        .map(firstLoginTime).toPropertyWhenPresent("firstLoginTime", row::getFirstLoginTime)
                        .map(lastLoginTime).toPropertyWhenPresent("lastLoginTime", row::getLastLoginTime)
        );
    }

    default Optional<Users> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, users, completer);
    }

    default List<Users> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, users, completer);
    }

    default List<Users> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, users, completer);
    }

    default Optional<Users> selectByPrimaryKey(String openid_) {
        return selectOne(c ->
                c.where(openid, isEqualTo(openid_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, users, completer);
    }

    default int updateByPrimaryKey(Users row) {
        return update(c ->
                c.set(firstLoginTime).equalTo(row::getFirstLoginTime)
                        .set(lastLoginTime).equalTo(row::getLastLoginTime)
                        .where(openid, isEqualTo(row::getOpenid))
        );
    }

    default int updateByPrimaryKeySelective(Users row) {
        return update(c ->
                c.set(firstLoginTime).equalToWhenPresent(row::getFirstLoginTime)
                        .set(lastLoginTime).equalToWhenPresent(row::getLastLoginTime)
                        .where(openid, isEqualTo(row::getOpenid))
        );
    }
}