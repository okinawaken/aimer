package org.dromara.aimer.service.config;

import com.mendmix.common.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.common.utils.JsonUtils;
import org.dromara.aimer.common.utils.ValidatorUtils;

/**
 * @author bixiu
 */
@Slf4j
public abstract class AbstractConfig {

    protected void refreshConfig(String configStr) {
        // 监听对象转换
        AbstractConfig newConfig = JsonUtils.toObject(configStr, this.getClass());
        log.info("监听到配置变更，namespace：{}，fileGroup：{}，fileName：{}，oldConfig：{}，newConfig：{}",
                namespace(), group(), fileName(), JsonUtils.toJson(this), JsonUtils.toJson(newConfig));

        // 校验配置对象
        ValidatorUtils.validate(newConfig);

        // 更新配置对象
        BeanUtils.copy(newConfig, this, true);
    }

    abstract String namespace();

    abstract String group();

    abstract String fileName();
}
