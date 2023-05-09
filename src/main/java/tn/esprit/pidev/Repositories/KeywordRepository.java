package tn.esprit.pidev.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.pidev.Entities.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
        @Query("select kw from Keyword kw where kw.name=?1")
	public Keyword findByName(String name);
}
