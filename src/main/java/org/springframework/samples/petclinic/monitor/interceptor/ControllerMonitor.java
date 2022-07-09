package org.springframework.samples.petclinic.monitor.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.SimpleTimer;
import io.prometheus.client.Summary;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerMonitor extends OncePerRequestFilter {
    private Summary requestLatency;

    public ControllerMonitor(CollectorRegistry defaultRegistry) {
        requestLatency = Summary.build().name("http_request_time")
                .help("http request in seconds")
                .labelNames("method", "url")
                .register(defaultRegistry);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        SimpleTimer simpleTimer = new SimpleTimer();
        ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
        double seconds = simpleTimer.elapsedSeconds();
        filterChain.doFilter(request, contentCachingResponseWrapper);
        //if (MediaType.APPLICATION_JSON_VALUE.equals(response.getContentType())) {
            requestLatency.labels(method, requestURI).observe(seconds);
        //}
        contentCachingResponseWrapper.copyBodyToResponse();
    }
}
