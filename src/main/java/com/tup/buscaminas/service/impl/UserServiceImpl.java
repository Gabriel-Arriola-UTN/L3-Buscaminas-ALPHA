package com.tup.buscaminas.service.impl;

import com.tup.buscaminas.entity.User;
import com.tup.buscaminas.repository.GameRepository;
import com.tup.buscaminas.repository.UserRepository;
import com.tup.buscaminas.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public User createUser(String username, String password) {
        //TODO: crear un nuevo usuario, ver la clase User para validar los campos

         return null;
    }

    @Override
    public User getUser(Long userId) {
        //TODO: retornar un usuario buscandolo por id, en caso de no existir arrojar una excepcion
        // TIP: metodo .orElseThrow(() -> new RuntimeException("mensaje"))
        
        return null;
    }

    @Override
    public User updateUser(Long userId, String username, String password) {
        //TODO: actualizar un usuario (username y password) si existe el id
        //validar la existencia del usuario
        //validar la disponibilidad del username
        //arrojar excepcion en caso de no cumplir las validaciones

        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        //TODO: eliminar un usuario por su numero de id
        //validar la existencia del usuario
        //validar que no existan juegos asociados al usuario
        //arrojar excepcion en caso de no cumplir las validaciones

    }
}
