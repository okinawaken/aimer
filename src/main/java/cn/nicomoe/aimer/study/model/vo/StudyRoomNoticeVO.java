package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomNoticeClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 通知公告（/mp/notice/board/info）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomNoticeClientRespDTO.class)
public class StudyRoomNoticeVO {

    /**
     * 是否展示：1 展示 0 不展示
     */
    private Integer showFlag;

    /**
     * 标题
     */
    private String title;

    /**
     * 公告内容（支持富文本）
     */
    private String noticeMsg;

    /**
     * 确认按钮文案
     */
    private String buttonText;

    /**
     * 门店名称
     */
    private String venueName;
}
