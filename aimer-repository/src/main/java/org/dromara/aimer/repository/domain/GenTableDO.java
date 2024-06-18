package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 代码生成业务表
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("gen_table")
public class GenTableDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId("table_id")
    private Long tableId;

    /**
     * 数据源名称
     */
    @TableField("data_name")
    private String dataName;

    /**
     * 表名称
     */
    @TableField("table_name")
    private String tableName;

    /**
     * 表描述
     */
    @TableField("table_comment")
    private String tableComment;

    /**
     * 关联子表的表名
     */
    @TableField("sub_table_name")
    private String subTableName;

    /**
     * 子表关联的外键名
     */
    @TableField("sub_table_fk_name")
    private String subTableFkName;

    /**
     * 实体类名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 使用的模板（crud单表操作 tree树表操作）
     */
    @TableField("tpl_category")
    private String tplCategory;

    /**
     * 生成包路径
     */
    @TableField("package_name")
    private String packageName;

    /**
     * 生成模块名
     */
    @TableField("module_name")
    private String moduleName;

    /**
     * 生成业务名
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 生成功能名
     */
    @TableField("function_name")
    private String functionName;

    /**
     * 生成功能作者
     */
    @TableField("function_author")
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    @TableField("gen_type")
    private String genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    @TableField("gen_path")
    private String genPath;

    /**
     * 其它生成选项
     */
    @TableField("options")
    private String options;

    /**
     * 创建部门
     */
    @TableField("create_dept")
    private Long createDept;

    /**
     * 创建者
     */
    @TableField("create_by")
    private Long createBy;

    /**
     * 更新者
     */
    @TableField("update_by")
    private Long updateBy;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
