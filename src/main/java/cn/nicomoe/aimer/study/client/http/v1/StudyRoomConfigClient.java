package cn.nicomoe.aimer.study.client.http.v1;

import cn.nicomoe.aimer.study.client.http.StudyRoomBaseClient;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomConfigClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomNoticeClientRespDTO;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;

/**
 * 配置域：全局配置、公告
 *
 * @author bixiu
 * @since 4.2.0
 */
public interface StudyRoomConfigClient extends StudyRoomBaseClient {

    /**
     * 小程序全局配置（含可提前预约天数、保留时长等）
     *
     * @return 响应数据
     */
    @Get(url = "/mp/sys/config/data")
    StudyRoomClientResult<StudyRoomConfigClientRespDTO> sysConfig();

    /**
     * 公告栏
     * <p>
     * 小程序传 {@code venueId=0} 表示不区分门店
     *
     * @param venueId 门店 ID
     * @return 响应数据
     */
    @Get(url = "/mp/notice/board/info")
    StudyRoomClientResult<StudyRoomNoticeClientRespDTO> noticeBoard(@Query(value = "venueId") Integer venueId);

}
