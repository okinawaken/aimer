package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AgreementRecordListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.CountryAreaRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.CredentialListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.DeregistrationRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;

/** 协议 / 凭证 / 地区域（显式用户上下文）。 */
public interface AliyuAgreementV2Client extends AliyuBaseClient {

    /** 协议详情。 */
    @Get(url = "/agreement/detail")
    AliyuClientResult<AgreementDetailRespDTO> detail(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 注销协议。 */
    @Get(url = "/agreement/deregistration")
    AliyuClientResult<DeregistrationRespDTO> deregistration(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 协议记录列表。 */
    @Get(url = "/agreement/record/list")
    AliyuClientResult<java.util.List<AgreementRecordListRespDTO>> recordList(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 协议记录详情。 */
    @Get(url = "/agreement/record/{recordId}")
    AliyuClientResult<AgreementRecordDetailRespDTO> recordDetail(@Var(value = "recordId") String recordId,
                                                                 @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 凭证列表。 参数 eventIds：场次 ID（逗号分隔） */
    @Get(url = "/credential/getList")
    AliyuClientResult<java.util.List<CredentialListRespDTO>> credentialList(@Query(value = "state") String state,
                                                                            @Query(value = "scene") String scene,
                                                                            @Query(value = "eventIds") String eventIds,
                                                                            @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 国家地区（注册用）。 */
    @Get(url = "/countryArea/queryForUserRegister")
    AliyuClientResult<CountryAreaRespDTO> queryForUserRegister(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
