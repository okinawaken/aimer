package cn.nicomoe.aimer.aliyu.service.risk;

/** 阿里鱼小程序风控头计算服务。 <p>提供风控拦截器逐次计算的 {@code coeus}（风控等级）、{@code epeius}（毫秒时间戳）与 {@code hecate}（请求体签名）。 签名对象是加密前的明文请求体，故相关方法必须在字段加密之前调用。 */
public interface AliyuRiskService {

    /** 计算 {@code coeus} 头：按请求路径匹配高风险接口清单，命中返回高等级，否则返回默认等级。 参数 path：请求路径（不含域名与查询串） 返回：风控等级字符串 */
    String calcCoeus(String path);

    /** 计算 {@code epeius} 头：当前毫秒时间戳。 返回：毫秒级时间戳的字符串形式 */
    String calcEpeius();

    /** 计算 {@code hecate} 头。 <p>签名对象是加密前的明文请求体，故必须在字段加密之前调用。 当 {@code epeius} 为空时，内部自动取当前时间戳，保证两个头时间一致。 参数 data：明文请求体（DTO、原生数组、Map 或字符串 JSON 均可） 参数 epeius：已计算的 epeius 时间戳；为空则内部重新取值返回：请求体的 MD5 签名（十六进制小写） */
    String calcHecate(Object data, String epeius);
}