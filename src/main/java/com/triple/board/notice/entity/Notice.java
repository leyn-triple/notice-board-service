package com.triple.board.notice.entity;

import com.triple.board.user.entity.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Notice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn
  private User user;

  @Column
  private String title;

  @Column
  private String contents;

  @Column
  private LocalDateTime regDate;

  @Column
  private LocalDateTime updateDate;

  @Column
  private LocalDateTime deleteDate;

  @Column
  @ColumnDefault("false")
  private boolean deleted;

  @Column
  private long likes;

  @Column
  private long hits;

}
