package io.github.bael.mscourse.shop.stats;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class RequestCounterInterceptor extends HandlerInterceptorAdapter {

    private final MeterRegistry meterRegistry;

    public RequestCounterInterceptor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
            throws Exception {
        // Update counters
        String handlerLabel = handler.toString();
        // get short form of handler method name
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            handlerLabel = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        } else {
            handlerLabel = handler.getClass().getCanonicalName();
        }
        meterRegistry.counter("http_request_counter", "method", request.getMethod(), "handler",
                handlerLabel, "status", Integer.toString(response.getStatus())).increment();
    }
}
