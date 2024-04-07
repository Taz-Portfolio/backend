package io.github.taz03.models;

import java.util.List;

public record Info(
    String name,
    String title,
    String resume,
    List<Connection> connections
) {}
