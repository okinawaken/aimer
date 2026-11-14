package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.BearerListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.DisListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.LoginUserRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.UserContactRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.UserInfoRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.ValidateUserV2RespDTO;

/**
 * 用户域：{@code /user}。资料、持票人、联系人、账号、退出登录。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuUserClient extends AliyuBaseClient {

    /**
     * 获取登录用户信息。
     *
     * @return 响应数据
     */
    @Get(url = "/user/loginUser")
    AliyuClientResult<LoginUserRespDTO> loginUser();

    /**
     * 用户信息。
     *
     * @return 响应数据
     */
    @Get(url = "/user/userInfo")
    AliyuClientResult<UserInfoRespDTO> userInfo();

    /**
     * 用户联系方式。
     *
     * @return 响应数据
     */
    @Get(url = "/user/userContact")
    AliyuClientResult<UserContactRespDTO> userContact();

    /**
     * 获取持票人列表。
     *
     * @param page            页码
     * @param pageSize        每页数量
     * @param eventIds        场次 ID（逗号分隔）
     * @param exitTicketCert 退票凭证（逗号分隔）
     * @return 响应数据
     */
    @Get(url = "/user/getBearers")
    AliyuClientResult<BearerListRespDTO> getBearers(@Query(value = "page") Integer page,
                                         @Query(value = "pageSize") Integer pageSize,
                                         @Query(value = "eventIds") String eventIds,
                                         @Query(value = "exitTicketCert") String exitTicketCert);

    /**
     * 获取联系人 / 收件人列表。
     *
     * @return 响应数据
     */
    @Get(url = "/user/getDis")
    AliyuClientResult<DisListRespDTO> getDis();

    /**
     * 联系人详情。
     *
     * @return 响应数据
     */
    @Get(url = "/user/getDisInfo")
    AliyuClientResult<Object> getDisInfo();

    /**
     * 账号密码登录。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/userLogin")
    AliyuClientResult<Object> userLogin(@JSONBody Map<String, Object> body);

    /**
     * 校验用户（V2）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/validateUserV2")
    AliyuClientResult<ValidateUserV2RespDTO> validateUserV2(@JSONBody Map<String, Object> body);

    /**
     * 注册并设密码。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/registerSetPass")
    AliyuClientResult<Object> registerSetPass(@JSONBody Map<String, Object> body);

    /**
     * 找回密码。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/findPass")
    AliyuClientResult<Object> findPass(@JSONBody Map<String, Object> body);

    /**
     * 找回密码校验。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/findPassCheck")
    AliyuClientResult<Object> findPassCheck(@JSONBody Map<String, Object> body);

    /**
     * 修改用户信息。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/modifyUser")
    AliyuClientResult<Object> modifyUser(@JSONBody Map<String, Object> body);

    /**
     * 修改联系人。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/modifyDis")
    AliyuClientResult<Object> modifyDis(@JSONBody Map<String, Object> body);

    /**
     * 新增持票人。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/addBearer")
    AliyuClientResult<Object> addBearer(@JSONBody Map<String, Object> body);

    /**
     * 删除持票人。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/removeBearer")
    AliyuClientResult<Object> removeBearer(@JSONBody Map<String, Object> body);

    /**
     * 新增联系人。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/addDis")
    AliyuClientResult<Object> addDis(@JSONBody Map<String, Object> body);

    /**
     * 删除联系人。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/removeDis")
    AliyuClientResult<Object> removeDis(@JSONBody Map<String, Object> body);

    /**
     * 退出登录（入参 {@code authToken}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/user/loginOff")
    AliyuClientResult<Object> loginOff(@JSONBody Map<String, Object> body);
}
