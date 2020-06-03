package com.weison.sbj.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;
import org.jsondoc.core.annotation.ApiObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@ApiObject
@ApiModel(description = "住址")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate(true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_address", indexes = @Index(name = "idx_province", columnList = "province"))
public class Address extends BaseEntity {

    @Builder.Default
    @ApiModelProperty("省份")
    @Column(name = "province", nullable = true, columnDefinition = VARCHAR_DEFAULT_0)
    private String province = null;

    @Builder.Default
    @ApiModelProperty("城市")
    @Column(name = "city", nullable = true, columnDefinition = VARCHAR_DEFAULT_0)
    private String city = null;


}
