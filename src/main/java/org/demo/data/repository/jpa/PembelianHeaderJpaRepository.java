package org.demo.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.PembelianHeaderEntity;

/**
 * Repository : PembelianHeader.
 */
public interface PembelianHeaderJpaRepository extends PagingAndSortingRepository<PembelianHeaderEntity, Integer> {

}
