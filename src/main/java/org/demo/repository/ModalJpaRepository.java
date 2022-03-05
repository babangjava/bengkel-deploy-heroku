package org.demo.repository;

import org.demo.jpa.Login;
import org.demo.jpa.Modal;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ModalJpaRepository extends PagingAndSortingRepository<Modal, Integer> {
}
