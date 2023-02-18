package interview.userhandlerbackend.controller;

import interview.userhandlerbackend.model.UserDTO;
import interview.userhandlerbackend.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public void delete(@PathVariable Integer id) {
    userService.delete(id);
  }

  @GetMapping(value = "/info")
  @PreAuthorize("hasAnyAuthority('PUBLIC', 'ADMIN')")
  public UserDTO getUserInfo(HttpServletRequest req) {
    return userService.getUserInfo(req);
  }

  @GetMapping("/refreshToken")
  @PreAuthorize("hasAnyAuthority('PUBLIC', 'ADMIN')")
  public String refreshToken(HttpServletRequest req) {
    return userService.refreshToken(req.getRemoteUser());
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public List<UserDTO> getAllUser() {
    return userService.getAllUser();
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ADMIN')")
  public UserDTO updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
    return userService.updateUser(id, userDTO);
  }

}
