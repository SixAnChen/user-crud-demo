package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping
    public Result selectAll() {
        return Result.success(userService.selectAll());
    }

    /**
     * 分页添加查询
     * @param userPageRequest
     * @return
     */
    @GetMapping("page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(userService.page(userPageRequest));
    }


    /**
     * 新增、修改
     * @param user
     * @return
     */
    @PostMapping
    public Result save(@RequestBody User user) {
        if (user.getId() == null) {
            userService.insert(user);
        } else {
            userService.update(user);
        }
        return Result.success();
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userService.delete(id);
        return Result.success();
    }

    /**
     * 批量删除
     * @param list
     * @return
     */
    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<User> list) {
        for (User user : list) {
            userService.delete(user.getId());
        }
        return Result.success();
    }

    /**
     * 重置密码
     * @param user
     * @return
     */
    @PutMapping("resetPass")
    public Result resetPass(@RequestBody User user) {
        userService.resetPass(user);
        return Result.success();
    }
}
