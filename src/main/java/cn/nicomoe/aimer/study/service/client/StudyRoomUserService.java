package cn.nicomoe.aimer.study.service.client;

import cn.nicomoe.aimer.study.model.dto.StudyRoomAuthorizeHeadImgClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUpdateUserInfoClientReqDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserDetailClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserFeeClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomUserSignListClientRespDTO;

public interface StudyRoomUserService {

    /**
     * 用户概览。
     */
    StudyRoomUserIndexClientRespDTO userIndex();

    /**
     * 用户详情。
     */
    StudyRoomUserDetailClientRespDTO userDetail();

    /**
     * 用户详情（指定 token）。
     */
    StudyRoomUserDetailClientRespDTO userDetail(String token);

    /**
     * 欠费 / 当前签到状态。
     */
    StudyRoomUserFeeClientRespDTO userFee();

    /**
     * 修改资料。
     */
    Integer updateUserInfo(StudyRoomUpdateUserInfoClientReqDTO form);

    /**
     * 更新授权头像。
     */
    void authorizeHeadImg(StudyRoomAuthorizeHeadImgClientReqDTO form);

    /**
     * 解除授权（退出登录）。
     */
    void cancelAuthorize();

    /**
     * 签到记录列表。
     */
    StudyRoomUserSignListClientRespDTO userSignList(Long id);

    /**
     * 更早的签到记录。
     */
    StudyRoomUserSignListClientRespDTO userSignListMore();
}
