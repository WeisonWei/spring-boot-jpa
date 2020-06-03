package com.weison.sbj.model;

import com.weison.sbj.entity.Account;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

@ApiModel(description = "用户交易")
@ApiObject
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class Transaction {

    @ApiObjectField(description = "账户类型")
    private Account.AccountType accountType;
    @ApiObjectField(description = "金额")
    private Float amount;
    @ApiObjectField(description = "交易状态")
    private STATUS status;
    @ApiObjectField(description = "交易类型")
    private Account.TransType transType;
    @ApiObjectField(description = "用户ID")
    private Long uid;


    /**
     * 交易状态
     */
    public enum STATUS {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 完成
         */
        COMPLETE,
        /**
         * 失败
         */
        FAILED
    }

}
