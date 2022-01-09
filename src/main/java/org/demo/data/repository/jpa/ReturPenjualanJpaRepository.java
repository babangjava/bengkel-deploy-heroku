package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.ReturPenjualanEntity;

/**
 * Repository : ReturPenjualan.
 */
public interface ReturPenjualanJpaRepository extends PagingAndSortingRepository<ReturPenjualanEntity, Integer> {

}
