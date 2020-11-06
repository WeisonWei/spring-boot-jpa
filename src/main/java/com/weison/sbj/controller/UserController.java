package com.weison.sbj.controller;

import com.weison.sbj.entity.User;
import com.weison.sbj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jsondoc.core.annotation.ApiMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Api(tags = "User Service Api")
@org.jsondoc.core.annotation.Api(name = "UserController", description = "")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    String port;

    @ApiMethod(description = "创建用户")
    @ApiOperation("创建用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", example = "1", required = true),
            @ApiImplicitParam(name = "accountType", value = "账户类型", example = "INVITER_CASH", required = true),
    })
    @PostMapping("/users")
    public User createUser() {
        User user = new User();
        user.setSex(User.Sex.FEMALE);
        user.setName("Weison");
        user.setAge(10);
        user.setVersion(1L);
        user.setAddressId(11L);
        user.setBirthDay(LocalDate.now());
        User save = userService.save(user);
        return save;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        User save = userService.save(user);
        return save;
    }

    @ApiMethod(description = "获取用户列表")
    @ApiOperation("获取用户列表")
    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> all = userService.getUsers();
        return all;
    }

    @ApiMethod(description = "通过ID查询用户")
    @ApiOperation("通过ID查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", example = "1", required = true),
    })
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") long id) {
        User byId = userService.getUserById(id);
        return byId;
    }

    @ApiMethod(description = "通过姓名查询用户")
    @ApiOperation("通过姓名查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "小明", example = "1", required = true),
    })
    @GetMapping("/users/name")
    public User getUserByName(@RequestParam("name") String name) {
        User byId = userService.getUserByName(name);
        return byId;
    }

    @ApiMethod(description = "通过条件查询用户")
    @ApiOperation("通过条件查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "小明", example = "1", required = true),
    })
    @PostMapping("/users/page")
    public Page<User> getPageUserByCondition(@RequestBody User user) {
        Page<User> pageUserByCondition = userService.getPageUserByCondition(user);
        return pageUserByCondition;
    }


    @ApiMethod(description = "查询年龄大于")
    @ApiOperation("查询年龄大于")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "min", value = "5", example = "1", required = true),
    })
    @PostMapping("/users/grater")
    public List<User> getPageUserByCondition(@RequestParam Integer min) {
        List<User> greaterThanAge = userService.findGreaterThanAge(min);
        return greaterThanAge;
    }

    @ApiMethod(description = "查询年龄大于")
    @ApiOperation("查询年龄大于")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "min", value = "5", example = "1", required = true),
    })

    @PostMapping("/users/u")
    public Integer updateNameById(@RequestParam Long id, @RequestParam String name) {
        Integer integer = userService.updateNameById(id, name);
        return integer;
    }
}
