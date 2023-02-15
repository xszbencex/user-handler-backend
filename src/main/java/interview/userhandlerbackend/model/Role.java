package interview.userhandlerbackend.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ADMIN, PUBLIC;

  public String getAuthority() {
    return name();
  }

}
