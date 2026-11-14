package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuAuthLoginClientReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

/**
 * 登录鉴权域：{@code /login}。
 * <p>
 * 登录走阿里安全 mpassport 登录 SDK（阿里鱼/大麦共用）。{@code authCode} 为 {@code wx.login()} 返回的 code（不加密）；
 * {@code encryptedData} / {@code iv} 来自 {@code wx.getPhoneNumber} 授权回调（微信平台产出，非本地 RC4+RSA 生成），
 * 小程序侧 {@code setStorageSync("encryptedIv"/"encryptedData")} 暂存后透传。载荷为 {@code ext:{iv, encryptedData, phone, messageCode}}
 * 包装结构（详见 {@link AliyuAuthLoginClientReqDTO}）。本接口仅承载请求结构，加密产物需由调用方（真机授权或抓包）提供。
 * </p>
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuLoginClient extends AliyuBaseClient {

    /**
     * 小程序授权登录（加密）。
     * <p>
     * 成功后服务端写入 cookie（JSESSIONID / MZCONSUMERJSESSIONID / MZOPERATEID），后续请求携带完整凭证束。
     *
     * @param form 登录凭证（含加密产物）
     * @return 响应数据
     */
    @Post(url = "/login/authLoginForApplet")
    AliyuClientResult<Object> authLoginForApplet(@JSONBody AliyuAuthLoginClientReqDTO form);
}
