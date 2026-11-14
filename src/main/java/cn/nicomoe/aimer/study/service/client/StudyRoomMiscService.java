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

/** 自习室其他域服务：储物柜、团购券核销。 */
public interface StudyRoomMiscService {

    /** 储物柜租赁订单。 */
    StudyRoomCabinetLeaseOrderListClientRespDTO cabinetLeaseOrderList();

    /** 储物柜租赁支付，返回支付参数。 */
    StudyRoomWxPayParamClientRespDTO cabinetLeasePay(Long leaseId, Long userId);

    /** 储物柜下单。 */
    StudyRoomCabinetLeaseCommitClientRespDTO cabinetLeaseCommit(StudyRoomCabinetLeaseCommitClientReqDTO form);

    /** 储物柜逾期支付，返回支付参数。 */
    StudyRoomCabinetLeaseOverduePayClientRespDTO cabinetLeaseOverduePay(Long leaseId, String userCode);

    /** 储物柜自动退租。 */
    void cabinetLeaseAutoCheckout(Long lastLeaseId);

    /** 储物柜开锁。 */
    void cabinetWifiOpen(StudyRoomCabinetWifiOpenClientReqDTO form);

    /** 门店的储物柜列表。 */
    StudyRoomCabinetVenueListClientRespDTO cabinetVenueList(Integer venueId);

    /** 柜体的格口列表。 */
    StudyRoomCabinetGridListClientRespDTO cabinetGridList(Integer cabinetId);

    /** 我当前已租的柜格列表。 */
    StudyRoomCabinetUserGridListClientRespDTO cabinetUserGridList(Integer venueId);

    /** 柜格定价。 */
    StudyRoomCabinetGridPriceClientRespDTO cabinetGridPrice(Integer gridId);

    /** 美团团购券核销。 */
    StudyRoomConsumeResultClientRespDTO receiptConsume(StudyRoomReceiptConsumeClientReqDTO form);

    /** 抖音券核销。 */
    StudyRoomConsumeResultClientRespDTO douYinVerify(StudyRoomDouYinVerifyClientReqDTO form);
}
