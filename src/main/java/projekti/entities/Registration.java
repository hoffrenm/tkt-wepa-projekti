package projekti.entities;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration extends AbstractPersistable<Long>  {

    @Size(min = 2, max = 50)
    private String name;
    
    @Size(min = 2, max = 50)
    private String username;
    
    @Size(min = 2, max = 20)
    private String password;
    
    @Size(min = 2, max = 15)
    private String customUrl;
}
