package hu.garaba.webshop.entity;

import java.math.BigDecimal;

public record Item(int id, String name, BigDecimal price) { }