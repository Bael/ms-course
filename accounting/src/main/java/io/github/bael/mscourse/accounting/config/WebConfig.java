package io.github.bael.mscourse.accounting.config;


import io.github.bael.mscourse.accounting.stat.RequestCounterInterceptor;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public WebConfig(MeterRegistry meterRegistry) {
        super();
        this.meterRegistry = meterRegistry;
    }

    private final MeterRegistry meterRegistry;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new RequestCounterInterceptor(meterRegistry));
    }
}
