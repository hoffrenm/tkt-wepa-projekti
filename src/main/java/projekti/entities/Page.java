/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page extends AbstractPersistable<Long> {

    @OneToOne
    private Account account;

    private String customUrl;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();
}
