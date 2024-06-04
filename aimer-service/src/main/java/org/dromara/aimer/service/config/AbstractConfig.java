package org.dromara.aimer.service.config;

import com.mendmix.common.util.BeanUtils;
import com.mendmix.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public abstract class AbstractConfig {

    @Value("${spring.profiles.active}")
    protected String namespace;

    @Value("${spring.application.name}")
    protected String fileGroup;

    protected void refreshConfig(String configStr) {
        // 监听对象转换
        AbstractConfig newConfig = JsonUtils.toObject(configStr, this.getClass());
        log.info("监听到配置变更，namespace：{}，fileGroup：{}，fileName：{}，oldConfig：{}，newConfig：{}",
                namespace, fileGroup, fileName(), JsonUtils.toJson(this), JsonUtils.toJson(newConfig));

        // 校验配置对象
        ValidatorUtils.validate(newConfig);

        // 更新配置对象
        BeanUtils.copy(newConfig, this, true);
    }

    abstract String fileName();
}
