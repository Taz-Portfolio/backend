package io.github.taz03;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record PortfolioProperties(
    @Value("${portfolio.contentRepository}")
    String contentRepository
) {}
