package org.demo.repository;

import org.demo.jpa.Login;
import org.demo.jpa.Sekema;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SekemaJpaRepository extends PagingAndSortingRepository<Sekema, Integer> {
}
