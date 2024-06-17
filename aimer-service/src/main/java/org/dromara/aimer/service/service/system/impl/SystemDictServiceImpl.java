package org.dromara.aimer.service.service.system.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.aimer.common.constants.SeparatorConstant;
import org.dromara.aimer.common.utils.StreamUtils;
import org.dromara.aimer.repository.entity.SysDictDataEntity;
import org.dromara.aimer.service.internal.generator.ISysDictDataService;
import org.dromara.aimer.service.service.system.ISystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SystemDictServiceImpl implements ISystemDictService {

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Override
    public Map<String, String> getDictValueToDictLabelMapByDictType(String dictType) {
        List<SysDictDataEntity> list = sysDictDataService.lambdaQuery()
                .eq(SysDictDataEntity::getDictType, dictType)
                .list();
        return StreamUtils.toMap(list, SysDictDataEntity::getDictValue, SysDictDataEntity::getDictLabel);
    }

    @Override
    public Map<String, String> getDictLabelToDictValueMapByDictType(String dictType) {
        List<SysDictDataEntity> list = sysDictDataService.lambdaQuery()
                .eq(SysDictDataEntity::getDictType, dictType)
                .list();
        return StreamUtils.toMap(list, SysDictDataEntity::getDictLabel, SysDictDataEntity::getDictValue);
    }

    @Override
    public String getDictLabel(String dictType, String dictValue, String separator) {
        Map<String, String> map = getDictValueToDictLabelMapByDictType(dictType);
        return Arrays.stream(dictValue.split(separator))
                .map(l -> map.getOrDefault(l, SeparatorConstant.EMPTY))
                .collect(Collectors.joining(separator));
    }

    @Override
    public String getDictValue(String dictType, String dictLabel, String separator) {
        Map<String, String> map = getDictLabelToDictValueMapByDictType(dictType);
        return Arrays.stream(dictLabel.split(separator))
                .map(l -> map.getOrDefault(l, SeparatorConstant.EMPTY))
                .collect(Collectors.joining(separator));
    }
}
