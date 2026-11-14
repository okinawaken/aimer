package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;
import cn.nicomoe.aimer.aliyu.model.dto.resp.AgreementDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.AgreementRecordListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.CountryAreaRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.CredentialListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.DeregistrationRespDTO;

/**
 * 协议 / 凭证 / 地区域：{@code /agreement}、{@code /credential}、{@code /countryArea}。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuAgreementClient extends AliyuBaseClient {

    /**
     * 协议详情。
     *
     * @return 响应数据
     */
    @Get(url = "/agreement/detail")
    AliyuClientResult<AgreementDetailRespDTO> detail();

    /**
     * 注销协议。
     *
     * @return 响应数据
     */
    @Get(url = "/agreement/deregistration")
    AliyuClientResult<DeregistrationRespDTO> deregistration();

    /**
     * 协议记录列表。
     *
     * @return 响应数据
     */
    @Get(url = "/agreement/record/list")
    AliyuClientResult<java.util.List<AgreementRecordListRespDTO>> recordList();

    /**
     * 协议记录详情。
     *
     * @param recordId 记录 ID
     * @return 响应数据
     */
    @Get(url = "/agreement/record/{recordId}")
    AliyuClientResult<Object> recordDetail(@Var(value = "recordId") String recordId);

    /**
     * 凭证列表（入参 {@code state, scene, eventIds}）。
     *
     * @param state    状态
     * @param scene    场景
     * @param eventIds 场次 ID（逗号分隔）
     * @return 响应数据
     */
    @Get(url = "/credential/getList")
    AliyuClientResult<java.util.List<CredentialListRespDTO>> credentialList(@Query(value = "state") String state,
                                             @Query(value = "scene") String scene,
                                             @Query(value = "eventIds") String eventIds);

    /**
     * 国家地区（注册用）。
     *
     * @return 响应数据
     */
    @Get(url = "/countryArea/queryForUserRegister")
    AliyuClientResult<CountryAreaRespDTO> queryForUserRegister();
}
