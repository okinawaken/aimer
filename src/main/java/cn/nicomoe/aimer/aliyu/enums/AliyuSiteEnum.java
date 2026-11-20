package cn.nicomoe.aimer.aliyu.enums;

import cn.nicomoe.aimer.enums.BaseEnum;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 阿里鱼小程序请求头 {@code site} 的渠道标识。
 *
 * <p>小程序按运行环境判定：抖音小程序 → {@code mp_douyin}，微信小程序 → {@code mp_weixin}，
 * 支付宝小程序 → {@code mp_alipay}，其余（移动端 H5）→ {@code m}。
 */
@Getter
@RequiredArgsConstructor
public enum AliyuSiteEnum implements BaseEnum<String> {

    /**
     * 微信小程序
     */
    MP_WEIXIN(Code.MP_WEIXIN, "微信小程序"),

    /**
     * 抖音小程序
     */
    MP_DOUYIN(Code.MP_DOUYIN, "抖音小程序"),

    /**
     * 支付宝小程序
     */
    MP_ALIPAY(Code.MP_ALIPAY, "支付宝小程序"),

    /**
     * 移动端 H5，小程序未命中上述环境时的缺省值
     */
    MP(Code.MP, "移动端 H5");

    private final String value;

    private final String description;

    /**
     * 渠道标识的编译期常量。注解属性只接受编译期常量，无法引用 {@link #getValue()}，注解场景请使用这里的值。
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Code {

        /**
         * 微信小程序
         */
        public static final String MP_WEIXIN = "mp_weixin";

        /**
         * 抖音小程序
         */
        public static final String MP_DOUYIN = "mp_douyin";

        /**
         * 支付宝小程序
         */
        public static final String MP_ALIPAY = "mp_alipay";

        /**
         * 移动端 H5
         */
        public static final String MP = "m";
    }
}
