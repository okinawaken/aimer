package org.dromara.aimer.service.internal.generator.impl;

import org.dromara.aimer.repository.entity.UndoLogEntity;
import org.dromara.aimer.repository.mapper.UndoLogMapper;
import org.dromara.aimer.service.internal.generator.IUndoLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * AT transaction mode undo table 服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Service
public class UndoLogServiceImpl extends ServiceImpl<UndoLogMapper, UndoLogEntity> implements IUndoLogService {

}
