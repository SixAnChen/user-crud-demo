package com.example.springboot.mapper;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserMapper extends Mapper<User> {

    /**
     * 查询所有
     * @return
     */
    @Select("select * from user")
    List<User> selectAll();

    /**
     * 分页条件查询
     * @param userPageRequest
     * @return
     */
    List<User> page(UserPageRequest userPageRequest);


}
