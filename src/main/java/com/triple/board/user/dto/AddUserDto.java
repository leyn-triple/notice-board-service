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
public class AddUserDto {

  @Email(message = "이메일 형식에 맞게 입력해 주세요.")
  @NotBlank(message = "이메일은 필수 항목 입니다.")
  private String email;

  @NotBlank(message = "이름은 필수 항목 입니다.")
  private String userName;

  @Size(min= 4, message = "비밀번호는 4자 이상 입력해 주세요.")
  @NotBlank(message = "비밀번호는 필수 항목 입니다.")
  private String password;

  @Size(min = 11, max = 11, message = "연락처는 11자리로 입력해 주세요. ex)01012345678")
  @NotBlank(message = "연락처는 필수 항목 입니다.")
  private String phone;

}
