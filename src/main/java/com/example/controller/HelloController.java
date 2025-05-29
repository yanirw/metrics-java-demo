package com.example.controller;

import com.example.config.MetricsConfig;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class HelloController {

    @Autowired
    private Counter customRequestCounter;

    @Autowired
    private Timer customRequestTimer;

    @Autowired
    private MetricsConfig metricsConfig;

    @Autowired
    private MeterRegistry meterRegistry;

    @GetMapping("/")
    public String hello() {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            customRequestCounter.increment();
            return "Hello World!";
        } finally {
            sample.stop(customRequestTimer);
        }
    }

    @GetMapping("/hello")
    public String helloEndpoint() {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            customRequestCounter.increment();
            // Simulate some processing time
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(10, 100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Hello from Spring Boot with Metrics!";
        } finally {
            sample.stop(customRequestTimer);
        }
    }

    @PostMapping("/increment-gauge")
    public String incrementGauge() {
        customRequestCounter.increment();
        metricsConfig.incrementGaugeValue();
        return "Gauge incremented!";
    }

    @PostMapping("/decrement-gauge")
    public String decrementGauge() {
        customRequestCounter.increment();
        metricsConfig.decrementGaugeValue();
        return "Gauge decremented!";
    }

    @GetMapping("/metrics-demo")
    public String metricsDemo() {
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            customRequestCounter.increment();
            // Simulate variable processing time
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 200));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "This endpoint demonstrates custom metrics collection for GCP Monitoring!";
        } finally {
            sample.stop(customRequestTimer);
        }
    }
} 