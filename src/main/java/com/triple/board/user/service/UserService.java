package com.triple.board.user.service;

import com.triple.board.config.ResponseError;
import com.triple.board.exception.ExistsEmailException;
import com.triple.board.exception.PasswordNotMatchException;
import com.triple.board.exception.UserNotFoundException;
import com.triple.board.notice.dto.ResponseNoticeDto;
import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.entity.NoticeLike;
import com.triple.board.notice.repository.NoticeLikeRepository;
import com.triple.board.notice.repository.NoticeRepository;
import com.triple.board.user.dto.AddUserDto;
import com.triple.board.user.dto.FindUserDto;
import com.triple.board.user.dto.ResponseUserDto;
import com.triple.board.user.dto.UpdateUserDto;
import com.triple.board.user.dto.UserPasswordDto;
import com.triple.board.user.entity.User;
import com.triple.board.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final NoticeRepository noticeRepository;
  private final NoticeLikeRepository noticeLikeRepository;

  private String getEncryptPassword(String password) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(password);
  }

  public ResponseEntity<?> addUser(AddUserDto addUserDto, Errors errors) {

    if (errors.hasErrors()) {
      List<ResponseError> responseErrors = new ArrayList<>();
      errors.getAllErrors().forEach(e -> {
        responseErrors.add(ResponseError.of(e));
      });
      return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
    }

    if (userRepository.countByEmail(addUserDto.getEmail()) > 0) {
      throw new ExistsEmailException("이미 가입된 이메일 입니다.");
    }

    String encryptPassword = getEncryptPassword(addUserDto.getPassword());

    User user = User.builder()
        .email(addUserDto.getEmail())
        .userName(addUserDto.getUserName())
        .password(encryptPassword)
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

  public ResponseEntity<?> findUser(FindUserDto findUserDto) {

    User user = userRepository.findByUserNameAndPhone(findUserDto.getUserName(), findUserDto.getPhone())
        .orElseThrow(() -> new UserNotFoundException("회원 정보가 없습니다."));

    ResponseUserDto responseUserDto = ResponseUserDto.of(user);
    return ResponseEntity.ok().body(responseUserDto);
  }

  public ResponseEntity<?> updateUserPassword(Long id, UserPasswordDto userPasswordDto, Errors errors) {

      if (errors.hasErrors()) {
        List<ResponseError> responseErrors = new ArrayList<>();
        errors.getAllErrors().forEach(e -> {
          responseErrors.add(ResponseError.of(e));
        });
        return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
      }

      User user = userRepository.findByIdAndPassword(id, userPasswordDto.getPassword())
          .orElseThrow(() -> new PasswordNotMatchException("비밀번호가 일치하지 않습니다."));

      user.setPassword(userPasswordDto.getNewPassword());
      userRepository.save(user);

      return ResponseEntity.ok().build();
  }

  private String getResetPassword(){
    return UUID.randomUUID().toString().replaceAll("-", "").substring(0,10);
  }

  void sendSMS(String message) {
    System.out.println("[문자메시지전송]");
    System.out.println(message);
  }

  public ResponseEntity<?> resetUserPassword(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("회원 정보가 없습니다."));

    String resetPassword = getResetPassword();
    String resetEncryptPassword = getEncryptPassword(resetPassword);
    user.setPassword(resetEncryptPassword);
    userRepository.save(user);

    String message = String.format("[%s] 님의 임시 비밀번호가 [%s]로 초기화 되었습니다."
        , user.getUserName(), resetPassword);
    sendSMS(message);

    return ResponseEntity.ok().build();
  }

  public ResponseEntity<?> deleteUser(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));


    try{
      userRepository.delete(user);
    } catch (DataIntegrityViolationException e) {
      String message = "작성한 글이 존재하여 회원 탈퇴가 불가능합니다.";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      String message = "회원 탈퇴 중 문제가 발생하였습니다.";
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok().build();
  }

  public List<NoticeLike> likeNotice(Long id) {

    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

    List<NoticeLike> noticeLikeList = noticeLikeRepository.findByUser(user);

    return noticeLikeList;
  }
}
