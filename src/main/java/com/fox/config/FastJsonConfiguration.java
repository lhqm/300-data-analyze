package com.fox.config;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置解转用
 */
@Configuration
public class FastJsonConfiguration {

    @Bean
    public ParserConfig fastJsonParserConfig() {
        ParserConfig config = new ParserConfig();
        config.setAutoTypeSupport(true);
        return config;
    }
}