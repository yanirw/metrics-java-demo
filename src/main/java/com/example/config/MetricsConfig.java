package com.example.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Component
public class MetricsConfig {

    private final AtomicInteger customGaugeValue = new AtomicInteger(0);
    
    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    public Counter customRequestCounter(MeterRegistry meterRegistry) {
        return Counter.builder("custom_requests_total")
                .description("Total number of custom requests")
                .tag("application", "hello-world-metrics")
                .register(meterRegistry);
    }

    @Bean
    public Timer customRequestTimer(MeterRegistry meterRegistry) {
        return Timer.builder("custom_request_duration_seconds")
                .description("Duration of custom requests")
                .tag("application", "hello-world-metrics")
                .register(meterRegistry);
    }

    @PostConstruct
    public void initializeGauge() {
        Gauge.builder("custom_gauge_value", customGaugeValue, AtomicInteger::get)
                .description("A custom gauge metric")
                .tag("application", "hello-world-metrics")
                .register(meterRegistry);
    }

    public void incrementGaugeValue() {
        customGaugeValue.incrementAndGet();
    }

    public void decrementGaugeValue() {
        customGaugeValue.decrementAndGet();
    }
} 