/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.repositories;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projekti.entities.Account;
import projekti.entities.Page;
import projekti.entities.Post;

/**
 *
 * @author Mika Hoffren
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByPage(Page page);

    Account findByUsername(String name);

    Account findByCustomUrl(String url);

    @Query(value = "SELECT * FROM ACCOUNT "
            + "WHERE UPPER(name) LIKE CONCAT('%',UPPER(:text),'%')",
            nativeQuery = true)
    List<Account> findAllBySearchCriteria(@Param("text") String text);
    
    List<Account> findByIdIn(Set<Long> ids);

}
