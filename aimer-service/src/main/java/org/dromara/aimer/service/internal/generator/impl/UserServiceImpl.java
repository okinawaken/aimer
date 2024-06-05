package org.dromara.aimer.service.internal.generator.impl;

import org.dromara.aimer.repository.entity.UserEntity;
import org.dromara.aimer.repository.mapper.UserMapper;
import org.dromara.aimer.service.internal.generator.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Shinomiya
 * @since 2024-06-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements IUserService {

}
