package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 排行榜用户
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomRankUserClientRespDTO {

    /**
     * 名次
     */
    private Integer rankNum;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 学习总时长
     */
    private String studyTotalTimeStr;

    /**
     * 学习天数
     */
    private Integer studyDays;
}
