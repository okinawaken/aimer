package cn.nicomoe.aimer.study.model.dto;

import lombok.Data;

/** 修改资料（{@code /mp/user/updateInfo}），上游只接收以下四个字段。 */
@Data
public class StudyRoomUpdateUserInfoClientReqDTO {

    /** 姓名 */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 性别：0 女，1 男 */
    private Integer gender;

    /** 是否参与排行榜：0 否，1 是 */
    private Integer rankUserState;

}
