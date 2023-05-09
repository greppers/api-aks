// For more information, see section 2.6.11 of https://docs.jboss.org/hibernate/orm/5.0/userguide/html_single/Hibernate_User_Guide.html
package tn.esprit.pidev.Entities;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "articles")
    private Set<User> authors;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "article_keywords", 
	       joinColumns = @JoinColumn(name =  "article_id"),
	       inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private Set<Keyword> keywords;
    
    @NotBlank
    @Lob
    private String content;

    private LocalDate published;
}
