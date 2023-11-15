package com.triple.board.user.dto;

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
public class UserPasswordDto {

  @NotBlank(message = "현재 비밀번호는 필수 항목 입니다.")
  private String password;

  @Size(min= 4, message = "비밀번호는 4자 이상 입력해 주세요.")
  @NotBlank(message = "신규 비밀번호는 필수 항목 입니다.")
  private String newPassword;

}
