package com.weison.sbj.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.weison.sbj.repository.UserAuditListener;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@ApiObject
@ApiModel(description = "用户")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate(true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_user", indexes = @Index(name = "idx_name", columnList = "name"))
@EntityListeners({AuditingEntityListener.class, UserAuditListener.class})
public class User extends BaseEntity {

    @Builder.Default
    @ApiObjectField(description ="姓名")
    @Column(name = "name", nullable = true, columnDefinition = VARCHAR_DEFAULT_0)
    private String name = null;

    @Builder.Default
    @ApiObjectField(description ="年龄")
    @Column(name = "age", nullable = true, columnDefinition = INT_DEFAULT_0)
    private Integer age = null;

    @Builder.Default
    @ApiObjectField(description ="性别")
    @Column(name = "sex", nullable = true, columnDefinition = INT_DEFAULT_0)
    private Sex sex = null;

    @Builder.Default
    @ApiObjectField(description ="生日")
    @Column(name = "birth_day", nullable = true, columnDefinition = DATETIME_DEFAULT_0)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate birthDay = LocalDate.now();

    @Builder.Default
    @ApiObjectField(description ="住址")
    @Column(name = "address_id", nullable = true, columnDefinition = BIGINT_DEFAULT_0)
    private Long addressId = null;

    @Builder.Default
    @ApiObjectField(description ="乐观锁字段")
    @Column(name = "version", nullable = true, columnDefinition = BIGINT_DEFAULT_0)
    @Version //乐观锁字段
    private Long version = null;

    public enum Sex {
        DEFAULT,
        MALE,
        FEMALE
    }
}
