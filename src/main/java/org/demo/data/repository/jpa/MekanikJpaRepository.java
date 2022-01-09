package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.MekanikEntity;

/**
 * Repository : Mekanik.
 */
public interface MekanikJpaRepository extends PagingAndSortingRepository<MekanikEntity, String> {

}
