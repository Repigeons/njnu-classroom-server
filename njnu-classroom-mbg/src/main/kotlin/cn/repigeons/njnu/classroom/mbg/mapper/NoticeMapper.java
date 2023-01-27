package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.NoticeDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.Notice;
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
public interface NoticeMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, time, text);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Notice> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="NoticeResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="text", property="text", jdbcType=JdbcType.VARCHAR)
    })
    List<Notice> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("NoticeResult")
    Optional<Notice> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, notice, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, notice, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(Notice row) {
        return MyBatis3Utils.insert(this::insert, row, notice, c ->
            c.map(time).toProperty("time")
            .map(text).toProperty("text")
        );
    }

    default int insertSelective(Notice row) {
        return MyBatis3Utils.insert(this::insert, row, notice, c ->
            c.map(time).toPropertyWhenPresent("time", row::getTime)
            .map(text).toPropertyWhenPresent("text", row::getText)
        );
    }

    default Optional<Notice> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, notice, completer);
    }

    default List<Notice> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, notice, completer);
    }

    default List<Notice> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, notice, completer);
    }

    default Optional<Notice> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, notice, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Notice row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalTo(row::getTime)
                .set(text).equalTo(row::getText);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Notice row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalToWhenPresent(row::getTime)
                .set(text).equalToWhenPresent(row::getText);
    }

    default int updateByPrimaryKey(Notice row) {
        return update(c ->
            c.set(time).equalTo(row::getTime)
            .set(text).equalTo(row::getText)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(Notice row) {
        return update(c ->
            c.set(time).equalToWhenPresent(row::getTime)
            .set(text).equalToWhenPresent(row::getText)
            .where(id, isEqualTo(row::getId))
        );
    }
}