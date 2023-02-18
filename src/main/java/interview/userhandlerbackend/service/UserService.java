package interview.userhandlerbackend.service;

import interview.userhandlerbackend.model.UserDTO;
import interview.userhandlerbackend.exception.CustomException;
import interview.userhandlerbackend.model.Role;
import interview.userhandlerbackend.model.User;
import interview.userhandlerbackend.repository.UserRepository;
import interview.userhandlerbackend.security.JwtTokenProvider;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<UserDTO, User> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder,
                     JwtTokenProvider jwtTokenProvider,
                     AuthenticationManager authenticationManager) {
    super(UserDTO.class, User.class);
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  public String signIn(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signUp(UserDTO userDTO) {
    final User user = mapFromDTO(userDTO);
    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      if (user.getRoles() == null || user.getRoles().isEmpty()) {
        user.setRoles(new ArrayList<>(Collections.singletonList(Role.PUBLIC)));
      }
      user.setActive(true);
      user.setCreatedAt(Instant.now());
      userRepository.save(user);
      return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    } else {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public void delete(Integer id) {
    userRepository.deleteById(id);
  }

  public UserDTO getUserInfo(HttpServletRequest req) {
    return mapToDTO(userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))));
  }

  public String refreshToken(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
  }

  public List<UserDTO> getAllUser() {
    return userRepository.findAll().stream().map(super::mapToDTO).collect(Collectors.toList());
  }

  public UserDTO updateUser(Integer id, UserDTO userDTO) {
    final Optional<User> user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new CustomException("User not found with id: " + id, HttpStatus.NOT_FOUND);
    }
    final User updatedUser = mapFromDTO(userDTO);
    updatedUser.setId(user.get().getId());
    updatedUser.setEmail(user.get().getEmail());
    updatedUser.setPassword(user.get().getPassword());
    updatedUser.setUsername(user.get().getUsername());
    updatedUser.setUpdatedAt(Instant.now());
    return super.mapToDTO(this.userRepository.save(updatedUser));
  }
}
