package cn.nicomoe.aimer.study.client.http.v1;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAuthorizeHeadImgClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomFileClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUpdateUserInfoClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserDetailClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserFeeClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignListClientRespDTO;
import com.dtflys.forest.annotation.DataFile;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.io.File;

/**
 * 用户域：资料、头像
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomUserClient extends StudyRoomBaseClient {

    /**
     * 用户概览：头像、手机号、累计签到次数、累计学习时长
     *
     * @return 响应数据
     */
    @Get(url = "/mp/user/index")
    StudyRoomClientResult<StudyRoomUserIndexClientRespDTO> userIndex();

    /**
     * 用户详情
     *
     * @return 响应数据
     */
    @Get(url = "/mp/user/detailInfo")
    StudyRoomClientResult<StudyRoomUserDetailClientRespDTO> userDetail();

    /**
     * 欠费 / 当前签到状态
     *
     * @return 响应数据
     */
    @Get(url = "/mp/user/info/fee")
    StudyRoomClientResult<StudyRoomUserFeeClientRespDTO> userFee();

    /**
     * 修改资料
     * <p>
     * 实测：传空对象也返回 {@code code=200, data=1}，说明服务端忽略空字段；{@code data} 是整数
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/user/updateInfo")
    StudyRoomClientResult<Integer> updateUserInfo(@JSONBody StudyRoomUpdateUserInfoClientReqDTO form);

    /**
     * 更新授权头像（URL 形式）
     * <p>
     * 入参：{@code {nickName, headImg}}（实测）；成功时 {@code data} 为 {@code null}
     *
     * @param form 请求参数
     * @return 响应数据
     */
    @Post(url = "/mp/user/authorize/headImg")
    StudyRoomClientResult<Void> authorizeHeadImg(@JSONBody StudyRoomAuthorizeHeadImgClientReqDTO form);

    /**
     * 上传授权头像（文件形式，multipart 字段名 {@code files}）
     *
     * @param file 参数 file
     * @return 响应数据
     */
    @Post(url = "/mp/user/authorize/headImg/upload", contentType = "multipart/form-data")
    StudyRoomClientResult<StudyRoomFileClientRespDTO> uploadAuthorizeHeadImg(@DataFile(value = "files") File file);

    /**
     * 解除授权（退出登录）
     *
     * @return 响应数据
     */
    @Post(url = "/mp/user/authorize/cancel")
    StudyRoomClientResult<Void> cancelAuthorize();

    /**
     * 签到记录列表（最近 N 条）
     * <p>
     * 入参 {@code id} 传 0 即可；返回 {@code {list:[StudyRoomUserSignClientRespDTO]}}。
     *
     * @param id ID
     * @return 响应数据
     */
    @Get(url = "/mp/user/userSign/list")
    StudyRoomClientResult<StudyRoomUserSignListClientRespDTO> userSignList(@Query(value = "id") Long id);

    /**
     * 更早的签到记录（分页续拉，无入参）
     *
     * @return 响应数据
     */
    @Get(url = "/mp/user/userSign/list/more")
    StudyRoomClientResult<StudyRoomUserSignListClientRespDTO> userSignListMore();

}
