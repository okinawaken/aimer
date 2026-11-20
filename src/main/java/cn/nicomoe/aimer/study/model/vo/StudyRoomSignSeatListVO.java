package cn.nicomoe.aimer.study.model.vo;

import cn.nicomoe.aimer.study.model.dto.StudyRoomSignSeatListClientRespDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.Objects;

/**
 * 签到选座响应（{@code /mp/study/seat/list} 的 {@code data}），有两种形态： <ul> <li>普通用户：座位图对象，按 {@link StudyRoomSignSeatMapVO} 解析</li> <li>存在待签到预约：上游直接完成签到，{@code data} 为整数 {@code 1}</li> </ul> 整数形态由 Jackson 委托构造器承接，对象形态仍走无参构造器。
 */
@Data
@AutoMapper(target = StudyRoomSignSeatListClientRespDTO.class)
public class StudyRoomSignSeatListVO extends StudyRoomSignSeatMapVO {

    /**
     * 预约座位签到成功标志。仅当上游直接完成预约座位签到时返回，值为 1
     */
    private Integer signInFlag;

    /**
     * 对象形态（普通座位图）走默认构造器。仅有委托构造器时 Jackson 会把对象也交给 {@code Integer} 反序列化并抛{@code MismatchedInputException}，因此必须保留无参构造器。
     */
    public StudyRoomSignSeatListVO() {
    }

    /**
     * 兼容 {@code data} 为整数 {@code 1} 的响应
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public StudyRoomSignSeatListVO(Integer signInFlag) {
        this.signInFlag = signInFlag;
    }

    /**
     * 是否签到成功
     */
    public boolean isSignInSuccess() {
        return Objects.equals(this.signInFlag, 1);
    }
}
