package interview.userhandlerbackend.controller;

import interview.userhandlerbackend.dto.UserDTO;
import interview.userhandlerbackend.dto.UserResponseDTO;
import interview.userhandlerbackend.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/authenticate")
  public String login(
      @RequestParam String username,
      @RequestParam String password) {
    return userService.signIn(username, password);
  }

  @PostMapping("/signUp")
  public String signup(@RequestBody UserDTO user) {
    return userService.signUp(user);
  }

  @DeleteMapping(value = "/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String delete(@PathVariable String username) {
    userService.delete(username);
    return username;
  }

  @GetMapping(value = "/info")
  @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
  public UserResponseDTO getUserInfo(HttpServletRequest req) {
    return userService.getUserInfo(req);
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
  public List<UserDTO> getAllUser() {
    return userService.getAllUser();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ROLE_CLIENT', 'ROLE_ADMIN')")
  public UserDTO getUserById(@PathVariable Integer id) {
    return userService.getUserById(id);
  }

}
