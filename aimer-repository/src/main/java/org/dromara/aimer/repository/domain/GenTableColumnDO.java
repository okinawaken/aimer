package org.dromara.aimer.repository.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import org.dromara.aimer.repository.base.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 代码生成业务表字段
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("gen_table_column")
public class GenTableColumnDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId("column_id")
    private Long columnId;

    /**
     * 归属表编号
     */
    @TableField("table_id")
    private Long tableId;

    /**
     * 列名称
     */
    @TableField("column_name")
    private String columnName;

    /**
     * 列描述
     */
    @TableField("column_comment")
    private String columnComment;

    /**
     * 列类型
     */
    @TableField("column_type")
    private String columnType;

    /**
     * JAVA类型
     */
    @TableField("java_type")
    private String javaType;

    /**
     * JAVA字段名
     */
    @TableField("java_field")
    private String javaField;

    /**
     * 是否主键（1是）
     */
    @TableField("is_pk")
    private String isPk;

    /**
     * 是否自增（1是）
     */
    @TableField("is_increment")
    private String isIncrement;

    /**
     * 是否必填（1是）
     */
    @TableField("is_required")
    private String isRequired;

    /**
     * 是否为插入字段（1是）
     */
    @TableField("is_insert")
    private String isInsert;

    /**
     * 是否编辑字段（1是）
     */
    @TableField("is_edit")
    private String isEdit;

    /**
     * 是否列表字段（1是）
     */
    @TableField("is_list")
    private String isList;

    /**
     * 是否查询字段（1是）
     */
    @TableField("is_query")
    private String isQuery;

    /**
     * 查询方式（等于、不等于、大于、小于、范围）
     */
    @TableField("query_type")
    private String queryType;

    /**
     * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
     */
    @TableField("html_type")
    private String htmlType;

    /**
     * 字典类型
     */
    @TableField("dict_type")
    private String dictType;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

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
}
