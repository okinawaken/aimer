package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveIndexClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 预约首页（/mp/reserve/index）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomReserveIndexClientRespDTO.class)
public class StudyRoomReserveIndexVO {

    /**
     * 区域筛选按钮是否展示（字符串 true/false）
     */
    private String areaIdBtn;

    /**
     * 渠道比例开关（字符串 true/false）
     */
    private String channelBiliBtn;

    /**
     * banner 图
     */
    private List<StudyRoomFileVO> bannerImgList;

    /**
     * 背景图
     */
    private List<StudyRoomFileVO> backImgList;

    /**
     * 门店搜索按钮是否展示（字符串 true/false）
     */
    private String venueNameBtn;

    /**
     * 门店列表
     */
    private List<StudyRoomVenueVO> venueList;
}
