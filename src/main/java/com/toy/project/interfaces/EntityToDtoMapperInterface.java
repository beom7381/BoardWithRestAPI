package com.toy.project.interfaces;

public interface EntityToDtoMapperInterface<E, D> {
    D toDto(E entity);
}
