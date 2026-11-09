package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetGridPriceClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseCommitClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseCommitClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOrderListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetLeaseOverduePayClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetUserGridListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetVenueListClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomCabinetWifiOpenClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomConsumeResultClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVerifyClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReceiptConsumeClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomWxPayParamClientRespDTO;

/**
 * 自习室其他域服务：储物柜、团购券核销。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomMiscService {

    /**
     * 储物柜租赁订单。
     *
     * @return 订单列表数据
     */
    StudyRoomCabinetLeaseOrderListClientRespDTO cabinetLeaseOrderList();

    /**
     * 储物柜租赁支付。
     *
     * @param leaseId 租约 ID
     * @param userId  用户 ID
     * @return 支付参数
     */
    StudyRoomWxPayParamClientRespDTO cabinetLeasePay(Long leaseId, Long userId);

    /**
     * 储物柜下单。
     *
     * @param form 请求参数
     * @return 租约数据
     */
    StudyRoomCabinetLeaseCommitClientRespDTO cabinetLeaseCommit(StudyRoomCabinetLeaseCommitClientReqDTO form);

    /**
     * 储物柜逾期支付。
     *
     * @param leaseId  租约 ID
     * @param userCode 用户编码
     * @return 支付参数
     */
    StudyRoomCabinetLeaseOverduePayClientRespDTO cabinetLeaseOverduePay(Long leaseId, String userCode);

    /**
     * 储物柜自动退租。
     *
     * @param lastLeaseId 最近一次租约 ID
     */
    void cabinetLeaseAutoCheckout(Long lastLeaseId);

    /**
     * 储物柜开锁。
     *
     * @param form 请求参数
     */
    void cabinetWifiOpen(StudyRoomCabinetWifiOpenClientReqDTO form);

    /**
     * 门店的储物柜列表。
     *
     * @param venueId 门店 ID
     * @return 储物柜列表数据
     */
    StudyRoomCabinetVenueListClientRespDTO cabinetVenueList(Integer venueId);

    /**
     * 柜体的格口列表。
     *
     * @param cabinetId 柜体 ID
     * @return 格口列表数据
     */
    StudyRoomCabinetGridListClientRespDTO cabinetGridList(Integer cabinetId);

    /**
     * 我当前已租的柜格列表。
     *
     * @param venueId 门店 ID
     * @return 柜格列表数据
     */
    StudyRoomCabinetUserGridListClientRespDTO cabinetUserGridList(Integer venueId);

    /**
     * 柜格定价。
     *
     * @param gridId 格口 ID
     * @return 定价数据
     */
    StudyRoomCabinetGridPriceClientRespDTO cabinetGridPrice(Integer gridId);

    /**
     * 美团团购券核销。
     *
     * @param form 请求参数
     * @return 核销结果数据
     */
    StudyRoomConsumeResultClientRespDTO receiptConsume(StudyRoomReceiptConsumeClientReqDTO form);

    /**
     * 抖音券核销。
     *
     * @param form 请求参数
     * @return 核销结果数据
     */
    StudyRoomConsumeResultClientRespDTO douYinVerify(StudyRoomDouYinVerifyClientReqDTO form);
}
