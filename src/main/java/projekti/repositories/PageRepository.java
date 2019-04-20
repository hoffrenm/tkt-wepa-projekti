/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.entities.Page;
import projekti.entities.Post;

/**
 *
 * @author Mika Hoffren
 */
public interface PageRepository extends JpaRepository<Page, Long> {

    Page findByCustomUrl(String url);

    List<Post> findAllPostsByCustomUrl(String url, Pageable pageable);

}
