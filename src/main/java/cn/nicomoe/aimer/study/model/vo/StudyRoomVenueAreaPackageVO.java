package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueAreaPackageClientRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 按区域分组的套餐（/mp/buy/package/list）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
@AutoMapper(target = StudyRoomVenueAreaPackageClientRespDTO.class)
public class StudyRoomVenueAreaPackageVO {

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域图片
     */
    private String areaImgUrl;

    /**
     * 套餐列表
     */
    private List<StudyRoomPackageItemVO> packageList;
}
