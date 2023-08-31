package cn.repigeons.njnu.classroom.mbg.mapper;

import cn.repigeons.njnu.classroom.mbg.model.Jas;
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

import static cn.repigeons.njnu.classroom.mbg.mapper.JasDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface JasMapper extends CommonCountMapper, CommonDeleteMapper, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(jasdm, jasmc, jxldm, jxldmDisplay, xxxqdm, xxxqdmDisplay, jaslxdm, jaslxdmDisplay, zt, lc, skzws, kszws, xnxqdm, xnxqdm2, dwdm, dwdmDisplay, zwsxdm, syrq, sysj, sxlb, sfypk, sfyxpk, pkyxj, sfkswh, sfyxks, ksyxj, sfyxcx, sfyxjy, sfyxzx, jsyt, xgdd, bz);

    static UpdateDSL<UpdateModel> updateAllColumns(Jas row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jasmc).equalTo(row::getJasmc)
                .set(jxldm).equalTo(row::getJxldm)
                .set(jxldmDisplay).equalTo(row::getJxldmDisplay)
                .set(xxxqdm).equalTo(row::getXxxqdm)
                .set(xxxqdmDisplay).equalTo(row::getXxxqdmDisplay)
                .set(jaslxdm).equalTo(row::getJaslxdm)
                .set(jaslxdmDisplay).equalTo(row::getJaslxdmDisplay)
                .set(zt).equalTo(row::getZt)
                .set(lc).equalTo(row::getLc)
                .set(skzws).equalTo(row::getSkzws)
                .set(kszws).equalTo(row::getKszws)
                .set(xnxqdm).equalTo(row::getXnxqdm)
                .set(xnxqdm2).equalTo(row::getXnxqdm2)
                .set(dwdm).equalTo(row::getDwdm)
                .set(dwdmDisplay).equalTo(row::getDwdmDisplay)
                .set(zwsxdm).equalTo(row::getZwsxdm)
                .set(syrq).equalTo(row::getSyrq)
                .set(sysj).equalTo(row::getSysj)
                .set(sxlb).equalTo(row::getSxlb)
                .set(sfypk).equalTo(row::getSfypk)
                .set(sfyxpk).equalTo(row::getSfyxpk)
                .set(pkyxj).equalTo(row::getPkyxj)
                .set(sfkswh).equalTo(row::getSfkswh)
                .set(sfyxks).equalTo(row::getSfyxks)
                .set(ksyxj).equalTo(row::getKsyxj)
                .set(sfyxcx).equalTo(row::getSfyxcx)
                .set(sfyxjy).equalTo(row::getSfyxjy)
                .set(sfyxzx).equalTo(row::getSfyxzx)
                .set(jsyt).equalTo(row::getJsyt)
                .set(xgdd).equalTo(row::getXgdd)
                .set(bz).equalTo(row::getBz);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(Jas row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jasmc).equalToWhenPresent(row::getJasmc)
                .set(jxldm).equalToWhenPresent(row::getJxldm)
                .set(jxldmDisplay).equalToWhenPresent(row::getJxldmDisplay)
                .set(xxxqdm).equalToWhenPresent(row::getXxxqdm)
                .set(xxxqdmDisplay).equalToWhenPresent(row::getXxxqdmDisplay)
                .set(jaslxdm).equalToWhenPresent(row::getJaslxdm)
                .set(jaslxdmDisplay).equalToWhenPresent(row::getJaslxdmDisplay)
                .set(zt).equalToWhenPresent(row::getZt)
                .set(lc).equalToWhenPresent(row::getLc)
                .set(skzws).equalToWhenPresent(row::getSkzws)
                .set(kszws).equalToWhenPresent(row::getKszws)
                .set(xnxqdm).equalToWhenPresent(row::getXnxqdm)
                .set(xnxqdm2).equalToWhenPresent(row::getXnxqdm2)
                .set(dwdm).equalToWhenPresent(row::getDwdm)
                .set(dwdmDisplay).equalToWhenPresent(row::getDwdmDisplay)
                .set(zwsxdm).equalToWhenPresent(row::getZwsxdm)
                .set(syrq).equalToWhenPresent(row::getSyrq)
                .set(sysj).equalToWhenPresent(row::getSysj)
                .set(sxlb).equalToWhenPresent(row::getSxlb)
                .set(sfypk).equalToWhenPresent(row::getSfypk)
                .set(sfyxpk).equalToWhenPresent(row::getSfyxpk)
                .set(pkyxj).equalToWhenPresent(row::getPkyxj)
                .set(sfkswh).equalToWhenPresent(row::getSfkswh)
                .set(sfyxks).equalToWhenPresent(row::getSfyxks)
                .set(ksyxj).equalToWhenPresent(row::getKsyxj)
                .set(sfyxcx).equalToWhenPresent(row::getSfyxcx)
                .set(sfyxjy).equalToWhenPresent(row::getSfyxjy)
                .set(sfyxzx).equalToWhenPresent(row::getSfyxzx)
                .set(jsyt).equalToWhenPresent(row::getJsyt)
                .set(xgdd).equalToWhenPresent(row::getXgdd)
                .set(bz).equalToWhenPresent(row::getBz);
    }

    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "row.jasdm", before = false, resultType = String.class)
    int insert(InsertStatementProvider<Jas> insertStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "JasResult", value = {
            @Result(column = "jasdm", property = "jasdm", jdbcType = JdbcType.CHAR, id = true),
            @Result(column = "jasmc", property = "jasmc", jdbcType = JdbcType.VARCHAR),
            @Result(column = "jxldm", property = "jxldm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "jxldm_display", property = "jxldmDisplay", jdbcType = JdbcType.VARCHAR),
            @Result(column = "xxxqdm", property = "xxxqdm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "xxxqdm_display", property = "xxxqdmDisplay", jdbcType = JdbcType.VARCHAR),
            @Result(column = "jaslxdm", property = "jaslxdm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "jaslxdm_display", property = "jaslxdmDisplay", jdbcType = JdbcType.VARCHAR),
            @Result(column = "zt", property = "zt", jdbcType = JdbcType.VARCHAR),
            @Result(column = "lc", property = "lc", jdbcType = JdbcType.SMALLINT),
            @Result(column = "skzws", property = "skzws", jdbcType = JdbcType.INTEGER),
            @Result(column = "kszws", property = "kszws", jdbcType = JdbcType.INTEGER),
            @Result(column = "xnxqdm", property = "xnxqdm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "xnxqdm2", property = "xnxqdm2", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dwdm", property = "dwdm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dwdm_display", property = "dwdmDisplay", jdbcType = JdbcType.VARCHAR),
            @Result(column = "zwsxdm", property = "zwsxdm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "syrq", property = "syrq", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sysj", property = "sysj", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sxlb", property = "sxlb", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sfypk", property = "sfypk", jdbcType = JdbcType.BIT),
            @Result(column = "sfyxpk", property = "sfyxpk", jdbcType = JdbcType.BIT),
            @Result(column = "pkyxj", property = "pkyxj", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sfkswh", property = "sfkswh", jdbcType = JdbcType.BIT),
            @Result(column = "sfyxks", property = "sfyxks", jdbcType = JdbcType.BIT),
            @Result(column = "ksyxj", property = "ksyxj", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sfyxcx", property = "sfyxcx", jdbcType = JdbcType.BIT),
            @Result(column = "sfyxjy", property = "sfyxjy", jdbcType = JdbcType.BIT),
            @Result(column = "sfyxzx", property = "sfyxzx", jdbcType = JdbcType.BIT),
            @Result(column = "jsyt", property = "jsyt", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "xgdd", property = "xgdd", jdbcType = JdbcType.LONGVARCHAR),
            @Result(column = "bz", property = "bz", jdbcType = JdbcType.LONGVARCHAR)
    })
    List<Jas> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("JasResult")
    Optional<Jas> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, jas, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, jas, completer);
    }

    default int deleteByPrimaryKey(String jasdm_) {
        return delete(c ->
                c.where(jasdm, isEqualTo(jasdm_))
        );
    }

    default int insert(Jas row) {
        return MyBatis3Utils.insert(this::insert, row, jas, c ->
                c.map(jasmc).toProperty("jasmc")
                        .map(jxldm).toProperty("jxldm")
                        .map(jxldmDisplay).toProperty("jxldmDisplay")
                        .map(xxxqdm).toProperty("xxxqdm")
                        .map(xxxqdmDisplay).toProperty("xxxqdmDisplay")
                        .map(jaslxdm).toProperty("jaslxdm")
                        .map(jaslxdmDisplay).toProperty("jaslxdmDisplay")
                        .map(zt).toProperty("zt")
                        .map(lc).toProperty("lc")
                        .map(skzws).toProperty("skzws")
                        .map(kszws).toProperty("kszws")
                        .map(xnxqdm).toProperty("xnxqdm")
                        .map(xnxqdm2).toProperty("xnxqdm2")
                        .map(dwdm).toProperty("dwdm")
                        .map(dwdmDisplay).toProperty("dwdmDisplay")
                        .map(zwsxdm).toProperty("zwsxdm")
                        .map(syrq).toProperty("syrq")
                        .map(sysj).toProperty("sysj")
                        .map(sxlb).toProperty("sxlb")
                        .map(sfypk).toProperty("sfypk")
                        .map(sfyxpk).toProperty("sfyxpk")
                        .map(pkyxj).toProperty("pkyxj")
                        .map(sfkswh).toProperty("sfkswh")
                        .map(sfyxks).toProperty("sfyxks")
                        .map(ksyxj).toProperty("ksyxj")
                        .map(sfyxcx).toProperty("sfyxcx")
                        .map(sfyxjy).toProperty("sfyxjy")
                        .map(sfyxzx).toProperty("sfyxzx")
                        .map(jsyt).toProperty("jsyt")
                        .map(xgdd).toProperty("xgdd")
                        .map(bz).toProperty("bz")
        );
    }

    default int insertSelective(Jas row) {
        return MyBatis3Utils.insert(this::insert, row, jas, c ->
                c.map(jasmc).toPropertyWhenPresent("jasmc", row::getJasmc)
                        .map(jxldm).toPropertyWhenPresent("jxldm", row::getJxldm)
                        .map(jxldmDisplay).toPropertyWhenPresent("jxldmDisplay", row::getJxldmDisplay)
                        .map(xxxqdm).toPropertyWhenPresent("xxxqdm", row::getXxxqdm)
                        .map(xxxqdmDisplay).toPropertyWhenPresent("xxxqdmDisplay", row::getXxxqdmDisplay)
                        .map(jaslxdm).toPropertyWhenPresent("jaslxdm", row::getJaslxdm)
                        .map(jaslxdmDisplay).toPropertyWhenPresent("jaslxdmDisplay", row::getJaslxdmDisplay)
                        .map(zt).toPropertyWhenPresent("zt", row::getZt)
                        .map(lc).toPropertyWhenPresent("lc", row::getLc)
                        .map(skzws).toPropertyWhenPresent("skzws", row::getSkzws)
                        .map(kszws).toPropertyWhenPresent("kszws", row::getKszws)
                        .map(xnxqdm).toPropertyWhenPresent("xnxqdm", row::getXnxqdm)
                        .map(xnxqdm2).toPropertyWhenPresent("xnxqdm2", row::getXnxqdm2)
                        .map(dwdm).toPropertyWhenPresent("dwdm", row::getDwdm)
                        .map(dwdmDisplay).toPropertyWhenPresent("dwdmDisplay", row::getDwdmDisplay)
                        .map(zwsxdm).toPropertyWhenPresent("zwsxdm", row::getZwsxdm)
                        .map(syrq).toPropertyWhenPresent("syrq", row::getSyrq)
                        .map(sysj).toPropertyWhenPresent("sysj", row::getSysj)
                        .map(sxlb).toPropertyWhenPresent("sxlb", row::getSxlb)
                        .map(sfypk).toPropertyWhenPresent("sfypk", row::getSfypk)
                        .map(sfyxpk).toPropertyWhenPresent("sfyxpk", row::getSfyxpk)
                        .map(pkyxj).toPropertyWhenPresent("pkyxj", row::getPkyxj)
                        .map(sfkswh).toPropertyWhenPresent("sfkswh", row::getSfkswh)
                        .map(sfyxks).toPropertyWhenPresent("sfyxks", row::getSfyxks)
                        .map(ksyxj).toPropertyWhenPresent("ksyxj", row::getKsyxj)
                        .map(sfyxcx).toPropertyWhenPresent("sfyxcx", row::getSfyxcx)
                        .map(sfyxjy).toPropertyWhenPresent("sfyxjy", row::getSfyxjy)
                        .map(sfyxzx).toPropertyWhenPresent("sfyxzx", row::getSfyxzx)
                        .map(jsyt).toPropertyWhenPresent("jsyt", row::getJsyt)
                        .map(xgdd).toPropertyWhenPresent("xgdd", row::getXgdd)
                        .map(bz).toPropertyWhenPresent("bz", row::getBz)
        );
    }

    default Optional<Jas> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, jas, completer);
    }

    default List<Jas> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, jas, completer);
    }

    default List<Jas> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, jas, completer);
    }

    default Optional<Jas> selectByPrimaryKey(String jasdm_) {
        return selectOne(c ->
                c.where(jasdm, isEqualTo(jasdm_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, jas, completer);
    }

    default int updateByPrimaryKey(Jas row) {
        return update(c ->
                c.set(jasmc).equalTo(row::getJasmc)
                        .set(jxldm).equalTo(row::getJxldm)
                        .set(jxldmDisplay).equalTo(row::getJxldmDisplay)
                        .set(xxxqdm).equalTo(row::getXxxqdm)
                        .set(xxxqdmDisplay).equalTo(row::getXxxqdmDisplay)
                        .set(jaslxdm).equalTo(row::getJaslxdm)
                        .set(jaslxdmDisplay).equalTo(row::getJaslxdmDisplay)
                        .set(zt).equalTo(row::getZt)
                        .set(lc).equalTo(row::getLc)
                        .set(skzws).equalTo(row::getSkzws)
                        .set(kszws).equalTo(row::getKszws)
                        .set(xnxqdm).equalTo(row::getXnxqdm)
                        .set(xnxqdm2).equalTo(row::getXnxqdm2)
                        .set(dwdm).equalTo(row::getDwdm)
                        .set(dwdmDisplay).equalTo(row::getDwdmDisplay)
                        .set(zwsxdm).equalTo(row::getZwsxdm)
                        .set(syrq).equalTo(row::getSyrq)
                        .set(sysj).equalTo(row::getSysj)
                        .set(sxlb).equalTo(row::getSxlb)
                        .set(sfypk).equalTo(row::getSfypk)
                        .set(sfyxpk).equalTo(row::getSfyxpk)
                        .set(pkyxj).equalTo(row::getPkyxj)
                        .set(sfkswh).equalTo(row::getSfkswh)
                        .set(sfyxks).equalTo(row::getSfyxks)
                        .set(ksyxj).equalTo(row::getKsyxj)
                        .set(sfyxcx).equalTo(row::getSfyxcx)
                        .set(sfyxjy).equalTo(row::getSfyxjy)
                        .set(sfyxzx).equalTo(row::getSfyxzx)
                        .set(jsyt).equalTo(row::getJsyt)
                        .set(xgdd).equalTo(row::getXgdd)
                        .set(bz).equalTo(row::getBz)
                        .where(jasdm, isEqualTo(row::getJasdm))
        );
    }

    default int updateByPrimaryKeySelective(Jas row) {
        return update(c ->
                c.set(jasmc).equalToWhenPresent(row::getJasmc)
                        .set(jxldm).equalToWhenPresent(row::getJxldm)
                        .set(jxldmDisplay).equalToWhenPresent(row::getJxldmDisplay)
                        .set(xxxqdm).equalToWhenPresent(row::getXxxqdm)
                        .set(xxxqdmDisplay).equalToWhenPresent(row::getXxxqdmDisplay)
                        .set(jaslxdm).equalToWhenPresent(row::getJaslxdm)
                        .set(jaslxdmDisplay).equalToWhenPresent(row::getJaslxdmDisplay)
                        .set(zt).equalToWhenPresent(row::getZt)
                        .set(lc).equalToWhenPresent(row::getLc)
                        .set(skzws).equalToWhenPresent(row::getSkzws)
                        .set(kszws).equalToWhenPresent(row::getKszws)
                        .set(xnxqdm).equalToWhenPresent(row::getXnxqdm)
                        .set(xnxqdm2).equalToWhenPresent(row::getXnxqdm2)
                        .set(dwdm).equalToWhenPresent(row::getDwdm)
                        .set(dwdmDisplay).equalToWhenPresent(row::getDwdmDisplay)
                        .set(zwsxdm).equalToWhenPresent(row::getZwsxdm)
                        .set(syrq).equalToWhenPresent(row::getSyrq)
                        .set(sysj).equalToWhenPresent(row::getSysj)
                        .set(sxlb).equalToWhenPresent(row::getSxlb)
                        .set(sfypk).equalToWhenPresent(row::getSfypk)
                        .set(sfyxpk).equalToWhenPresent(row::getSfyxpk)
                        .set(pkyxj).equalToWhenPresent(row::getPkyxj)
                        .set(sfkswh).equalToWhenPresent(row::getSfkswh)
                        .set(sfyxks).equalToWhenPresent(row::getSfyxks)
                        .set(ksyxj).equalToWhenPresent(row::getKsyxj)
                        .set(sfyxcx).equalToWhenPresent(row::getSfyxcx)
                        .set(sfyxjy).equalToWhenPresent(row::getSfyxjy)
                        .set(sfyxzx).equalToWhenPresent(row::getSfyxzx)
                        .set(jsyt).equalToWhenPresent(row::getJsyt)
                        .set(xgdd).equalToWhenPresent(row::getXgdd)
                        .set(bz).equalToWhenPresent(row::getBz)
                        .where(jasdm, isEqualTo(row::getJasdm))
        );
    }
}