package org.dromara.aimer.service.internal.generator.impl;

import org.dromara.aimer.repository.domain.SysUserPostDO;
import org.dromara.aimer.repository.mapper.SysUserPostMapper;
import org.dromara.aimer.service.internal.generator.ISysUserPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与岗位关联表 服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPostDO> implements ISysUserPostService {

}
