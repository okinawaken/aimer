package cn.nicomoe.aimer.study.client.http;

import cn.nicomoe.aimer.study.client.http.interceptor.StudyRoomAuthInterceptor;
import cn.nicomoe.aimer.study.client.http.interceptor.StudyRoomResponseInterceptor;
import cn.nicomoe.aimer.study.constant.StudyRoomApiConstants;
import com.dtflys.forest.annotation.BaseRequest;

/**
 * 自习室小程序接口的公共请求配置
 *
 * @author bixiu
 * @since 4.2.0
 */
@BaseRequest(baseURL = StudyRoomApiConstants.Gateway.BASE_URL, contentType = "application/json", interceptor = {StudyRoomAuthInterceptor.class, StudyRoomResponseInterceptor.class})
public interface StudyRoomBaseClient {
}
