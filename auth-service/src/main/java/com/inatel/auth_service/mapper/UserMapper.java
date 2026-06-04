package com.inatel.auth_service.mapper;

import com.inatel.auth_service.dto.UserRegisterDTO;
import com.inatel.auth_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true) // será gerado pelo JPA
    @Mapping(target = "role", ignore = true) // defina no service (ou mapeie se vier do DTO)
    @Mapping(target = "banned", ignore = true) // defina default no service
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserRegisterDTO dto);
}
