package com.toy.project.global.interfaces;

public interface EntityToDtoMapperInterface<E, D> {
    D toDto(E entity);
}
