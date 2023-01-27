package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.CorrectionDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.Correction;
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
public interface CorrectionMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, weekday, jxlmc, jsmph, jasdm, skzws, zylxdm, jcKs, jcJs, sfyxzx, jyytms, kcm);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Correction> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="CorrectionResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="weekday", property="weekday", jdbcType=JdbcType.VARCHAR),
        @Result(column="JXLMC", property="jxlmc", jdbcType=JdbcType.VARCHAR),
        @Result(column="jsmph", property="jsmph", jdbcType=JdbcType.VARCHAR),
        @Result(column="JASDM", property="jasdm", jdbcType=JdbcType.VARCHAR),
        @Result(column="SKZWS", property="skzws", jdbcType=JdbcType.INTEGER),
        @Result(column="zylxdm", property="zylxdm", jdbcType=JdbcType.VARCHAR),
        @Result(column="jc_ks", property="jcKs", jdbcType=JdbcType.SMALLINT),
        @Result(column="jc_js", property="jcJs", jdbcType=JdbcType.SMALLINT),
        @Result(column="SFYXZX", property="sfyxzx", jdbcType=JdbcType.BIT),
        @Result(column="jyytms", property="jyytms", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="kcm", property="kcm", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Correction> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("CorrectionResult")
    Optional<Correction> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, correction, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, correction, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(Correction row) {
        return MyBatis3Utils.insert(this::insert, row, correction, c ->
            c.map(weekday).toProperty("weekday")
            .map(jxlmc).toProperty("jxlmc")
            .map(jsmph).toProperty("jsmph")
            .map(jasdm).toProperty("jasdm")
            .map(skzws).toProperty("skzws")
            .map(zylxdm).toProperty("zylxdm")
            .map(jcKs).toProperty("jcKs")
            .map(jcJs).toProperty("jcJs")
            .map(sfyxzx).toProperty("sfyxzx")
            .map(jyytms).toProperty("jyytms")
            .map(kcm).toProperty("kcm")
        );
    }

    default int insertSelective(Correction row) {
        return MyBatis3Utils.insert(this::insert, row, correction, c ->
            c.map(weekday).toPropertyWhenPresent("weekday", row::getWeekday)
            .map(jxlmc).toPropertyWhenPresent("jxlmc", row::getJxlmc)
            .map(jsmph).toPropertyWhenPresent("jsmph", row::getJsmph)
            .map(jasdm).toPropertyWhenPresent("jasdm", row::getJasdm)
            .map(skzws).toPropertyWhenPresent("skzws", row::getSkzws)
            .map(zylxdm).toPropertyWhenPresent("zylxdm", row::getZylxdm)
            .map(jcKs).toPropertyWhenPresent("jcKs", row::getJcKs)
            .map(jcJs).toPropertyWhenPresent("jcJs", row::getJcJs)
            .map(sfyxzx).toPropertyWhenPresent("sfyxzx", row::getSfyxzx)
            .map(jyytms).toPropertyWhenPresent("jyytms", row::getJyytms)
            .map(kcm).toPropertyWhenPresent("kcm", row::getKcm)
        );
    }

    default Optional<Correction> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, correction, completer);
    }

    default List<Correction> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, correction, completer);
    }

    default List<Correction> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, correction, completer);
    }

    default Optional<Correction> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, correction, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Correction row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(weekday).equalTo(row::getWeekday)
                .set(jxlmc).equalTo(row::getJxlmc)
                .set(jsmph).equalTo(row::getJsmph)
                .set(jasdm).equalTo(row::getJasdm)
                .set(skzws).equalTo(row::getSkzws)
                .set(zylxdm).equalTo(row::getZylxdm)
                .set(jcKs).equalTo(row::getJcKs)
                .set(jcJs).equalTo(row::getJcJs)
                .set(sfyxzx).equalTo(row::getSfyxzx)
                .set(jyytms).equalTo(row::getJyytms)
                .set(kcm).equalTo(row::getKcm);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Correction row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(weekday).equalToWhenPresent(row::getWeekday)
                .set(jxlmc).equalToWhenPresent(row::getJxlmc)
                .set(jsmph).equalToWhenPresent(row::getJsmph)
                .set(jasdm).equalToWhenPresent(row::getJasdm)
                .set(skzws).equalToWhenPresent(row::getSkzws)
                .set(zylxdm).equalToWhenPresent(row::getZylxdm)
                .set(jcKs).equalToWhenPresent(row::getJcKs)
                .set(jcJs).equalToWhenPresent(row::getJcJs)
                .set(sfyxzx).equalToWhenPresent(row::getSfyxzx)
                .set(jyytms).equalToWhenPresent(row::getJyytms)
                .set(kcm).equalToWhenPresent(row::getKcm);
    }

    default int updateByPrimaryKey(Correction row) {
        return update(c ->
            c.set(weekday).equalTo(row::getWeekday)
            .set(jxlmc).equalTo(row::getJxlmc)
            .set(jsmph).equalTo(row::getJsmph)
            .set(jasdm).equalTo(row::getJasdm)
            .set(skzws).equalTo(row::getSkzws)
            .set(zylxdm).equalTo(row::getZylxdm)
            .set(jcKs).equalTo(row::getJcKs)
            .set(jcJs).equalTo(row::getJcJs)
            .set(sfyxzx).equalTo(row::getSfyxzx)
            .set(jyytms).equalTo(row::getJyytms)
            .set(kcm).equalTo(row::getKcm)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(Correction row) {
        return update(c ->
            c.set(weekday).equalToWhenPresent(row::getWeekday)
            .set(jxlmc).equalToWhenPresent(row::getJxlmc)
            .set(jsmph).equalToWhenPresent(row::getJsmph)
            .set(jasdm).equalToWhenPresent(row::getJasdm)
            .set(skzws).equalToWhenPresent(row::getSkzws)
            .set(zylxdm).equalToWhenPresent(row::getZylxdm)
            .set(jcKs).equalToWhenPresent(row::getJcKs)
            .set(jcJs).equalToWhenPresent(row::getJcJs)
            .set(sfyxzx).equalToWhenPresent(row::getSfyxzx)
            .set(jyytms).equalToWhenPresent(row::getJyytms)
            .set(kcm).equalToWhenPresent(row::getKcm)
            .where(id, isEqualTo(row::getId))
        );
    }
}