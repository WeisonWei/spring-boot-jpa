package com.weison.sbj.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

@ApiModel(description = "所有Entity的父类")
@Slf4j
@MappedSuperclass
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntity implements Serializable {

    public static final String INT_DEFAULT_0 = "int default 0";
    public static final String TINY_INT_DEFAULT_0 = "tinyint default 0";
    public static final String BIGINT_DEFAULT_0 = "bigint default 0";
    public static final String DECIMAL_DEFAULT_0 = "decimal default 0";
    public static final String VARCHAR_DEFAULT_0 = "varchar(255) default ''";
    public static final String VARCHAR_DEFAULT_20 = "varchar(20) default ''";
    public static final String VARCHAR_DEFAULT_50 = "varchar(50) default ''";
    public static final String DATETIME_DEFAULT_0 = "datetime default null";
    public static final String TEXT_DEFAULT = "text";

    @ApiModelProperty("ID")
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;

    @ApiModelProperty("创建人")
    @CreatedBy
    @Column(name = "cid", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long cid;

    @ApiModelProperty("修改人")
    @LastModifiedBy
    @Column(name = "mid", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long mid;

    @ApiModelProperty("创建时间")
    @CreatedDate
    @Column(name = "ctime", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long ctime;

    @ApiModelProperty("修改时间")
    @LastModifiedDate
    @Column(name = "utime", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long utime;

    @ApiModelProperty("软删除")
    @Deprecated
    @Column(name = "del", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long del;

    public BaseEntity delete() {
        setDel(System.currentTimeMillis());
        return this;
    }

    public BaseEntity insert() {
        setCtime(System.currentTimeMillis());
        return this;
    }

    public BaseEntity update() {
        setUtime(System.currentTimeMillis());
        return this;
    }

    @Deprecated
    public <T extends BaseEntity> T toEntity() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) genericSuperclass;
        return toEntity(clazz);
    }

    public <T extends BaseEntity> T toModel() {
        String modelName = this.getClass().getName().replace(".entity.", ".model.");
        try {
            Class<T> modelClass = null;
            modelClass = (Class<T>) Class.forName(modelName);
            return toEntity(modelClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return toEntity();
    }

    public <T> T toEntity(Class<T> cls) {
        return clone(cls);
    }

    public <T> T append(Object append) {
        BeanUtils.copyProperties(append, this, getNullPropertyNames(append));
        return (T) this;
    }

    private <T> T clone(Class<T> cls) {
        if (this.getClass().equals(cls)) {
            return (T) this;
        }
        T target = null;
        try {
            target = cls.newInstance();
        } catch (Exception e) {
            target = null;
        }
        BeanUtils.copyProperties(this, target);
        return target;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
