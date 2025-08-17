package com.example.portal.repo;

import com.example.portal.model.Document; import com.example.portal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
  List<Document> findByOwner(User owner);
}
