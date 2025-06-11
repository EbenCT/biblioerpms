package com.sw2parcial2.biblioerpms.mapper;

import com.sw2parcial2.biblioerpms.dto.UsuarioDTO;
import com.sw2parcial2.biblioerpms.dto.input.CreateUsuarioInput;
import com.sw2parcial2.biblioerpms.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario entity);

    List<UsuarioDTO> toDTO(List<Usuario> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "miembros", ignore = true)
    Usuario toEntity(CreateUsuarioInput input);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "miembros", ignore = true)
    void updateEntity(CreateUsuarioInput input, @MappingTarget Usuario entity);
}
