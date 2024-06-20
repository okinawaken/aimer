package org.dromara.aimer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否常量
 *
 * @author bixiu
 */
@Getter
@AllArgsConstructor
public enum YesOrNoEnum {

    /**
     * 否
     */
    NO(0),

    /**
     * 是
     */
    YES(1),
    ;

    private final Integer code;
}
