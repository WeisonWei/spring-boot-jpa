package com.weison.sbj.controller;

import com.weison.sbj.config.WebConfig;
import com.weison.sbj.util.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;

/**
 * @author weixiaoxing
 * @date 2020/10/22
 */
@Api(name = "BeanController", description = "")
@RequestMapping("/v1")
@RestController
@Slf4j
public class BeanController {

    @Autowired
    WebConfig webConfig;

    @PostMapping("/beans/reFresh")
    @ApiMethod(description = "更新bean")
    public String reFresh(
            @ApiQueryParam(name = "beanName", description = "购买用户")
            @RequestParam("beanName") String beanName,
            @ApiQueryParam(name = "keyValue", description = "字段和值")
            @RequestParam("keyValue") String keyValue) {

        log.info("--1->" + webConfig.toString());

        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        Object bean = applicationContext.getBean(beanName);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        log.info("--3->" + beanDefinitionNames);

        String[] split = keyValue.split("\\|");
        String key = split[0];
        String value = split[1];
        // 拿到bean的Class对象
        Class<?> beanType = applicationContext.getType(beanName);
        if (beanType == null) {
            return "error bean name";
        }

        // 找到bean需要更新的字段
        Field[] declaredFields = beanType.getDeclaredFields();
        Arrays.stream(declaredFields)
                .filter(field -> field.getName().equals(key))
                .findFirst()
                .ifPresent(field -> setFieldData(field, bean, value));
        log.info("--2->" + webConfig.toString());
        return "ok";
    }

    @GetMapping("/beans")
    @ApiMethod(description = "获取bean")
    public String get(
            @ApiQueryParam(name = "beanName", description = "购买用户")
            @RequestParam("beanName") String beanName) {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        Object bean = applicationContext.getBean(beanName);
        log.info("--3->" + webConfig.toString());
        return webConfig.toString();
    }

    @SneakyThrows
    private void setFieldData(Field field, Object bean, String data) {
        // 注意这里要设置权限为true
        field.setAccessible(true);
        Class<?> type = field.getType();
        if (type.equals(String.class)) {
            field.set(bean, data);
        } else if (type.equals(Integer.class)) {
            field.set(bean, Integer.valueOf(data));
        } else if (type.equals(Long.class)) {
            field.set(bean, Long.valueOf(data));
        } else if (type.equals(Double.class)) {
            field.set(bean, Double.valueOf(data));
        } else if (type.equals(Short.class)) {
            field.set(bean, Short.valueOf(data));
        } else if (type.equals(Byte.class)) {
            field.set(bean, Byte.valueOf(data));
        } else if (type.equals(Boolean.class)) {
            field.set(bean, Boolean.valueOf(data));
        } else if (type.equals(Date.class)) {
            field.set(bean, new Date(Long.valueOf(data)));
        }
    }
}
