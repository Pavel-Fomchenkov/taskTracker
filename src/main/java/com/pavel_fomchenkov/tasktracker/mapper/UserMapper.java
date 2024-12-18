package com.pavel_fomchenkov.tasktracker.mapper;

import com.pavel_fomchenkov.tasktracker.dto.UserDTO;
import com.pavel_fomchenkov.tasktracker.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Named("mapToUserDTO")
    UserDTO mapToUserDTO(User user);
}