package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.GridsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.Grids;
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
public interface GridsMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, text, imgUrl, url, method, button, active);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Grids> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="GridsResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="text", property="text", jdbcType=JdbcType.VARCHAR),
        @Result(column="img_url", property="imgUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="method", property="method", jdbcType=JdbcType.VARCHAR),
        @Result(column="button", property="button", jdbcType=JdbcType.VARCHAR),
        @Result(column="active", property="active", jdbcType=JdbcType.BIT)
    })
    List<Grids> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("GridsResult")
    Optional<Grids> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, grids, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, grids, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(Grids row) {
        return MyBatis3Utils.insert(this::insert, row, grids, c ->
            c.map(text).toProperty("text")
            .map(imgUrl).toProperty("imgUrl")
            .map(url).toProperty("url")
            .map(method).toProperty("method")
            .map(button).toProperty("button")
            .map(active).toProperty("active")
        );
    }

    default int insertSelective(Grids row) {
        return MyBatis3Utils.insert(this::insert, row, grids, c ->
            c.map(text).toPropertyWhenPresent("text", row::getText)
            .map(imgUrl).toPropertyWhenPresent("imgUrl", row::getImgUrl)
            .map(url).toPropertyWhenPresent("url", row::getUrl)
            .map(method).toPropertyWhenPresent("method", row::getMethod)
            .map(button).toPropertyWhenPresent("button", row::getButton)
            .map(active).toPropertyWhenPresent("active", row::getActive)
        );
    }

    default Optional<Grids> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, grids, completer);
    }

    default List<Grids> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, grids, completer);
    }

    default List<Grids> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, grids, completer);
    }

    default Optional<Grids> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, grids, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Grids row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(text).equalTo(row::getText)
                .set(imgUrl).equalTo(row::getImgUrl)
                .set(url).equalTo(row::getUrl)
                .set(method).equalTo(row::getMethod)
                .set(button).equalTo(row::getButton)
                .set(active).equalTo(row::getActive);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Grids row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(text).equalToWhenPresent(row::getText)
                .set(imgUrl).equalToWhenPresent(row::getImgUrl)
                .set(url).equalToWhenPresent(row::getUrl)
                .set(method).equalToWhenPresent(row::getMethod)
                .set(button).equalToWhenPresent(row::getButton)
                .set(active).equalToWhenPresent(row::getActive);
    }

    default int updateByPrimaryKey(Grids row) {
        return update(c ->
            c.set(text).equalTo(row::getText)
            .set(imgUrl).equalTo(row::getImgUrl)
            .set(url).equalTo(row::getUrl)
            .set(method).equalTo(row::getMethod)
            .set(button).equalTo(row::getButton)
            .set(active).equalTo(row::getActive)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(Grids row) {
        return update(c ->
            c.set(text).equalToWhenPresent(row::getText)
            .set(imgUrl).equalToWhenPresent(row::getImgUrl)
            .set(url).equalToWhenPresent(row::getUrl)
            .set(method).equalToWhenPresent(row::getMethod)
            .set(button).equalToWhenPresent(row::getButton)
            .set(active).equalToWhenPresent(row::getActive)
            .where(id, isEqualTo(row::getId))
        );
    }
}