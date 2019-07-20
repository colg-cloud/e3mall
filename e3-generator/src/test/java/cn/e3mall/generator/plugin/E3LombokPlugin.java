package cn.e3mall.generator.plugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import cn.hutool.core.util.StrUtil;
import tk.mybatis.mapper.generator.FalseMethodPlugin;
import tk.mybatis.mapper.generator.MapperCommentGenerator;

/**
 * E3LombokPlugin: 自定义代码生成规则
 *
 * @author colg
 */
public class E3LombokPlugin extends FalseMethodPlugin {
    private Set<String> mappers = new HashSet<>();
    private boolean caseSensitive = false;
    private boolean useMapperCommentGenerator = true;
    /** 开始的分隔符，例如mysql为`，sqlServer为[ */
    private String beginningDelimiter = "";
    /** 结束的分隔符，例如mysql为`，sqlServer为] */
    private String endingDelimiter = "";
    /** 数据库模式 */
    private String schema;
    /** 注释生成器 */
    private CommentGeneratorConfiguration commentCfg;
    /** 强制生成注解 */
    private boolean forceAnnotation;

    public String getDelimiterName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        if (StringUtility.stringHasValue(schema)) {
            nameBuilder.append(schema);
            nameBuilder.append(".");
        }
        nameBuilder.append(beginningDelimiter);
        nameBuilder.append(name);
        nameBuilder.append(endingDelimiter);
        return nameBuilder.toString();
    }

    /**
     * 生成Mapper接口
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // doc
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * - @mbg.generated");
        interfaze.addJavaDocLine(" *");
        interfaze.addJavaDocLine(" * @author colg");
        interfaze.addJavaDocLine(" */");

        // 获取实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        // import接口
        mappers.forEach(mapper -> interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">")));
        // import实体类
        interfaze.addImportedType(entityType);
        return true;
    }

    /**
     * 处理实体类的包和@Table注解
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // doc
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * - @mbg.generated");
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author colg");
        topLevelClass.addJavaDocLine(" */");

        // 引入JPA注解
        topLevelClass.addImportedType("javax.persistence.*");

        topLevelClass.addImportedType("lombok.*");
        topLevelClass.addImportedType("lombok.experimental.Accessors");

        // 类注解
        topLevelClass.addAnnotation("@NoArgsConstructor");
        topLevelClass.addAnnotation("@AllArgsConstructor");
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@Accessors(chain = true)");

        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        // 如果包含空格, 或者需要分隔符, 需要完善
        if (StrUtil.containsBlank(tableName)) {
            tableName = context.getBeginningDelimiter() + tableName + context.getEndingDelimiter();
        }
        // 是否忽略大小写，对于区分大小写的数据库，会有用
        String shortName = topLevelClass.getType()
                                        .getShortName();
        if (caseSensitive && !tableName.equals(shortName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (!tableName.equalsIgnoreCase(shortName)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (!StrUtil.hasBlank(schema, beginningDelimiter, endingDelimiter)) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        } else if (forceAnnotation) {
            topLevelClass.addAnnotation("@Table(name = \"" + getDelimiterName(tableName) + "\")");
        }


    }

    /**
     * 生成基础实体类
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 生成实体类注解KEY对象
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 生成带BLOB字段的对象
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processEntityClass(topLevelClass, introspectedTable);
        return false;
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        // 设置默认的注释生成器
        useMapperCommentGenerator = !"FALSE".equalsIgnoreCase(context.getProperty("useMapperCommentGenerator"));
        if (useMapperCommentGenerator) {
            commentCfg = new CommentGeneratorConfiguration();
            commentCfg.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
            context.setCommentGeneratorConfiguration(commentCfg);
        }
        // 支持oracle获取注释#114
        context.getJdbcConnectionConfiguration()
               .addProperty("remarksReporting", "true");
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String mappers = this.properties.getProperty("mappers");
        if (StringUtility.stringHasValue(mappers)) {
            Collections.addAll(this.mappers, mappers.split(","));
        } else {
            throw new RuntimeException("Mapper插件缺少必要的mappers属性!");
        }
        String caseSensitive = this.properties.getProperty("caseSensitive");
        if (StringUtility.stringHasValue(caseSensitive)) {
            this.caseSensitive = caseSensitive.equalsIgnoreCase("TRUE");
        }
        String forceAnnotation = this.properties.getProperty("forceAnnotation");
        if (StringUtility.stringHasValue(forceAnnotation)) {
            if (useMapperCommentGenerator) {
                commentCfg.addProperty("forceAnnotation", forceAnnotation);
            }
            this.forceAnnotation = forceAnnotation.equalsIgnoreCase("TRUE");
        }
        String beginningDelimiter = this.properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(beginningDelimiter)) {
            this.beginningDelimiter = beginningDelimiter;
        }
        String endingDelimiter = this.properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(endingDelimiter)) {
            this.endingDelimiter = endingDelimiter;
        }
        String schema = this.properties.getProperty("schema");
        if (StringUtility.stringHasValue(schema)) {
            this.schema = schema;
        }
        if (useMapperCommentGenerator) {
            commentCfg.addProperty("beginningDelimiter", this.beginningDelimiter);
            commentCfg.addProperty("endingDelimiter", this.endingDelimiter);
        }
    }

}