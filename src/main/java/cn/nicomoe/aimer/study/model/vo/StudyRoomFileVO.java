package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomFileClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 图片信息（门店图、banner、头像等）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomFileClientRespDTO.class)
public class StudyRoomFileVO {

    /**
     * 文件 key
     */
    private String fileKey;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 可直接访问的 URL
     */
    private String fileUrl;

    /**
     * 跳转类型：0 不跳转 1 跳 H5
     */
    private Integer linkHtmlType;

    /**
     * 跳转地址
     */
    private String linkHtmlUrl;
}
