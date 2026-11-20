package cn.nicomoe.aimer.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 可预约日期（{@code /mp/reserve/v2/date/select}）。 原始值形如 {@code "2026-10-26 (周一)"}，后续接口的 {@code reserveDateStr} 只取空格前的日期部分。
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudyRoomReserveDateClientRespDTO {

    /**
     * 可预约日期，格式 yyyy-MM-dd (周X)
     */
    private List<String> reserveDateList;
}
