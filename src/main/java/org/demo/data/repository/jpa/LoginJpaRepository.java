package org.demo.data.repository.jpa;

import org.demo.bean.jpa.LoginEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository : Login.
 */
public interface LoginJpaRepository extends PagingAndSortingRepository<LoginEntity, String> {
    LoginEntity findByUserName(String username);
}
