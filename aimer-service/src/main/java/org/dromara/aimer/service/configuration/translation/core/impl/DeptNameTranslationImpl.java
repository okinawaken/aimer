package org.dromara.aimer.service.configuration.translation.core.impl;

import org.dromara.aimer.service.configuration.translation.annotation.TranslationType;
import org.dromara.aimer.service.configuration.translation.constant.TransConstant;
import org.dromara.aimer.service.configuration.translation.core.TranslationInterface;
import org.springframework.stereotype.Component;


/**
 * 部门翻译实现
 * @author bixiu
 */
@Component
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
public class DeptNameTranslationImpl implements TranslationInterface<String> {

    @Override
    public String translation(Object key, String other) {
        return null;
    }
}
