package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.RolDTO;
import com.sw2parcial2.biblioerpms.entity.Rol;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {

    RolDTO toDTO(Rol entity);

    List<RolDTO> toDTO(List<Rol> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    Rol toEntity(RolDTO dto);
}
