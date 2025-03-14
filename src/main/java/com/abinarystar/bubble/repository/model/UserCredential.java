package com.abinarystar.bubble.repository.model;

import com.abinarystar.bubble.enumeration.UserCredentialType;
import com.abinarystar.core.data.AbstractData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t_user_credential")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCredential extends AbstractData {

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(nullable = false, updatable = false)
  private User user;

  @Column(nullable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private UserCredentialType type;

  @Column(nullable = false, length = 100)
  private String hashedCredential;
}
