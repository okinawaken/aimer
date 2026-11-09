package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAuthorizeHeadImgClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUpdateUserInfoClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserDetailClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserFeeClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignListClientRespDTO;

/**
 * 自习室用户域服务。
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomUserService {

    /**
     * 用户概览。
     *
     * @return 用户概览数据
     */
    StudyRoomUserIndexClientRespDTO userIndex();

    /**
     * 用户详情。
     *
     * @return 用户详情数据
     */
    StudyRoomUserDetailClientRespDTO userDetail();

    /**
     * 用户详情（指定 token）。
     *
     * @param token 用户 token
     * @return 用户详情数据
     */
    StudyRoomUserDetailClientRespDTO userDetail(String token);

    /**
     * 欠费 / 当前签到状态。
     *
     * @return 费用状态数据
     */
    StudyRoomUserFeeClientRespDTO userFee();

    /**
     * 修改资料。
     *
     * @param form 请求参数
     * @return 处理结果
     */
    Integer updateUserInfo(StudyRoomUpdateUserInfoClientReqDTO form);

    /**
     * 更新授权头像。
     *
     * @param form 请求参数
     */
    void authorizeHeadImg(StudyRoomAuthorizeHeadImgClientReqDTO form);

    /**
     * 解除授权（退出登录）。
     */
    void cancelAuthorize();

    /**
     * 签到记录列表。
     *
     * @param id ID
     * @return 签到记录数据
     */
    StudyRoomUserSignListClientRespDTO userSignList(Long id);

    /**
     * 更早的签到记录。
     *
     * @return 签到记录数据
     */
    StudyRoomUserSignListClientRespDTO userSignListMore();
}
