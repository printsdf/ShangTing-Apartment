package com.atguigu.hellomp.mapper;

import com.atguigu.hellomp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wy292
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-07-19 17:39:05
* @Entity com.atguigu.hellomp.entity.User
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<User> selectUserPage(IPage<User> page);
}




