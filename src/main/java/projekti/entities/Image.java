/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Mika Hoffren
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Image extends AbstractPersistable<Long> {

    private String name;
    private String description;
    private Long likes = 0L;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ElementCollection(targetClass = String.class)
    private List<String> likers = new ArrayList<>();

    private String contentType;
    private Long contentLength;

    @Lob
    private byte[] content;
}
