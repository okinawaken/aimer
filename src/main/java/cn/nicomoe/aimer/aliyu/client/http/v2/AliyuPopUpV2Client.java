package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpQueryRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuPopUpRecordReqDTO;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Var;

/** 弹窗营销域（显式用户上下文）。 */
public interface AliyuPopUpV2Client extends AliyuBaseClient {

    /** 查询弹窗配置，无弹窗时 {@code data} 为空。 */
    @Post(url = "/aliyuPopUp/query")
    AliyuClientResult<AliyuPopUpQueryRespDTO> query(@JSONBody AliyuPopUpQueryReqDTO body,
                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 弹窗曝光记录。 */
    @Post(url = "/aliyuPopUp/record")
    AliyuClientResult<Void> record(@JSONBody AliyuPopUpRecordReqDTO body,
                                   @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
