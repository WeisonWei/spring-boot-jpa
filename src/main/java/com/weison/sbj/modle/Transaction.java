package com.weison.sbj.modle;

import com.weison.sbj.entity.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@ApiModel(description = "用户交易")
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class Transaction {

    @ApiModelProperty("账户类型")
    private Account.AccountType accountType;
    @ApiModelProperty("金额")
    private Float amount;
    @ApiModelProperty("交易状态")
    private STATUS status;
    @ApiModelProperty("交易类型")
    private Account.TransType transType;
    @ApiModelProperty("用户ID")
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
