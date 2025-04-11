package com.fastForm.core.services;

import com.fastForm.core.Dto.LoginRequestDto;
import com.fastForm.core.Dto.LoginResponseDto;
import com.fastForm.core.Dto.ResponseDto;
import com.fastForm.core.Dto.UserDto;
import com.fastForm.core.Utils.JwtUtils;
import com.fastForm.core.entities.UserEntity;
import com.fastForm.core.exceptions.auth.AuthException;
import com.fastForm.core.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new AuthException(AuthException.AuthErrorTypeEnum.EMAIL_DUPLICATED, "Email já cadastrado.");

        if (!userDTO.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]$"))
            throw new AuthException(AuthException.AuthErrorTypeEnum.INVALID_CREDENTIALS,
                    "Senha inválida. A senha deve incluir letras maiúsculas, minúsculas, números e caracteres especiais.");

        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userRepository.save(userEntity);

        ResponseDto responseDto = new ResponseDto(userEntity, "Usuário cadastrado com sucesso!", true);
        return ResponseEntity.ok(responseDto);
    }

    public ResponseEntity<ResponseDto> login(LoginRequestDto loginRequestDto) {

        UserEntity userEntity = userRepository.findByEmail(loginRequestDto.email()).orElseThrow(
                () -> new AuthException(AuthException.AuthErrorTypeEnum.INVALID_CREDENTIALS, "Credenciais inválidas."));

        if (!BCrypt.checkpw(loginRequestDto.password(), userEntity.getPassword()))
            throw new AuthException(AuthException.AuthErrorTypeEnum.INVALID_CREDENTIALS, "Credenciais inválidas.");

        String jwtToken = JwtUtils.generateToken(userEntity.getId(), userEntity.getName(), userEntity.getEmail());

        LoginResponseDto loginResponseDto = new LoginResponseDto(jwtToken, userEntity.getName(), userEntity.getEmail());

        ResponseDto responseDto = new ResponseDto(loginResponseDto, "Login realizado com sucesso!", true);
        return ResponseEntity.ok(responseDto);
    }

}
