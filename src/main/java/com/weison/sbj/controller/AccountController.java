package com.weison.sbj.controller;


import com.weison.sbj.entity.Account;
import com.weison.sbj.modle.Transaction;
import com.weison.sbj.service.AccountService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@Api(name = "AccountController", description = "")
public class AccountController {

    @Resource
    private AccountService accountService;

    @ApiMethod(description = "更新用户账户信息")
    @PostMapping("/accounts")
    public ResponseEntity<Account> updateLastAccount(
            @ApiQueryParam(name = "transaction", description = "transaction")
            @RequestBody Transaction transaction) {
        return ok(accountService.updateLastAccount(transaction));
    }

}
