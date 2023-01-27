package cn.repigeons.njnu.classroom.mbg.mapper;

import static cn.repigeons.njnu.classroom.mbg.mapper.KcbDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

import cn.repigeons.njnu.classroom.mbg.model.Kcb;
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
public interface KcbMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, jxlmc, jsmph, jasdm, skzws, zylxdm, jcKs, jcJs, weekday, sfyxzx, jyytms, kcm);

    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="row.id", before=false, resultType=Integer.class)
    int insert(InsertStatementProvider<Kcb> insertStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="KcbResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="JXLMC", property="jxlmc", jdbcType=JdbcType.VARCHAR),
        @Result(column="jsmph", property="jsmph", jdbcType=JdbcType.VARCHAR),
        @Result(column="JASDM", property="jasdm", jdbcType=JdbcType.CHAR),
        @Result(column="SKZWS", property="skzws", jdbcType=JdbcType.INTEGER),
        @Result(column="zylxdm", property="zylxdm", jdbcType=JdbcType.CHAR),
        @Result(column="jc_ks", property="jcKs", jdbcType=JdbcType.SMALLINT),
        @Result(column="jc_js", property="jcJs", jdbcType=JdbcType.SMALLINT),
        @Result(column="weekday", property="weekday", jdbcType=JdbcType.VARCHAR),
        @Result(column="SFYXZX", property="sfyxzx", jdbcType=JdbcType.BIT),
        @Result(column="jyytms", property="jyytms", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="kcm", property="kcm", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Kcb> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("KcbResult")
    Optional<Kcb> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, kcb, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, kcb, completer);
    }

    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(Kcb row) {
        return MyBatis3Utils.insert(this::insert, row, kcb, c ->
            c.map(jxlmc).toProperty("jxlmc")
            .map(jsmph).toProperty("jsmph")
            .map(jasdm).toProperty("jasdm")
            .map(skzws).toProperty("skzws")
            .map(zylxdm).toProperty("zylxdm")
            .map(jcKs).toProperty("jcKs")
            .map(jcJs).toProperty("jcJs")
            .map(weekday).toProperty("weekday")
            .map(sfyxzx).toProperty("sfyxzx")
            .map(jyytms).toProperty("jyytms")
            .map(kcm).toProperty("kcm")
        );
    }

    default int insertSelective(Kcb row) {
        return MyBatis3Utils.insert(this::insert, row, kcb, c ->
            c.map(jxlmc).toPropertyWhenPresent("jxlmc", row::getJxlmc)
            .map(jsmph).toPropertyWhenPresent("jsmph", row::getJsmph)
            .map(jasdm).toPropertyWhenPresent("jasdm", row::getJasdm)
            .map(skzws).toPropertyWhenPresent("skzws", row::getSkzws)
            .map(zylxdm).toPropertyWhenPresent("zylxdm", row::getZylxdm)
            .map(jcKs).toPropertyWhenPresent("jcKs", row::getJcKs)
            .map(jcJs).toPropertyWhenPresent("jcJs", row::getJcJs)
            .map(weekday).toPropertyWhenPresent("weekday", row::getWeekday)
            .map(sfyxzx).toPropertyWhenPresent("sfyxzx", row::getSfyxzx)
            .map(jyytms).toPropertyWhenPresent("jyytms", row::getJyytms)
            .map(kcm).toPropertyWhenPresent("kcm", row::getKcm)
        );
    }

    default Optional<Kcb> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, kcb, completer);
    }

    default List<Kcb> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, kcb, completer);
    }

    default List<Kcb> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, kcb, completer);
    }

    default Optional<Kcb> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, kcb, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(Kcb row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jxlmc).equalTo(row::getJxlmc)
                .set(jsmph).equalTo(row::getJsmph)
                .set(jasdm).equalTo(row::getJasdm)
                .set(skzws).equalTo(row::getSkzws)
                .set(zylxdm).equalTo(row::getZylxdm)
                .set(jcKs).equalTo(row::getJcKs)
                .set(jcJs).equalTo(row::getJcJs)
                .set(weekday).equalTo(row::getWeekday)
                .set(sfyxzx).equalTo(row::getSfyxzx)
                .set(jyytms).equalTo(row::getJyytms)
                .set(kcm).equalTo(row::getKcm);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Kcb row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jxlmc).equalToWhenPresent(row::getJxlmc)
                .set(jsmph).equalToWhenPresent(row::getJsmph)
                .set(jasdm).equalToWhenPresent(row::getJasdm)
                .set(skzws).equalToWhenPresent(row::getSkzws)
                .set(zylxdm).equalToWhenPresent(row::getZylxdm)
                .set(jcKs).equalToWhenPresent(row::getJcKs)
                .set(jcJs).equalToWhenPresent(row::getJcJs)
                .set(weekday).equalToWhenPresent(row::getWeekday)
                .set(sfyxzx).equalToWhenPresent(row::getSfyxzx)
                .set(jyytms).equalToWhenPresent(row::getJyytms)
                .set(kcm).equalToWhenPresent(row::getKcm);
    }

    default int updateByPrimaryKey(Kcb row) {
        return update(c ->
            c.set(jxlmc).equalTo(row::getJxlmc)
            .set(jsmph).equalTo(row::getJsmph)
            .set(jasdm).equalTo(row::getJasdm)
            .set(skzws).equalTo(row::getSkzws)
            .set(zylxdm).equalTo(row::getZylxdm)
            .set(jcKs).equalTo(row::getJcKs)
            .set(jcJs).equalTo(row::getJcJs)
            .set(weekday).equalTo(row::getWeekday)
            .set(sfyxzx).equalTo(row::getSfyxzx)
            .set(jyytms).equalTo(row::getJyytms)
            .set(kcm).equalTo(row::getKcm)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(Kcb row) {
        return update(c ->
            c.set(jxlmc).equalToWhenPresent(row::getJxlmc)
            .set(jsmph).equalToWhenPresent(row::getJsmph)
            .set(jasdm).equalToWhenPresent(row::getJasdm)
            .set(skzws).equalToWhenPresent(row::getSkzws)
            .set(zylxdm).equalToWhenPresent(row::getZylxdm)
            .set(jcKs).equalToWhenPresent(row::getJcKs)
            .set(jcJs).equalToWhenPresent(row::getJcJs)
            .set(weekday).equalToWhenPresent(row::getWeekday)
            .set(sfyxzx).equalToWhenPresent(row::getSfyxzx)
            .set(jyytms).equalToWhenPresent(row::getJyytms)
            .set(kcm).equalToWhenPresent(row::getKcm)
            .where(id, isEqualTo(row::getId))
        );
    }
}