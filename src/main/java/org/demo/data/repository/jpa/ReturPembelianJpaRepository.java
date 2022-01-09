package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.ReturPembelianEntity;

/**
 * Repository : ReturPembelian.
 */
public interface ReturPembelianJpaRepository extends PagingAndSortingRepository<ReturPembelianEntity, Integer> {

}
