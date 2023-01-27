package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.FeedbackDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.Feedback;
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
public interface FeedbackMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, time, jc, jasdm);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Feedback> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="FeedbackResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="jc", property="jc", jdbcType=JdbcType.SMALLINT),
        @Result(column="JASDM", property="jasdm", jdbcType=JdbcType.CHAR)
    })
    List<Feedback> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("FeedbackResult")
    Optional<Feedback> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, feedback, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, feedback, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(Feedback row) {
        return MyBatis3Utils.insert(this::insert, row, feedback, c ->
            c.map(time).toProperty("time")
            .map(jc).toProperty("jc")
            .map(jasdm).toProperty("jasdm")
        );
    }

    default int insertSelective(Feedback row) {
        return MyBatis3Utils.insert(this::insert, row, feedback, c ->
            c.map(time).toPropertyWhenPresent("time", row::getTime)
            .map(jc).toPropertyWhenPresent("jc", row::getJc)
            .map(jasdm).toPropertyWhenPresent("jasdm", row::getJasdm)
        );
    }

    default Optional<Feedback> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, feedback, completer);
    }

    default List<Feedback> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, feedback, completer);
    }

    default List<Feedback> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, feedback, completer);
    }

    default Optional<Feedback> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, feedback, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Feedback row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalTo(row::getTime)
                .set(jc).equalTo(row::getJc)
                .set(jasdm).equalTo(row::getJasdm);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Feedback row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalToWhenPresent(row::getTime)
                .set(jc).equalToWhenPresent(row::getJc)
                .set(jasdm).equalToWhenPresent(row::getJasdm);
    }

    default int updateByPrimaryKey(Feedback row) {
        return update(c ->
            c.set(time).equalTo(row::getTime)
            .set(jc).equalTo(row::getJc)
            .set(jasdm).equalTo(row::getJasdm)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(Feedback row) {
        return update(c ->
            c.set(time).equalToWhenPresent(row::getTime)
            .set(jc).equalToWhenPresent(row::getJc)
            .set(jasdm).equalToWhenPresent(row::getJasdm)
            .where(id, isEqualTo(row::getId))
        );
    }
}