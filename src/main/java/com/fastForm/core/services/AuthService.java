package com.fastForm.core.services;

import com.fastForm.core.Dto.ResponseDto;
import com.fastForm.core.Dto.UserDto;
import com.fastForm.core.entities.UserEntity;
import com.fastForm.core.exceptions.auth.AuthException;
import com.fastForm.core.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepository;

    @Autowired
    private AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ResponseDto> register(UserDto userDTO) {
        if (userDTO.getPassword().length() < 8)
            throw new AuthException(AuthException.AuthErrorTypeEnum.WEAK_PASSWORD, "A senha deve ter pelo menos 8 caracteres.");

        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new AuthException(AuthException.AuthErrorTypeEnum.EMAIL_DUPLICATED, "Email já cadastrado.");

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userRepository.save(userEntity);

        ResponseDto responseDto = new ResponseDto("Usuário cadastrado com sucesso!");
        responseDto.setData(userEntity);
        responseDto.setSuccess(true);

        return ResponseEntity.ok(responseDto);
    }

}
