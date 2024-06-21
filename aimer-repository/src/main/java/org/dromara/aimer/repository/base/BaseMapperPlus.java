package org.dromara.aimer.repository.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.dromara.aimer.common.utils.MapstructUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author bixiu
 */
public interface BaseMapperPlus<E> extends BaseMapper<E> {

    default <V> V selectById(Serializable id, Class<V> voClass) {
        E obj = selectById(id);
        return MapstructUtils.convert(obj, voClass);
    }

    default <V> List<V> selectBatchIds(@Param(Constants.COLL) Collection<? extends Serializable> idList, Class<V> voClass) {
        List<E> list = this.selectBatchIds(idList);
        return MapstructUtils.convert(list, voClass);
    }

    default <V> List<V> selectByMap(Map<String, Object> columnMap, Class<V> voClass) {
        List<E> list = this.selectByMap(columnMap);
        return MapstructUtils.convert(list, voClass);
    }

    default <V> V selectOne(@Param(Constants.WRAPPER) Wrapper<E> queryWrapper, Class<V> voClass) {
        E obj = this.selectOne(queryWrapper);
        return MapstructUtils.convert(obj, voClass);
    }

    default <V> V selectOne(@Param(Constants.WRAPPER) Wrapper<E> queryWrapper, boolean throwEx, Class<V> voClass) {
        E obj = selectOne(queryWrapper, throwEx);
        return MapstructUtils.convert(obj, voClass);
    }

    default <V> List<V> selectList(@Param(Constants.WRAPPER) Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> list = this.selectList(queryWrapper);
        return MapstructUtils.convert(list, voClass);
    }

    default <V> List<V> selectList(IPage<E> page, @Param(Constants.WRAPPER) Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> list = selectList(page, queryWrapper);
        return MapstructUtils.convert(list, voClass);
    }

    default <V> List<V> selectObjs(@Param(Constants.WRAPPER) Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> list = selectObjs(queryWrapper);
        return MapstructUtils.convert(list, voClass);
    }

    default <V> IPage<V> selectPage(IPage<E> page, @Param(Constants.WRAPPER) Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> list = this.selectList(page, queryWrapper);
        IPage<V> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(MapstructUtils.convert(list, voClass));
        return voPage;
    }
}
