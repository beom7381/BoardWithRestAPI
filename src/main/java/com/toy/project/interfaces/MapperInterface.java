package com.toy.project.interfaces;

public interface MapperInterface<E, D> extends DtoToEntityMapperInterface<E, D>,
                                               EntityToDtoMapperInterface<E, D>
{
    E toEntity(D dto);
    D toDto(E entity);
}
