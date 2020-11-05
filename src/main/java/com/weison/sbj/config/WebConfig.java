package com.weison.sbj.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component("WebConfig")
@Data
public class WebConfig {
    private String name = "123";
}
