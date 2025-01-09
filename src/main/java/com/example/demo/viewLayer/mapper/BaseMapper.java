package com.example.demo.viewLayer.mapper;

public interface BaseMapper <E, D> {

    D toDto(E e);

    E toEntity(D d);
}
