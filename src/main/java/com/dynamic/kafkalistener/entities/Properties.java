package com.dynamic.kafkalistener.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "properties")
public class Properties extends BaseEntity implements Serializable {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "topic", nullable = false)
  private String topic;

  @Column(name = "concurrency", nullable = false)
  private int concurrency;

  @Column(name = "group_id", nullable = false)
  private String groupId;

  @Column(name = "auto_startup", nullable = false)
  private boolean autoStartup;

}
