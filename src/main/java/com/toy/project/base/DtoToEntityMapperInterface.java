package com.toy.project.base;

public interface DtoToEntityMapperInterface<E, D> {
    E toEntity(D dto);
}

