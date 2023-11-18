package com.triple.board.user.repository;

import com.triple.board.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  int countByEmail(String email);

  Optional<User> findByIdAndPassword(long id, String password);

  Optional<User> findByUserNameAndPhone(String userName, String phone);

  Optional<User> findByEmail(String email);
}
