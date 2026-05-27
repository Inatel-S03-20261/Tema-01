package com.inatel.auth_service.mapper;

import com.inatel.auth_service.dto.UserRegisterDTO;
import com.inatel.auth_service.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRegisterDTO dto);
}
