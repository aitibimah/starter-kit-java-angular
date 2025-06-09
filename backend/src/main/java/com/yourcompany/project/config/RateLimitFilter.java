package com.yourcompany.project.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, io.github.bucket4j.Bucket> rateLimitBuckets;

    public RateLimitFilter(Map<String, io.github.bucket4j.Bucket> rateLimitBuckets) {
        this.rateLimitBuckets = rateLimitBuckets;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String clientIp = getClientIP(request);
        io.github.bucket4j.Bucket bucket = rateLimitBuckets.computeIfAbsent(clientIp, k -> createNewBucket());

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded");
        }
    }

    private String getClientIP(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }

    private io.github.bucket4j.Bucket createNewBucket() {
        return io.github.bucket4j.Bucket.builder()
                .addLimit(io.github.bucket4j.Bandwidth.simple(100, java.time.Duration.ofMinutes(1)))
                .build();
    }
}
