package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpRecordReqDTO;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;

/** 弹窗营销域。 */
public interface AliyuPopUpClient extends AliyuBaseClient {

    /** 查询弹窗配置。 */
    @Post(url = "/aliyuPopUp/query")
    AliyuClientResult<AliyuPopUpQueryRespDTO> query(@JSONBody AliyuPopUpQueryReqDTO body);

    /** 弹窗曝光记录。 */
    @Post(url = "/aliyuPopUp/record")
    AliyuClientResult<Void> record(@JSONBody AliyuPopUpRecordReqDTO body);
}
