/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.entities.Page;
import projekti.entities.Post;

/**
 *
 * @author Mika Hoffren
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM Post "
            + "JOIN Page_Posts ON Page_Posts.posts_id = Post.id "
            + "JOIN Page ON Page.id = Page_Posts.page_id "
            + "WHERE Page.custom_url = :url "
            + "ORDER BY time DESC "
            + "LIMIT 25",
            nativeQuery = true)
    List<Post> find25NewestPostsForPage(@Param("url") String url);
}
