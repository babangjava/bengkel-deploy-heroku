package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.PenjualanHeaderEntity;

/**
 * Repository : PenjualanHeader.
 */
public interface PenjualanHeaderJpaRepository extends PagingAndSortingRepository<PenjualanHeaderEntity, Integer> {

}
