package com.toy.project.base;

public interface EntityToDtoMapperInterface<E, D> {
    D toDto(E entity);
}
