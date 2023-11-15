package com.triple.board.notice.repository;

import com.triple.board.notice.entity.Notice;
import com.triple.board.notice.entity.NoticeLike;
import com.triple.board.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {

  List<NoticeLike> findByUser(User user);

}
