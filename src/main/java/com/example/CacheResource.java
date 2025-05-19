package com.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.cache.CacheResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/cache")
@ApplicationScoped
public class CacheResource {

    private final AtomicInteger computationCounter = new AtomicInteger(0);
    private final List<String> logs = new ArrayList<>();

    @GET
    @Path("/time")
    @Produces(MediaType.TEXT_PLAIN)
    @CacheResult(cacheName = "time-cache")
    public String getCurrentTime() throws InterruptedException {
        int count = computationCounter.incrementAndGet();
        Thread.sleep(2000); // Имитация тяжелой операции
        String logMessage = "Computed at: " + LocalDateTime.now() + " | Computation count: " + count;
        logs.add(logMessage);
        return logMessage;
    }

    @GET
    @Path("/logs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getLogs() {
        return logs;
    }
}