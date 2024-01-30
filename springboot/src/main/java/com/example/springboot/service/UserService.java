package com.example.springboot.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 * 作者：SixAn
 * 日期：2023-10-20 04:01
 */
@Service
public class UserService {

    private static final String DEFAULT_PASS = "123456";

    @Resource
    UserMapper userMapper;

    /**
     * 查询所有
     * @return
     */
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    /**
     * 分页条件查询
     * @param userPageRequest
     * @return
     */
    public Object page(UserPageRequest userPageRequest) {
        PageHelper.startPage(userPageRequest.getPageNum(), userPageRequest.getPageSize());
        List<User> userList = userMapper.page(userPageRequest);
        return new PageInfo<>(userList);
    }


    /**
     * 新增
     * @param user
     */
    public void insert(User user) {
        Date date = new Date();
        // 当做卡号处理
        user.setUsername(DateUtil.format(date, "yyyy-MM-dd") + "-" + IdUtil.fastSimpleUUID());
        // insertSelective, 忽略空值，只插入非空的值
        userMapper.insertSelective(user);
    }

    /**
     * 修改
     * @param user
     */
    public void update(User user) {
        // 设置默认卡号
        if (user.getUsername() == null) {
            Date date = new Date();
            user.setUsername(DateUtil.format(date, "yyyy-MM-dd") + "-" + IdUtil.fastSimpleUUID());
        }
        user.setUpdatetime(new Date());
        // updateByPrimaryKeySelective, 根据主键修改数据，忽略空值
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Integer id) {
        // deleteByPrimaryKey, 根据主键删除
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 重置密码
     * @param user
     */
    public void resetPass(User user) {
        user.setUpdatetime(new Date());
        user.setPassword(DEFAULT_PASS);
        userMapper.updateByPrimaryKeySelective(user);
    }
}
