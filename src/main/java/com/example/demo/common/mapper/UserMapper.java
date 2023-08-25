package com.example.demo.common.mapper;

import com.example.demo.common.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhangXuBo
 * @since 2023-06-08
 */
public interface UserMapper extends BaseMapper<User> {

    String selectCustome();
}
