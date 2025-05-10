package org.example.repository;

import org.example.entity.Term;
import org.example.entity.TermVersionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TermRepository
        extends JpaRepository<Term, TermVersionKey> {

    // title별로 최신 version을 가진 Term을 찾는 쿼리
    @Query("SELECT t FROM Term t WHERE t.termVersionKey.version = " +
            "(SELECT MAX(t2.termVersionKey.version) FROM Term t2 WHERE t2.termVersionKey.title = t.termVersionKey.title)")
    List<Term> findLatestTerms();
}
