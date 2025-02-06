package muit.backend.repository;

import muit.backend.domain.entity.member.Post;
import muit.backend.domain.enums.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //익명 + 핫게용
    Page<Post> findAllByPostTypeAndTitleContaining(PostType postType, String title, PageRequest pageRequest);

    @Query("SELECT p.post FROM PostLikes p GROUP BY p.post.id HAVING COUNT(p)>=2")
    Page<Post> findAllHot(@Param("title") String title, Pageable pageable);



    //분실물용
    Page<Post> findAllByPostTypeAndMusicalNameAndLostItemContainingAndLocationContaining(PostType postType, PageRequest of, String musicalName, String lostItem, String location);

    Page<Post> findAllByPostTypeAndMusicalNameAndLostItemContainingAndLocationContainingAndLostDateBetween(PostType postType, PageRequest of, String musicalName, String lostItem, String location, LocalDateTime startTime, LocalDateTime endTime);

    Page<Post> findAllByPostTypeAndLostItemContainingAndLocationContainingAndLostDateBetween(PostType postType, PageRequest of, String lostItem, String location, LocalDateTime startTime, LocalDateTime endTime);

    Page<Post> findAllByPostTypeAndLostItemContainingAndLocationContaining(PostType postType, PageRequest of, String lostItem, String location);

    //리뷰 용
    Page<Post> findAllByPostTypeAndMusicalNameAndLocationContaining(PostType postType, PageRequest of, String musicalName, String location);

    Page<Post> findAllByPostTypeAndLocationContaining(PostType postType, PageRequest of, String location);
}
