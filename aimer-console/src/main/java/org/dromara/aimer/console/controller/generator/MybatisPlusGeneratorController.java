package org.dromara.aimer.console.controller.generator;

import org.dromara.aimer.common.response.BaseResponse;
import org.dromara.aimer.service.utils.MybatisPlusGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bixiu
 */
@RestController
@RequestMapping("/mybatis-plus-generator")
public class MybatisPlusGeneratorController {

    @Autowired
    private MybatisPlusGeneratorUtils mybatisPlusGeneratorUtils;

    @GetMapping("")
    public BaseResponse<Void> testMybatisPlusGenerator() {
        mybatisPlusGeneratorUtils.generateTable();
        return BaseResponse.ok();
    }
}
