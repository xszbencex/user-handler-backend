package interview.userhandlerbackend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"user\"")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
  @Column(unique = true, nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Size(min = 8, message = "Minimum password length: 8 characters")
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  List<Role> roles;

  private String active;

  private String address;

  private String created;

  private String createdAt;

  private String deleted;

  private String deletedAt;

  private String deletedFlag;

  private String emailToken;

  private String lastLogin;

  private String name;

  private String nextLoginChangePwd;

  private String passwordExpired;

  private String phone;

  private String settlementId;

  private String tempPassword;

  private String tempPasswordExpired;

  private String updated;

  private String updatedAt;

  private String userType;

  private String settlementsBySettlementId;

  private String userByCreatedId;

  private String userByDeletedId;

  private String userByUpdatedId;
}
