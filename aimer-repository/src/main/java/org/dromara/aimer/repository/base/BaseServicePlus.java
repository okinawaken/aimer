package org.dromara.aimer.repository.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.dromara.aimer.common.utils.MapstructUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author bixiu
 */
public interface BaseServicePlus<E> extends IService<E> {

    default <V> V getById(Serializable id, Class<V> voClass) {
        E obj = getById(id);
        return MapstructUtils.convert(obj, voClass);
    }

    default <V> Optional<V> getOptById(Serializable id, Class<V> voClass) {
        Optional<E> opt = getOptById(id);
        return opt.map(item -> MapstructUtils.convert(item, voClass));
    }

    default <V> List<V> listByIds(Collection<? extends Serializable> idList, Class<V> voClass) {
        List<E> objList = listByIds(idList);
        return MapstructUtils.convert(objList, voClass);
    }

    default <V> List<V> listByMap(Map<String, Object> columnMap, Class<V> voClass) {
        List<E> objs = listByMap(columnMap);
        return MapstructUtils.convert(objs, voClass);
    }

    default <V> V getOne(Wrapper<E> queryWrapper, Class<V> voClass) {
        E obj = getOne(queryWrapper);
        return MapstructUtils.convert(obj, voClass);
    }

    default <V> Optional<V> getOneOpt(Wrapper<E> queryWrapper, Class<V> voClass) {
        Optional<E> opt = getOneOpt(queryWrapper);
        return opt.map(item -> MapstructUtils.convert(item, voClass));
    }

    default <V> V getOne(Wrapper<E> queryWrapper, boolean throwEx, Class<V> voClass) {
        E obj = getOne(queryWrapper, throwEx);
        return MapstructUtils.convert(obj, voClass);
    }

    default <V> Optional<V> getOneOpt(Wrapper<E> queryWrapper, boolean throwEx, Class<V> voClass) {
        Optional<E> opt = getOneOpt(queryWrapper, throwEx);
        return opt.map(item -> MapstructUtils.convert(item, voClass));
    }

    default <V> List<V> list(Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> objList = list(queryWrapper);
        return MapstructUtils.convert(objList, voClass);
    }

    default <V> List<V> list(IPage<E> page, Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> objList = list(page, queryWrapper);
        return MapstructUtils.convert(objList, voClass);
    }

    default <V> List<V> list(Class<V> voClass) {
        List<E> objList = list();
        return MapstructUtils.convert(objList, voClass);
    }

    default <V> List<V> list(IPage<E> page, Class<V> voClass) {
        List<E> objList = list(page);
        return MapstructUtils.convert(objList, voClass);
    }

    default <V> IPage<V> page(IPage<E> page, Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> list = list(page, queryWrapper);
        List<V> voList = MapstructUtils.convert(list, voClass);
        IPage<V> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(voList);
        return voPage;
    }

    default <V> IPage<V> page(IPage<E> page, Class<V> voClass) {
        List<E> list = list(page);
        List<V> voList = MapstructUtils.convert(list, voClass);
        IPage<V> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(voList);
        return voPage;
    }

    default <V> List<V> listObjs(Class<V> voClass) {
        List<E> objList = listObjs();
        return MapstructUtils.convert(objList, voClass);
    }

    default <V> List<V> listObjs(Wrapper<E> queryWrapper, Class<V> voClass) {
        List<E> objList = listObjs(queryWrapper);
        return MapstructUtils.convert(objList, voClass);
    }
}
