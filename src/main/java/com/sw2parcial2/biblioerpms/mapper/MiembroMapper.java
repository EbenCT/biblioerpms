package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.MiembroDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateMiembroInput;
import com.sw2parcial2.biblioerpms.entity.Miembro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface MiembroMapper {

    MiembroDTO toDTO(Miembro entity);

    List<MiembroDTO> toDTO(List<Miembro> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "prestamos", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    Miembro toEntity(CreateMiembroInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "prestamos", ignore = true)
    @Mapping(target = "reservas", ignore = true)
    void updateEntity(CreateMiembroInput input, @MappingTarget Miembro entity);
}
