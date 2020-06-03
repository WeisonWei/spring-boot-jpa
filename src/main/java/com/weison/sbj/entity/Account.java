package com.weison.sbj.entity;

import com.weison.sbj.util.DataUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jsondoc.core.annotation.ApiObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Optional;

@ApiObject
@Table(name = "t_account", indexes = @Index(name = "idx_user_type", columnList = "user_id,account_type"))
@Entity
@Data
@NoArgsConstructor
@DynamicInsert(true)
@DynamicUpdate(true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class Account extends BaseEntity {

    @ApiModelProperty("用户ID")
    @Column(name = "user_id", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long userId = null;

    @ApiModelProperty("账户类型")
    @Column(name = "account_type", nullable = true, columnDefinition = BaseEntity.INT_DEFAULT_0)
    private AccountType accountType = null;

    @ApiModelProperty("交易类型")
    @Column(name = "trans_type", nullable = true, columnDefinition = BaseEntity.INT_DEFAULT_0)
    private Account.TransType transType;

    @ApiModelProperty("金额")
    @Column(name = "amount", nullable = true, columnDefinition = BaseEntity.DECIMAL_DEFAULT_0)
    private Float amount = null;

    @ApiModelProperty("余额")
    @Column(name = "balance", nullable = true, columnDefinition = BaseEntity.DECIMAL_DEFAULT_0)
    private Float balance = null;

    /**
     * 账户类型
     */
    @ApiModel(description = "账户类型")
    public enum AccountType {
        /**
         * 默认
         */
        INIT,
        /**
         * 现金
         */
        CASH,
        /**
         * 积分
         */
        POINTS
    }

    /**
     * 交易类型: 用户发起的交易行为
     */
    public enum TransType {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 增加
         */
        ADD,
        /**
         * 兑换
         */
        EXCHANGE
    }

    public Account newAddAccount(Float amount, TransType transType) {
        return Optional.ofNullable(amount)
                .map(floatAmount -> this.newAccount(floatAmount, transType, true))
                .orElseGet(this::newEmptyAccount);
    }

    public Account newSubtractAccount(Float amount, TransType transType) {
        return Optional.ofNullable(amount)
                .map(floatAmount -> this.newAccount(floatAmount, transType, false))
                .orElseGet(this::newEmptyAccount);
    }

    public Account newAccount(Float amount, TransType transType, Boolean isAdd) {
        Account account = this.newEmptyAccount();
        float cashOrPointsBalance = isAdd ? DataUtils.floatAdd(this.balance, amount) :
                DataUtils.floatSubtract(this.balance, amount);
        account.setBalance(cashOrPointsBalance);
        account.setAmount(amount);
        account.setTransType(transType);
        return account;
    }

    public Account newEmptyAccount() {
        Account account = new Account();
        account.setAccountType(this.accountType);
        account.setUserId(this.userId);
        return account;
    }

    public boolean isBalanceEnough(Float amount) {
        return this.balance >= amount;
    }
}
