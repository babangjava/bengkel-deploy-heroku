package org.demo.repository;

import org.demo.jpa.Penjualan;
import org.demo.jpa.Sekema;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PenjualanJpaRepository extends PagingAndSortingRepository<Penjualan, Integer> {
}
