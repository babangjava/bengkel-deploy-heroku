package org.demo.repository;

import org.demo.jpa.Login;
import org.demo.jpa.SekemaDetail;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SekemaDetailJpaRepository extends PagingAndSortingRepository<SekemaDetail, Integer> {
}
