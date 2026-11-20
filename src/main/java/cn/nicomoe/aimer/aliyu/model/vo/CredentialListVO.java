package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.CredentialListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 凭证字典列表元素（{@code GET /credential/getList}）。
 */
@Data
@AutoMapper(target = CredentialListRespDTO.class)
public class CredentialListVO {

    /**
     * 是否允许删除
     */
    private Long allowDelete;

    /**
     * 是否可操作
     */
    private Boolean canOperate;

    /**
     * 子项
     */
    private List<CredentialListVO> children;

    /**
     * 启用状态
     */
    private Long enableState;

    /**
     * 创建时间（时间字段，保持 String）
     */
    private String gmtCreate;

    /**
     * 修改时间（时间字段，保持 String）
     */
    private String gmtModified;

    /**
     * 扩展值 1：选择器的选项值（证件类型编码）
     */
    private String itemExt1;

    /**
     * 扩展值 2
     */
    private String itemExt2;

    /**
     * 扩展值 3
     */
    private String itemExt3;

    /**
     * 字典项 ID
     */
    private String itemId;

    /**
     * 字典项文案：选择器的选项标签
     */
    private String itemValue;

    /**
     * 是否下线
     */
    private Boolean offLine;

    /**
     * 父级字典项 ID
     */
    private String parentItemId;

    /**
     * 系统字典 key
     */
    private String sysDictionaryKey;
}
