package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuMemberPointClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuUserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DynamicQrCodeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.GrowthValueDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.GrowthValueTierRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberHomePageRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointBatchQuerySubscribedReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointBatchQuerySubscribedRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointBatchSubscribeReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointDeregisterUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointDeregisterUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointGrowthValueDetailReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointModifyUserReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointPointsDetailReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskCheckStatusReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskCheckStatusRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskClaimReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskClaimRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskReportReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberPointTaskReportRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsRuleRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.TaskListRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuMemberPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/** 阿里鱼会员 / 积分域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuMemberPointServiceImpl implements AliyuMemberPointService {

    private final AliyuMemberPointClient memberPointClient;

    @Override
    public AliyuUserInfoRespDTO userInfo() {
        AliyuClientResult<AliyuUserInfoRespDTO> result = memberPointClient.userInfo();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public MemberInfoRespDTO getMemberInfo() {
        AliyuClientResult<MemberInfoRespDTO> result = memberPointClient.getMemberInfo();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public MemberHomePageRespDTO getMemberHomePageInfo() {
        AliyuClientResult<MemberHomePageRespDTO> result = memberPointClient.getMemberHomePageInfo();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean checkMember() {
        AliyuClientResult<Boolean> result = memberPointClient.checkMember();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public PointsDetailRespDTO getPointsDetail(MemberPointPointsDetailReqDTO query) {
        AliyuClientResult<PointsDetailRespDTO> result = memberPointClient.getPointsDetail(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<PointsRuleRespDTO> getPointsRule(String cardType) {
        AliyuClientResult<List<PointsRuleRespDTO>> result = memberPointClient.getPointsRule(cardType);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public GrowthValueDetailRespDTO getGrowthValueDetail(MemberPointGrowthValueDetailReqDTO query) {
        AliyuClientResult<GrowthValueDetailRespDTO> result = memberPointClient.getGrowthValueDetail(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public GrowthValueTierRespDTO getGrowthValueTierInfo() {
        AliyuClientResult<GrowthValueTierRespDTO> result = memberPointClient.getGrowthValueTierInfo();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public DynamicQrCodeRespDTO getDynamicQrCode(String cardType) {
        AliyuClientResult<DynamicQrCodeRespDTO> result = memberPointClient.getDynamicQrCode(cardType);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Object modifyUser(MemberPointModifyUserReqDTO form) {
        AliyuClientResult<Object> result = memberPointClient.modifyUser(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public MemberPointDeregisterUserRespDTO deregisterUser(MemberPointDeregisterUserReqDTO form) {
        AliyuClientResult<MemberPointDeregisterUserRespDTO> result = memberPointClient.deregisterUser(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public Boolean batchSubscribe(MemberPointBatchSubscribeReqDTO form) {
        AliyuClientResult<Boolean> result = memberPointClient.batchSubscribe(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<MemberPointBatchQuerySubscribedRespDTO> batchQuerySubscribed(MemberPointBatchQuerySubscribedReqDTO form) {
        AliyuClientResult<List<MemberPointBatchQuerySubscribedRespDTO>> result = memberPointClient.batchQuerySubscribed(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public TaskListRespDTO taskList(MemberPointTaskListReqDTO form) {
        AliyuClientResult<TaskListRespDTO> result = memberPointClient.taskList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public MemberPointTaskCheckStatusRespDTO taskCheckStatus(MemberPointTaskCheckStatusReqDTO form) {
        AliyuClientResult<MemberPointTaskCheckStatusRespDTO> result = memberPointClient.taskCheckStatus(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public MemberPointTaskClaimRespDTO taskClaim(MemberPointTaskClaimReqDTO form) {
        return memberPointClient.taskClaim(form);
    }

    @Override
    public MemberPointTaskReportRespDTO taskReport(MemberPointTaskReportReqDTO form) {
        return memberPointClient.taskReport(form);
    }
}
