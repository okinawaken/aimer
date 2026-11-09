package cn.nicomoe.aimer.study.model.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自动预约任务查询条件
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomAutoReserveTaskQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 门店 ID
     */
    private Integer venueId;

    /**
     * 启用状态：0 暂停，1 启用
     */
    private Integer enableStatus;
}
