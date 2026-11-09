package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueRoomClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 门店区域介绍
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomVenueRoomClientRespDTO.class)
public class StudyRoomVenueRoomVO {

    /**
     * 区域名称
     */
    private String title;

    /**
     * 区域图片
     */
    private String imagePath;

    /**
     * 区域须知
     */
    private List<StudyRoomAreaTipVO> areaTipsList;
}
