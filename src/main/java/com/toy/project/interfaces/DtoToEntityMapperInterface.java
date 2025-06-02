package com.toy.project.interfaces;

public interface DtoToEntityMapperInterface<E, D> {
    E toEntity(D dto);
}

