package org.demo.data.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.demo.bean.jpa.BarangEntity;
import org.springframework.data.repository.query.Param;

/**
 * Repository : Barang.
 */
public interface BarangJpaRepository extends PagingAndSortingRepository<BarangEntity, String> {
    @Query(value = "select t from BarangEntity t where LOWER(t.kodeBarang) like LOWER(CONCAT('%',:param,'%')) OR LOWER(t.namaBarang) like LOWER(CONCAT('%',:param,'%')) OR LOWER(t.harga) like LOWER(CONCAT('%',:param,'%')) ")
    Page<BarangEntity> getPagging(@Param("param")String param, Pageable pageable);
}
