package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
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

/**
 * 协议 / 凭证 / 地区域。
 */
public interface AliyuAgreementClient extends AliyuBaseClient {

    /**
     * 协议详情。
     */
    @Get(url = "/agreement/detail")
    AliyuClientResult<AgreementDetailRespDTO> detail();

    /**
     * 注销协议。
     */
    @Get(url = "/agreement/deregistration")
    AliyuClientResult<DeregistrationRespDTO> deregistration();

    /**
     * 协议记录列表。
     */
    @Get(url = "/agreement/record/list")
    AliyuClientResult<java.util.List<AgreementRecordListRespDTO>> recordList();

    /**
     * 协议记录详情。
     */
    @Get(url = "/agreement/record/{recordId}")
    AliyuClientResult<AgreementRecordDetailRespDTO> recordDetail(@Var(value = "recordId") String recordId);

    /**
     * 凭证列表。
     */
    @Get(url = "/credential/getList")
    AliyuClientResult<java.util.List<CredentialListRespDTO>> credentialList(@Query(value = "state") String state,
                                                                            @Query(value = "scene") String scene,
                                                                            @Query(value = "eventIds") String eventIds);

    /**
     * 国家地区（注册用）。{@code all=false} 时仅返回配置可选的国家/地区。
     */
    @Get(url = "/countryArea/queryForUserRegister")
    AliyuClientResult<CountryAreaRespDTO> queryForUserRegister(@Query(value = "all") Boolean all);
}
