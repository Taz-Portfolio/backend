package io.github.taz03.models;

import java.util.List;

public record Info(
    String name,
    String title,
    List<Connection> connections
) {}
