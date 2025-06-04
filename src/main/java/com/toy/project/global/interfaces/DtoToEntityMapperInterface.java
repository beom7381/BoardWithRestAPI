package com.toy.project.global.interfaces;

public interface DtoToEntityMapperInterface<E, D> {
    E toEntity(D dto);
}

