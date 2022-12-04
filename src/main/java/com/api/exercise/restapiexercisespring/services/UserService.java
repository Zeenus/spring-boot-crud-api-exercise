package com.api.exercise.restapiexercisespring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.api.exercise.restapiexercisespring.data.dtos.UserDTO;
import com.api.exercise.restapiexercisespring.data.repositories.UserRepository;
import com.api.exercise.restapiexercisespring.util.UserEntityUtils;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserEntityUtils userEntityUtils;

    public UserService(UserRepository userRepository, UserEntityUtils userEntityUtils) {
        this.userRepository = userRepository;
        this.userEntityUtils = userEntityUtils;
    }

    public List<UserDTO> getAllUsers() {
        return userEntityUtils
                .allEntitiesToDTOs(userRepository.findAll());
    }

    public UserDTO getUser(Long id) {
        var result = userRepository.findById(id);
        return result.isPresent() ? userEntityUtils.entityToDTO(result.get()) : new UserDTO();
    }

    public UserDTO addUser(UserDTO dto) {
        return userEntityUtils.entityToDTO(
                userRepository.save(
                        userEntityUtils.dtoToEntity(dto)));
    }

    public UserDTO updateUser(Long id, UserDTO dto){
        var user = userRepository.findById(id);
        if (user.isPresent()){
            user.get().setEmail(dto.getEmail());
            user.get().setUsername(dto.getUsername());
            user.get().setFirstName(dto.getFirstName());
            user.get().setLastName(dto.getLastName());

            return userEntityUtils.entityToDTO(                
                userRepository.save(user.get())
            );
        }

        return new UserDTO();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
