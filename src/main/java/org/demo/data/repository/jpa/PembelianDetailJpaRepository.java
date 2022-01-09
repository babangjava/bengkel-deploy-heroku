package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.PembelianDetailEntity;

/**
 * Repository : PembelianDetail.
 */
public interface PembelianDetailJpaRepository extends PagingAndSortingRepository<PembelianDetailEntity, Integer> {

}
