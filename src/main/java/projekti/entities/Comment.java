/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.entities;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Comment extends AbstractPersistable<Long> {

    private String poster;
    private String content;
    private LocalDateTime time;

}
