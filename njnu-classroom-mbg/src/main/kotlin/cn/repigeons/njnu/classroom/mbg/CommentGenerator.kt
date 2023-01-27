package cn.repigeons.njnu.classroom.mbg

import org.mybatis.generator.api.CommentGenerator
import org.mybatis.generator.api.IntrospectedColumn
import org.mybatis.generator.api.IntrospectedTable
import org.mybatis.generator.api.dom.java.Field
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType
import org.mybatis.generator.api.dom.java.JavaElement
import org.mybatis.generator.api.dom.java.TopLevelClass
import org.mybatis.generator.config.MergeConstants
import org.mybatis.generator.config.PropertyRegistry
import org.mybatis.generator.internal.util.StringUtility
import java.util.*

class CommentGenerator : CommentGenerator {
    private val lineSeparator = System.getProperty("line.separator")
    private val properties = Properties()
    private var suppressAllComments = false
    private var addRemarkComments = false
    override fun addConfigurationProperties(props: Properties) {
        properties.putAll(props)
        suppressAllComments =
            StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS))
        addRemarkComments =
            StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS))
    }

    /**
     * 给模型类添加注释
     */
    override fun addModelClassComment(
        topLevelClass: TopLevelClass,
        introspectedTable: IntrospectedTable
    ) {
        if (suppressAllComments) return
        var remarks = introspectedTable.remarks
        if (addRemarkComments) {
            //数据库中特殊字符需要转义
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'")
            }
            addFieldJavaDoc(topLevelClass, remarks)
        }
    }

    /**
     * 给字段添加注释
     */
    override fun addFieldAnnotation(
        field: Field,
        introspectedTable: IntrospectedTable,
        introspectedColumn: IntrospectedColumn,
        imports: Set<FullyQualifiedJavaType>
    ) {
        if (suppressAllComments) return
        var remarks = introspectedColumn.remarks
        //根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments) {
            //数据库中特殊字符需要转义
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'")
            }
            addFieldJavaDoc(field, remarks)
        }
    }

    /**
     * 添加注释
     */
    private fun addFieldJavaDoc(field: JavaElement, remarks: String) {
        //文档注释开始
        field.addJavaDocLine("/**")
        //获取数据库字段的备注信息
        val remarkLines = remarks.split(lineSeparator)
            .dropLastWhile { it.isBlank() }
        var hasLine = false
        remarkLines.forEach { remarkLine ->
            val remark = remarkLine.trim()
            if (remark.isNotEmpty()) {
                field.addJavaDocLine(" * $remarkLine")
                hasLine = true
            }
        }
        if (!hasLine) {
            // field.addJavaDocLine(" *")
            field.addJavaDocLine(" * " + MergeConstants.NEW_ELEMENT_TAG)
        }
        field.addJavaDocLine(" */")
    }
}