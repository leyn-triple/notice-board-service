package com.triple.board.notice.repository;

import com.triple.board.notice.entity.Notice;
import com.triple.board.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  int countByTitleAndContentsAndRegDateIsGreaterThanEqual(String title, String contents, LocalDateTime regDate);

  List<Notice> findByUser(User user);

}
