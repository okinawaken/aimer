package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 提交报修（/mp/userRepair/submit）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomRepairSubmitClientReqDTO {

    /**
     * 门店ID（必填）
     */
    private Integer venueId;

    /**
     * 问题描述（必填）
     */
    private String repairDesc;

    /**
     * 图片地址列表（先调 /mp/image/upload 上传）
     */
    private List<String> repairImgList;
}
