package com.example.portal.service;

import com.example.portal.model.Document; import com.example.portal.model.User; import com.example.portal.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException; import java.nio.file.*; import java.util.List;

@Service
public class FileService {
  private final DocumentRepository repo; private final Path storageRoot;
  public FileService(DocumentRepository repo, @Value("${app.storage.path}") String storagePath){
    this.repo=repo; this.storageRoot = Path.of(storagePath);
  }
  public Document upload(User owner, MultipartFile file) throws IOException {
    if(file.isEmpty()) throw new IOException("empty file");
    Files.createDirectories(storageRoot);
    Path target = storageRoot.resolve(System.currentTimeMillis()+"-"+file.getOriginalFilename());
    Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
    Document d = new Document(); d.setOwner(owner); d.setFilename(file.getOriginalFilename()); d.setStoragePath(target.toString());
    return repo.save(d);
  }
  public List<Document> list(User owner){ return repo.findByOwner(owner); }
}
