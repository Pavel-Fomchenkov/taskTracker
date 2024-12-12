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

//    @Mapping(target = "authorities", source = "role", qualifiedByName = "roleToAuthorities")
//    UserDetailsDTO mapToUserDetailsDTO(User user);
//
//    @Named("roleToAuthorities")
//    default Collection<? extends GrantedAuthority> roleToAuthorities(Role role) {
//        return Arrays.stream(role.name().split(", "))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
}