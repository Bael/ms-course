package io.github.bael.mscourse.accounting.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bael.mscourse.accounting.stat.RequestCounterInterceptor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
//@EnableWebMvc
public class WebAppConfig
//        implements WebMvcConfigurer
{


//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new StringHttpMessageConverter());
//
//        converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
//
//    }


//    public WebAppConfig(MeterRegistry meterRegistry) {
//        super();
//        this.meterRegistry = meterRegistry;
//    }


//    private final MeterRegistry meterRegistry;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
////        registry.addInterceptor(new RequestCounterInterceptor(meterRegistry));
//        registry.addInterceptor(new RequestCounterInterceptor(meterRegistry));
//    }
}
