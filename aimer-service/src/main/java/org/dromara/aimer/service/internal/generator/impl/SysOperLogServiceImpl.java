package org.dromara.aimer.service.internal.generator.impl;

import org.dromara.aimer.repository.domain.SysOperLogDO;
import org.dromara.aimer.repository.mapper.SysOperLogMapper;
import org.dromara.aimer.service.internal.generator.ISysOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLogDO> implements ISysOperLogService {

}
