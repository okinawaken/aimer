package org.dromara.aimer.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.dromara.aimer.repository.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作日志记录
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_oper_log")
public class SysOperLogEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @TableId("oper_id")
    private Long operId;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private String tenantId;

    /**
     * 模块标题
     */
    @TableField("title")
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    @TableField("business_type")
    private Integer businessType;

    /**
     * 方法名称
     */
    @TableField("method")
    private String method;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 操作类别（0其它 1后台用户 2手机端用户）
     */
    @TableField("operator_type")
    private Integer operatorType;

    /**
     * 操作人员
     */
    @TableField("oper_name")
    private String operName;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 请求URL
     */
    @TableField("oper_url")
    private String operUrl;

    /**
     * 主机地址
     */
    @TableField("oper_ip")
    private String operIp;

    /**
     * 操作地点
     */
    @TableField("oper_location")
    private String operLocation;

    /**
     * 请求参数
     */
    @TableField("oper_param")
    private String operParam;

    /**
     * 返回参数
     */
    @TableField("json_result")
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    @TableField("status")
    private Integer status;

    /**
     * 错误消息
     */
    @TableField("error_msg")
    private String errorMsg;

    /**
     * 操作时间
     */
    @TableField("oper_time")
    private LocalDateTime operTime;

    /**
     * 消耗时间
     */
    @TableField("cost_time")
    private Long costTime;
}
