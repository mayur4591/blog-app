package com.example.blog_app.services.impl;

import com.example.blog_app.entities.User;
import com.example.blog_app.exceptions.ResourceNotFoundException;
import com.example.blog_app.payloads.UserDto;
import com.example.blog_app.repository.UserRepo;
import com.example.blog_app.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        user.setPassword(encoder.encode(userDto.getPassword()));
        User savedUser = userRepo.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(encoder.encode(userDto.getPassword()));

        User updatedUser = userRepo.save(user);
        UserDto userDto1 = userToDto(updatedUser);

        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream()
                .map(user -> this.userToDto(user))
                .collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));

        userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto)
    {
        User user = modelMapper.map(userDto,User.class);

        return user;
    }

    public UserDto userToDto(User user)
    {
        UserDto userDto = modelMapper.map(user,UserDto.class);

        return userDto;
    }


}
