package kz.satbayev.university.repository;

import kz.satbayev.university.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BooksRepository extends JpaRepository<Books, Long> {
}
