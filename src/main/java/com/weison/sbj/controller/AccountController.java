package com.weison.sbj.controller;


import com.weison.sbj.entity.Account;
import com.weison.sbj.model.Transaction;
import com.weison.sbj.service.AccountService;
import org.jsondoc.core.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@Api(name = "AccountController", description = "")
@ApiVersion(since = "1.0", until = "2.0")
@ApiAuthNone
public class AccountController {

    @Resource
    private AccountService accountService;


    @ApiMethod(path = "/accounts", description = "更新用户账户信息",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @PutMapping("/accounts")
    public @ApiResponseObject
    ResponseEntity<Account> updateLastAccount(
            @ApiBodyObject(clazz = Transaction.class)
            @RequestBody Transaction transaction) {
        return ok(accountService.updateLastAccount(transaction));
    }

    @ApiMethod(description = "更新用户账户信息")
    @GetMapping("/accounts")
    public @ApiResponseObject
    ResponseEntity<Account> getAccount(
            @ApiQueryParam(name = "userId", description = "用户id")
            @RequestParam Long userId) {
        return ok(null);
    }

    @ApiMethod(description = "更新用户账户信息")
    @GetMapping("/accounts/{userId}")
    public @ApiResponseObject
    ResponseEntity<Account> getUserLastAccount(
            @ApiPathParam(name = "userId", description = "用户id")
            @PathVariable Long userId) {
        return ok(null);
    }

}
