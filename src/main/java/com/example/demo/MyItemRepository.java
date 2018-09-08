package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface MyItemRepository extends JpaRepository<MyItem, Long> {

	@Query(
			value = "SELECT * FROM item it WHERE it.status=?1 OR it.status=?2",
			countQuery = "SELECT count(*) FROM item it WHERE it.status=?1 OR it.status=?2",
			nativeQuery = true)
	Page<MyItem> findTempByStatus(Long status, Long status1, Pageable pageable);
	
}
