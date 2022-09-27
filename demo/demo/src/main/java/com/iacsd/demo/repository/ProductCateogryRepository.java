package com.iacsd.demo.repository;

import com.iacsd.demo.model.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductCateogryRepository extends CrudRepository<ProductCategory, Long> {

	List<ProductCategory> findByIsDeletedFalse();
	
	//Page<ProductCategory> findAll(Pageable pageable);
	Page<ProductCategory> findByAccountId(Long id ,Pageable pageable);             //ByAmit

	
	ProductCategory findByUid(String uid);
	
	@Query("SELECT COUNT(pc)>0 from ProductCategory pc inner join pc.account acc where pc.name =:productCategoryName and acc.id=:accId")
	Boolean isProductCategoryNameExist(String productCategoryName ,Long accId);

	List<ProductCategory> findByAccountId(Long id);
}
