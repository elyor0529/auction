package by.auction.repository;

import by.auction.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for data management in a table "categories"
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{

    List<Category> findAll();

    Optional<Category> findByName(String name);

}
