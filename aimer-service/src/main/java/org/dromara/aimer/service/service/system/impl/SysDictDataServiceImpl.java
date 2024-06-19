package org.dromara.aimer.service.service.system.impl;

import org.dromara.aimer.common.constants.SeparatorConstant;
import org.dromara.aimer.common.utils.StreamUtils;
import org.dromara.aimer.repository.domain.SysDictDataDO;
import org.dromara.aimer.repository.mapper.SysDictDataMapper;
import org.dromara.aimer.service.service.system.ISysDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictDataDO> implements ISysDictDataService {

    @Override
    public Map<String, String> getDictValueToDictLabelMapByDictType(String dictType) {
        List<SysDictDataDO> list = this.lambdaQuery()
                .eq(SysDictDataDO::getDictType, dictType)
                .list();
        return StreamUtils.toMap(list, SysDictDataDO::getDictValue, SysDictDataDO::getDictLabel);
    }

    @Override
    public Map<String, String> getDictLabelToDictValueMapByDictType(String dictType) {
        List<SysDictDataDO> list = this.lambdaQuery()
                .eq(SysDictDataDO::getDictType, dictType)
                .list();
        return StreamUtils.toMap(list, SysDictDataDO::getDictLabel, SysDictDataDO::getDictValue);
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
