package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuProjectClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.HotProjectListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemBuyRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ItemQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectCityListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectColumnInfoReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectColumnInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectEventRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectEventsByDateRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectHasSaleRemindReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectHotReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectPrivilegeCheckReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectPrivilegeCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectShowCalendarReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ProjectTypeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ReceiveBasicInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegionCountryRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.RegionProvinceRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.ShowCalendarRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/** 阿里鱼项目 / 演出域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuProjectServiceImpl implements AliyuProjectService {

    private final AliyuProjectClient projectClient;

    @Override
    public ProjectListRespDTO projects(ProjectListReqDTO query) {
        AliyuClientResult<ProjectListRespDTO> result = projectClient.projects(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ProjectListRespDTO customizableProjects(ProjectListReqDTO query) {
        AliyuClientResult<ProjectListRespDTO> result = projectClient.customizableProjects(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ProjectListRespDTO languageProjects(ProjectListReqDTO query) {
        AliyuClientResult<ProjectListRespDTO> result = projectClient.languageProjects(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ProjectDetailRespDTO project(String projectToken, Long reqType, String eventDate) {
        AliyuClientResult<ProjectDetailRespDTO> result = projectClient.project(projectToken, reqType, eventDate);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ProjectEventRespDTO event(String eventToken) {
        AliyuClientResult<ProjectEventRespDTO> result = projectClient.event(eventToken);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ProjectEventsByDateRespDTO eventsByDate(String date) {
        AliyuClientResult<ProjectEventsByDateRespDTO> result = projectClient.eventsByDate(date);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<ShowCalendarRespDTO> getShowCalendar(ProjectShowCalendarReqDTO query) {
        AliyuClientResult<List<ShowCalendarRespDTO>> result = projectClient.getShowCalendar(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ProjectColumnInfoRespDTO getColumnInfo(ProjectColumnInfoReqDTO query) {
        AliyuClientResult<ProjectColumnInfoRespDTO> result = projectClient.getColumnInfo(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public HotProjectListRespDTO hotProjectVos(ProjectHotReqDTO query) {
        AliyuClientResult<HotProjectListRespDTO> result = projectClient.hotProjectVos(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ProjectPrivilegeCheckRespDTO privilegeCheck(ProjectPrivilegeCheckReqDTO query) {
        AliyuClientResult<ProjectPrivilegeCheckRespDTO> result = projectClient.privilegeCheck(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean hasSaleRemind(ProjectHasSaleRemindReqDTO form) {
        AliyuClientResult<Boolean> result = projectClient.hasSaleRemind(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<ProjectTypeListRespDTO> getProjectTypeList() {
        AliyuClientResult<List<ProjectTypeListRespDTO>> result = projectClient.getProjectTypeList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<ProjectCityListRespDTO> getProjectCityList() {
        AliyuClientResult<List<ProjectCityListRespDTO>> result = projectClient.getProjectCityList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<ProjectCityListRespDTO> getAreaCityList() {
        AliyuClientResult<List<ProjectCityListRespDTO>> result = projectClient.getAreaCityList();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public RegionCountryRespDTO getRegionByCountryId(String countryId, String tenantId, String ptnrId) {
        AliyuClientResult<RegionCountryRespDTO> result = projectClient.getRegionByCountryId(countryId, tenantId, ptnrId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public RegionProvinceRespDTO getRegionByProvinceId(String parentId, String countryId) {
        AliyuClientResult<RegionProvinceRespDTO> result = projectClient.getRegionByProvinceId(parentId, countryId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ReceiveBasicInfoRespDTO loadReceiveBasicInfo() {
        AliyuClientResult<ReceiveBasicInfoRespDTO> result = projectClient.loadReceiveBasicInfo();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ItemDetailRespDTO itemDetail(ItemQueryReqDTO query) {
        AliyuClientResult<ItemDetailRespDTO> result = projectClient.itemDetail(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public ItemBuyRespDTO itemBuy(ItemQueryReqDTO query) {
        AliyuClientResult<ItemBuyRespDTO> result = projectClient.itemBuy(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
