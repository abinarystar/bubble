package com.abinarystar.bubble.repository.model;

import com.abinarystar.core.data.AbstractData;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(
    name = "t_user_email",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "email"})
)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEmail extends AbstractData {

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(nullable = false, updatable = false)
  private User user;

  @Column(nullable = false, updatable = false, length = 320)
  private String email;

  @ColumnDefault("false")
  @Builder.Default
  private Boolean isVerified = Boolean.FALSE;
}
