package org.etutoria.listingservice.repositories;
import org.etutoria.listingservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
