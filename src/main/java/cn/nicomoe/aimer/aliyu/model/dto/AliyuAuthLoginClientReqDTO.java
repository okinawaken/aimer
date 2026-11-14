package cn.nicomoe.aimer.aliyu.model.dto;

import lombok.Data;

/**
 * 小程序授权登录请求（{@code POST /login/authLoginForApplet}）。
 * <p>
 * 走阿里安全 mpassport 登录 SDK。源码中存在三套并行的载荷形态，均发往同一接口：
 * </p>
 * <pre>
 * 形态 A（一键登录主流程）
 *   { appType, authCode, authToken, ext: { iv, encryptedData, phone, messageCode } }
 * 形态 A'（支付宝渠道 g.Nc 分支）
 *   { appType, authCode, authToken, ext: { encryptedData } }
 * 形态 B（已缓存手机号凭证，走 jsCode）
 *   { langType: 1, jsCode, encryptedIv, encryptedData, userTel }
 * </pre>
 * <p>
 * 三者还可能附加 {@code sqmCode}（来自阿里安全环境）。关键澄清：
 * <ul>
 *   <li>{@code authCode} / {@code jsCode} 是 {@code wx.login()} 返回的 code（同一值，两套形态字段名不同），<b>本身不加密</b>。</li>
 *   <li>{@code encryptedData} 与 {@code iv} 来自<b>微信平台</b> {@code wx.getPhoneNumber} 授权回调，
 *       小程序只透传并 {@code setStorageSync} 暂存，<b>并非本地 RC4+RSA 生成</b>。</li>
 *   <li>{@code encryptedData} 嵌套在 {@code ext} 内，不是顶层扁平字段。</li>
 * </ul>
 * 本 DTO 仅承载字段结构，加密产物需由调用方（真机授权或抓包）提供，离线无法生成。
 *
 * @author bixiu
 * @since 4.3.0
 */
@Data
public class AliyuAuthLoginClientReqDTO {

    /**
     * 应用类型（{@code (0,g.oN)()} 产出）
     */
    private String appType;

    /**
     * {@code wx.login()} 返回的 code（非加密），形态 A 字段名；形态 B 中同值以 {@code jsCode} 传递
     */
    private String authCode;

    /**
     * mpassport token（{@code (0,h.bW)()} 产出）
     */
    private String authToken;

    /**
     * 登录凭证包装：{@code iv} + {@code encryptedData} 来自 {@code wx.getPhoneNumber} 回调，
     * {@code phone} / {@code messageCode} 为配套的手机号与短信验证码
     */
    private Ext ext;

    /**
     * 形态 B 专用：语言类型（固定 1）。仅 jsCode 路径使用，主流程不填。
     */
    private String langType;

    /**
     * 形态 B 专用：{@code wx.login()} 的 code，同 {@code authCode}。仅 jsCode 路径使用。
     */
    private String jsCode;

    /**
     * 形态 B 专用：与 {@code ext.iv} 同源，来自 {@code wx.getPhoneNumber} 回调的 {@code iv}。
     */
    private String encryptedIv;

    /**
     * 形态 B 专用：用户手机号（明文，作为服务端辅助校验）。
     */
    private String userTel;

    /**
     * 阿里安全环境产出的验证码（部分场景附加）。
     */
    private String sqmCode;

    /**
     * 登录凭证包装对象（形态 A 的 {@code ext}）。
     * <p>
     * {@code iv} 与 {@code encryptedData} 由 {@code wx.getPhoneNumber} 授权回调产出，
     * 小程序侧 {@code setStorageSync("encryptedIv"/"encryptedData")} 暂存后透传至此。
     * </p>
     */
    @Data
    public static class Ext {

        /**
         * AES 加密向量（微信 {@code getPhoneNumber} 回调 {@code iv}）
         */
        private String iv;

        /**
         * 微信对手机号的加密包（微信平台产出，非本地加密）
         */
        private String encryptedData;

        /**
         * 手机号（明文，配套校验）
         */
        private String phone;

        /**
         * 短信验证码
         */
        private String messageCode;
    }
}
