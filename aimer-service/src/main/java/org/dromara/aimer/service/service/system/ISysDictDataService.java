package org.dromara.aimer.service.service.system;

import org.dromara.aimer.common.constants.SeparatorConstant;
import org.dromara.aimer.repository.base.BaseServicePlus;
import org.dromara.aimer.repository.domain.SysDictDataDO;

import java.util.Map;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
public interface ISysDictDataService extends BaseServicePlus<SysDictDataDO> {

    /**
     * 获取字典下所有的字典值与标签
     *
     * @param dictType 字典类型
     * @return dictValue为key，dictLabel为值组成的Map
     */
    Map<String, String> getDictValueToDictLabelMapByDictType(String dictType);

    /**
     * 获取字典下所有的字典标签与值
     *
     * @param dictType 字典类型
     * @return dictLabel为key，dictValue为值组成的Map
     */
    Map<String, String> getDictLabelToDictValueMapByDictType(String dictType);

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    String getDictLabel(String dictType, String dictValue, String separator);

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    String getDictValue(String dictType, String dictLabel, String separator);

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    default String getDictLabel(String dictType, String dictValue) {
        return getDictLabel(dictType, dictValue, SeparatorConstant.COMMA);
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @return 字典值
     */
    default String getDictValue(String dictType, String dictLabel) {
        return getDictValue(dictType, dictLabel, SeparatorConstant.COMMA);
    }
}
