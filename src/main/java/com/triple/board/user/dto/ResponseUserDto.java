package com.triple.board.user.dto;

import com.triple.board.user.entity.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseUserDto {

  private Long id;
  private String email;
  private String userName;
  private String phone;

  public static ResponseUserDto of(User user) {
    return ResponseUserDto.builder()
        .id(user.getId())
        .userName(user.getUserName())
        .phone(user.getPhone())
        .email(user.getEmail())
        .build();
  }
}
