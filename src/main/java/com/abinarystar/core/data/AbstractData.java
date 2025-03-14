package com.abinarystar.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.Instant;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractData {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(length = 36)
  private String id;

  @CreatedDate
  @Column(updatable = false)
  private Instant createdDate;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @LastModifiedDate
  private Instant updatedDate;

  @LastModifiedBy
  private String updatedBy;

  @Version
  private Long version;

  @ColumnDefault("false")
  private Boolean isDeleted = Boolean.FALSE;
}
