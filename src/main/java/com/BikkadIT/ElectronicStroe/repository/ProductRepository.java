package com.BikkadIT.ElectronicStroe.repository;
import com.BikkadIT.ElectronicStroe.model.Category;
import com.BikkadIT.ElectronicStroe.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<Product ,String> {

    //search
    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);


}
