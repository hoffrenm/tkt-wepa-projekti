/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Mika Hoffren
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account extends AbstractPersistable<Long> {

    // TODO Accountista erillinen loginiin käytettetävä entiteetti?
    private String name;
    private String username;
    private String password;
    private String customUrl;

    @OneToOne
    private Image profilePicture;

    @OneToMany
    private List<Image> album;

    @OneToOne
    private Page page;

    @ManyToMany
    private List<Account> friends = new ArrayList<>();
    
    @ManyToMany
    List<Account> requests = new ArrayList<>();

}
