package com.triple.board.user.service;

import com.triple.board.config.ResponseError;
import com.triple.board.exception.UserNotFoundException;
import com.triple.board.notice.dto.ResponseNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.repository.NoticeRepository;
import com.triple.board.user.dto.AddUserDto;
import com.triple.board.user.dto.ResponseUserDto;
import com.triple.board.user.dto.UpdateUserDto;
import com.triple.board.user.entity.User;
import com.triple.board.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final NoticeRepository noticeRepository;
  public ResponseEntity<?> addUser(AddUserDto addUserDto, Errors errors) {

    List<ResponseError> responseErrors = new ArrayList<>();
    if (errors.hasErrors()) {

      errors.getAllErrors().forEach(e -> {
        responseErrors.add(ResponseError.of(e));
      });
      return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
    }

    User user = User.builder()
        .email(addUserDto.getEmail())
        .userName(addUserDto.getUserName())
        .password(addUserDto.getPassword())
        .phone(addUserDto.getPhone())
        .regDate(LocalDateTime.now())
        .build();
    userRepository.save(user);

    return ResponseEntity.ok().build();
  }

  public ResponseEntity<?> updateUser(Long id, UpdateUserDto updateUserDto, Errors errors) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    user.setPhone(updateUserDto.getPhone());
    user.setUpdateDate(LocalDateTime.now());
    userRepository.save(user);

    return ResponseEntity.ok().build();
  }

  public ResponseUserDto getUser(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    return ResponseUserDto.of(user);
  }

  public List<ResponseNoticeDto> getUserNotice(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    List<Notice> noticeList = noticeRepository.findByUser(user);

    List<ResponseNoticeDto> responseNoticeDtoList = new ArrayList<>();

    noticeList.stream().forEach((e) -> {
      responseNoticeDtoList.add(ResponseNoticeDto.of(e));
    });


    return responseNoticeDtoList;
  }
}
