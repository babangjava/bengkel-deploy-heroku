package org.demo.repository;

import org.demo.jpa.Login;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LoginJpaRepository extends PagingAndSortingRepository<Login, String> {
    Login findByUserName(String username);
}
