package tn.esprit.pidev.Entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Keyword implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToMany(mappedBy = "keywords")
    private Set<Article> articles;

    @Column(unique=true)
    private String name;
}
