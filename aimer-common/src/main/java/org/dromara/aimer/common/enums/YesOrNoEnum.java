package org.dromara.aimer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNoEnum {

    NO(0),
    YES(1),
    ;

    private final Integer code;
}
