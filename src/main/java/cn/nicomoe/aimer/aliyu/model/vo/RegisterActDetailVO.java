package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.RegisterActDetailRespDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

/**
 * 报名活动详情响应（{@code GET /act/registerActDetail}）。
 */
@Data
@AutoMapper(target = RegisterActDetailRespDTO.class)
public class RegisterActDetailVO {

    /**
     * 活动 ID
     */
    private String actId;

    /**
     * 活动名称（分享标题）
     */
    private String actName;

    /**
     * 活动状态： 1 即将开始、2 报名中、3 报名截止、4 等待抽签、5 抽签中、6 中签待下单、 7 未中签、8 超时、9 已下架、10 众测中。
     */
    private Long actStatus;

    /**
     * 活动状态描述文案
     */
    private String actStatusDesc;

    /**
     * 活动类型（活动列表按 {@code 2} 区分抽签页与报名页）
     */
    private Long actType;

    /**
     * 页面背景色
     */
    private String backgroundColor;

    /**
     * 报名开始时间（毫秒时间戳）
     */
    private String beginTime;

    /**
     * 是否可订阅微信通知
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
     * 活动详情富文本（HTML 文本）
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
     * 报名结束时间（毫秒时间戳）
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
     * 邀请记录列表
     */
    private List<FissionDrawActVO.InvitationRecordVO> invitationRecords;

    /**
     * 邀请码上限
     */
    private Long inviteCodeLimit;

    /**
     * 头图地址
     */
    private String mainPicUrl;

    /**
     * 我的抽签号码列表
     */
    private List<FissionDrawActVO.MyCodeVO> myCodes;

    /**
     * 关联项目 ID（下单 / 前往预约时拼接项目详情路由）
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
     * 参与数量 / 报名号码数
     */
    private Long registerCodeCount;

    /**
     * 已报名参与人，兼容单个对象与数组。 元素包含 {@code bearerId/bearerName/identificationType/identification}。
     */
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<RegisterUserVO> registerUser;

    /**
     * 抽签规则列表
     */
    private List<RuleVO> ruleDTOList;

    /**
     * 活动规则页地址（详情页「活动规则」入口）
     */
    private String ruleUrl;

    /**
     * 分享图地址（分享卡片优先使用）
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
     * 微信订阅消息模板 ID（报名时传给 {@code wx.requestSubscribeMessage}）
     */
    private String templateId;

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
     * 场馆名称（详情页头部「门店：xxx」）
     */
    private String venueName;

    /**
     * 微信通知标记：数值 1 表示支持订阅通知
     */
    private Long wechatNotify;

    /**
     * 中签号码列表（详情页中签卡片直接按文本渲染）
     */
    private List<String> winCodes;

    /**
     * 是否已开启微信订阅通知
     */
    private Boolean wxSubscribeNotifyEnabled;

    /**
     * 抽签规则（{@code ruleDTOList} 元素），时间字段为毫秒时间戳字符串。
     */
    @Data
    public static class RuleVO {

        /**
         * 活动 ID
         */
        private String actId;

        /**
         * 数据状态位
         */
        private Long dataState;

        /**
         * 抽签规则类型
         */
        private Long drawRule;

        /**
         * 抽签时间（毫秒时间戳）
         */
        private String drawTime;

        /**
         * 兑换结束时间（毫秒时间戳）
         */
        private String exchangeEndTime;

        /**
         * 兑换开始时间（毫秒时间戳）
         */
        private String exchangeStartTime;

        /**
         * 主键 ID
         */
        private String id;

        /**
         * 激励值配置
         */
        private String inspireValue;

        /**
         * 租户合作方 ID
         */
        private String ptnrId;

        /**
         * 公布时间（毫秒时间戳）
         */
        private String publishTime;

        /**
         * 租户 ID
         */
        private String tenantId;
    }

    /**
     * 已报名参与人。
     */
    @Data
    public static class RegisterUserVO {

        /**
         * 持票人 ID
         */
        private String bearerId;

        /**
         * 持票人姓名
         */
        private String bearerName;

        /**
         * 证件类型
         */
        private String identificationType;

        /**
         * 证件号
         */
        private String identification;
    }
}
