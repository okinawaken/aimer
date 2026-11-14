package cn.nicomoe.aimer.aliyu.client.http.interceptor;

import cn.nicomoe.aimer.aliyu.client.http.interceptor.util.AliyuRequestUtils;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.service.risk.AliyuRiskService;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.interceptor.ForestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 注入风控头，必须在字段加密前对明文请求体签名。 */
@Component
@RequiredArgsConstructor
public class AliyuRiskInterceptor implements ForestInterceptor {

    private final AliyuRiskService riskService;

    @Override
    public boolean beforeExecute(ForestRequest request) {
        String coeus = this.riskService.calcCoeus(request.getPath());
        String epeius = this.riskService.calcEpeius();
        String hecate = this.riskService.calcHecate(AliyuRequestUtils.plainBody(request), epeius);

        request.addHeader(AliyuApiConstants.Header.COEUS, coeus);
        request.addHeader(AliyuApiConstants.Header.EPEIUS, epeius);
        request.addHeader(AliyuApiConstants.Header.HECATE, hecate);
        return true;
    }
}