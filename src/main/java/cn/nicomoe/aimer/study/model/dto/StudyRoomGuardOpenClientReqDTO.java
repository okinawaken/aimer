package cn.nicomoe.aimer.study.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 门禁开门（/mp/guard/open）
 *
 * @author bixiu
 * @since 4.2.0
 */
@Data
public class StudyRoomGuardOpenClientReqDTO {

    /**
     * 门禁 ID（/mp/guard/select 返回的 id）（必填）
     */
    @NotNull(message = "门禁ID不能为空")
    private Long guardId;

}
