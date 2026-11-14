package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.InvoicingOrderListRespDTO;

/**
 * 选座 / 支付 / 发票 / 消息域：{@code /pms}、{@code /maipay2}、{@code /invoice}、{@code /msg}。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuCheckoutClient extends AliyuBaseClient {

    /**
     * 选座缓存预热。
     *
     * @param projectId 项目 ID
     * @param eventId   场次 ID
     * @return 响应数据
     */
    @Get(url = "/pms/chooseSeat/seatCachePreheat")
    AliyuClientResult<Boolean> seatCachePreheat(@Query(value = "projectId") String projectId,
                                               @Query(value = "eventId") String eventId);

    /**
     * 渲染支付（卖座支付）。
     *
     * @param supplierClass  供应商类别
     * @param outerPaymentNo 外部支付单号
     * @return 响应数据
     */
    @Get(url = "/maipay2/renderPay")
    AliyuClientResult<Object> renderPay(@Query(value = "supplierClass") String supplierClass,
                                        @Query(value = "outerPaymentNo") String outerPaymentNo);

    /**
     * 发起支付（拉起微信支付，入参 {@code payChannelId, outerPaymentNo}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/maipay2/doPay")
    AliyuClientResult<Object> doPay(@JSONBody Map<String, Object> body);

    /**
     * 可开票订单列表。
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return 响应数据
     */
    @Get(url = "/invoice/simple/getInvoicingOrderList")
    AliyuClientResult<InvoicingOrderListRespDTO> getInvoicingOrderList(@Query(value = "page") Integer page,
                                                    @Query(value = "pageSize") Integer pageSize);

    /**
     * 可开票订单详情。
     *
     * @param orderId 订单 ID
     * @return 响应数据
     */
    @Get(url = "/invoice/simple/getInvoicingOrderDetail")
    AliyuClientResult<Object> getInvoicingOrderDetail(@Query(value = "orderId") String orderId);

    /**
     * 开票灰度判断。
     *
     * @return 响应数据
     */
    @Get(url = "/invoice/simple/invoiceGray")
    AliyuClientResult<Boolean> invoiceGray();

    /**
     * 申请开票。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/invoice/simple/applyInvoice")
    AliyuClientResult<Object> applyInvoice(@JSONBody Map<String, Object> body);

    /**
     * 发送手机短信验证码（入参含阿里安全滑块参数 {@code signParam, sig, sessionId, token}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/msg/sendMobileMsg")
    AliyuClientResult<Object> sendMobileMsg(@JSONBody Map<String, Object> body);

    /**
     * 发送邮箱验证码（入参含阿里安全滑块参数 {@code signParam, sig, sessionId, token}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/msg/sendEmailMsg")
    AliyuClientResult<Object> sendEmailMsg(@JSONBody Map<String, Object> body);
}
