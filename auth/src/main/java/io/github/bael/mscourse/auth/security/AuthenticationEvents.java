package io.github.bael.mscourse.auth.security;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvents {

        Counter successCounter = Metrics.counter("successful_login_count");
        Counter failureCounter = Metrics.counter("failure_login_count");

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
                successCounter.increment();
        // ...
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
            failureCounter.increment();
//            System.out.println(event.getAuthentication().getCredentials());
        // ...
    }

}
