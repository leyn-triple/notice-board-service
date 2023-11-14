package com.triple.board.notice.repository;

import com.triple.board.notice.entity.Notice;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

  int countByTitleAndContentsAndRegDateIsGreaterThanEqual(String title, String contents, LocalDateTime regDate);

}
