package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.RegisterActListRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 报名活动列表响应（{@code POST /act/registerActList}）。
 */
@Data
@AutoMapper(target = RegisterActListRespDTO.class)
public class RegisterActListVO {

    /**
     * 活动列表
     */
    private List<DataObjVO> dataList;

    /**
     * 结束行号
     */
    private Long endRow;

    /**
     * 是否还有下一页
     */
    private Boolean nextPage;

    /**
     * 当前页码
     */
    private Long page;

    /**
     * 每页数量
     */
    private Long pageSize;

    /**
     * 起始行号
     */
    private Long startRow;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总行数
     */
    private Long totalRow;

    /**
     * 活动列表卡片（活动列表页与报名详情页共用同一实体）。
     */
    @Data
    public static class DataObjVO {

        /**
         * 活动 ID
         */
        private String actId;

        /**
         * 活动名称
         */
        private String actName;

        /**
         * 活动类型（与 {@link #type} 一起判断跳报名页还是抽签页）
         */
        private Long actType;

        /**
         * 活动类型标记
         */
        private Long type;

        /**
         * 页面背景色（CSS 颜色值）
         */
        private String backgroundColor;

        /**
         * 报名开始时间（毫秒时间戳，列表页按数字格式化日期）
         */
        private String beginTime;

        /**
         * 是否可订阅微信通知（详情页按 {@code false ===} 比较）
         */
        private Boolean canSubscribe;

        /**
         * 活动封面图地址
         */
        private String coverUrl;

        /**
         * 数据状态位
         */
        private Long dataState;

        /**
         * 活动详情富文本（HTML）
         */
        private String description;

        /**
         * 抽签状态
         */
        private Long drawStatus;

        /**
         * 抽签时间（毫秒时间戳）
         */
        private String drawTime;

        /**
         * 报名结束时间（毫秒时间戳，列表页按数字格式化日期）
         */
        private String endTime;

        /**
         * 兑换结束时间（毫秒时间戳）
         */
        private String exchangeEndTime;

        /**
         * 兑换开始时间（毫秒时间戳）
         */
        private String exchangeStartTime;

        /**
         * 扩展字段
         */
        private String ext;

        /**
         * 已助力人数
         */
        private Long helpCount;

        /**
         * 主键 ID
         */
        private String id;

        /**
         * 邀请记录列表（结构同 {@link FissionDrawActVO.InvitationRecordVO}）
         */
        private List<FissionDrawActVO.InvitationRecordVO> invitationRecords;

        /**
         * 邀请码上限（抽签详情页缺省回落到 5）
         */
        private Long inviteCodeLimit;

        /**
         * 头图地址
         */
        private String mainPicUrl;

        /**
         * 我的抽签号码列表（结构同 {@link FissionDrawActVO.MyCodeVO}）
         */
        private List<FissionDrawActVO.MyCodeVO> myCodes;

        /**
         * 关联项目 ID
         */
        private String projectId;

        /**
         * 关联项目名称
         */
        private String projectName;

        /**
         * 租户合作方 ID
         */
        private String ptnrId;

        /**
         * 公布时间（毫秒时间戳）
         */
        private String publishTime;

        /**
         * 参与号码数（抽签详情页按数字使用）
         */
        private Long registerCodeCount;

        /**
         * 抽签规则列表（结构同 {@link RegisterActDetailVO.RuleVO}）
         */
        private List<RegisterActDetailVO.RuleVO> ruleDTOList;

        /**
         * 活动规则页地址
         */
        private String ruleUrl;

        /**
         * 分享图地址
         */
        private String shareImageUrl;

        /**
         * 报名截止时间（毫秒时间戳）
         */
        private String signUpEndTime;

        /**
         * 报名提示文案
         */
        private String signUpMessage;

        /**
         * 活动阶段
         */
        private Long stage;

        /**
         * 业务状态位
         */
        private Long status;

        /**
         * 租户 ID
         */
        private String tenantId;

        /**
         * 测试标记（0/1）
         */
        private Long testFlag;

        /**
         * 场馆 ID
         */
        private String venueId;

        /**
         * 场馆名称（列表卡片「门店：xxx」）
         */
        private String venueName;

        /**
         * 微信通知标记（详情页按 {@code 1 != wechatNotify} 比较，故为数值）
         */
        private Long wechatNotify;

        /**
         * 中签号码列表
         */
        private List<String> winCodes;

        /**
         * 是否已开启微信订阅通知（详情页按 {@code true ===} 比较）
         */
        private Boolean wxSubscribeNotifyEnabled;
    }
}
