package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.PositionsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.Positions;
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
public interface PositionsMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, name, latitude, longitude, kind);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Positions> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="PositionsResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.REAL),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.REAL),
        @Result(column="kind", property="kind", jdbcType=JdbcType.SMALLINT)
    })
    List<Positions> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("PositionsResult")
    Optional<Positions> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, positions, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, positions, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(Positions row) {
        return MyBatis3Utils.insert(this::insert, row, positions, c ->
            c.map(name).toProperty("name")
            .map(latitude).toProperty("latitude")
            .map(longitude).toProperty("longitude")
            .map(kind).toProperty("kind")
        );
    }

    default int insertSelective(Positions row) {
        return MyBatis3Utils.insert(this::insert, row, positions, c ->
            c.map(name).toPropertyWhenPresent("name", row::getName)
            .map(latitude).toPropertyWhenPresent("latitude", row::getLatitude)
            .map(longitude).toPropertyWhenPresent("longitude", row::getLongitude)
            .map(kind).toPropertyWhenPresent("kind", row::getKind)
        );
    }

    default Optional<Positions> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, positions, completer);
    }

    default List<Positions> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, positions, completer);
    }

    default List<Positions> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, positions, completer);
    }

    default Optional<Positions> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, positions, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Positions row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(name).equalTo(row::getName)
                .set(latitude).equalTo(row::getLatitude)
                .set(longitude).equalTo(row::getLongitude)
                .set(kind).equalTo(row::getKind);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Positions row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(name).equalToWhenPresent(row::getName)
                .set(latitude).equalToWhenPresent(row::getLatitude)
                .set(longitude).equalToWhenPresent(row::getLongitude)
                .set(kind).equalToWhenPresent(row::getKind);
    }

    default int updateByPrimaryKey(Positions row) {
        return update(c ->
            c.set(name).equalTo(row::getName)
            .set(latitude).equalTo(row::getLatitude)
            .set(longitude).equalTo(row::getLongitude)
            .set(kind).equalTo(row::getKind)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(Positions row) {
        return update(c ->
            c.set(name).equalToWhenPresent(row::getName)
            .set(latitude).equalToWhenPresent(row::getLatitude)
            .set(longitude).equalToWhenPresent(row::getLongitude)
            .set(kind).equalToWhenPresent(row::getKind)
            .where(id, isEqualTo(row::getId))
        );
    }
}