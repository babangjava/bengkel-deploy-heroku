package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.PenjualanDetailEntity;

/**
 * Repository : PenjualanDetail.
 */
public interface PenjualanDetailJpaRepository extends PagingAndSortingRepository<PenjualanDetailEntity, Integer> {

}
