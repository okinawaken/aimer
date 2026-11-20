package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.MemberPointBatchQuerySubscribedRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 批量查询微信订阅状态的单条结果（{@code POST /aliyupoint/batchQuerySubscribed}）。 响应可为数组或 {@code {list:[...]}}。
 */
@Data
@AutoMapper(target = MemberPointBatchQuerySubscribedRespDTO.class)
public class MemberPointBatchQuerySubscribedVO {

    /**
     * 订阅类型（如 {@code PROJECT_RESERVE}、{@code LOTTERY_RESERVE}、{@code BANNER_START}）
     */
    private String subscribeType;

    /**
     * 关联的跳转链接（与请求 items 中的 jumpUrl 对应，banner 用它回填状态）
     */
    private String jumpUrl;

    /**
     * 是否已订阅
     */
    private Boolean subscribed;
}
