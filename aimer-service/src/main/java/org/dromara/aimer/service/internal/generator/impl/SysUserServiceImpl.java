package org.dromara.aimer.service.internal.generator.impl;

import org.dromara.aimer.repository.domain.SysUserDO;
import org.dromara.aimer.repository.mapper.SysUserMapper;
import org.dromara.aimer.service.internal.generator.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements ISysUserService {

}
