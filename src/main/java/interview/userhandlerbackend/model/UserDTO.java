package interview.userhandlerbackend.model;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

  private Integer id;
  private String username;
  private String email;
  private String password;
  List<Role> roles;
  private Boolean active;
  private String address;
  private String created;
  private Instant createdAt;
  private String deleted;
  private Instant deletedAt;
  private String deletedFlag;
  private String emailToken;
  private Instant lastLogin;
  private String name;
  private String nextLoginChangePwd;
  private Boolean passwordExpired;
  private String phone;
  private String settlementId;
  private String tempPassword;
  private String tempPasswordExpired;
  private String updated;
  private Instant updatedAt;
  private String userType;
  private String settlementsBySettlementId;
  private String userByCreatedId;
  private String userByDeletedId;
  private String userByUpdatedId;
}
