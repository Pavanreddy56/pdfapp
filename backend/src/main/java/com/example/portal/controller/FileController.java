package com.example.portal.controller;

import com.example.portal.model.Document; import com.example.portal.model.User; import com.example.portal.repo.DocumentRepository; import com.example.portal.service.FileService;
import org.springframework.core.io.FileSystemResource; import org.springframework.core.io.Resource; import org.springframework.http.*; import org.springframework.security.core.annotation.AuthenticationPrincipal; import org.springframework.web.bind.annotation.*; import org.springframework.web.multipart.MultipartFile;
import java.io.File; import java.io.IOException; import java.util.List;

@RestController @RequestMapping("/api/files")
public class FileController {
  private final FileService files; private final DocumentRepository repo;
  public FileController(FileService files, DocumentRepository repo){ this.files=files; this.repo=repo; }
  @PostMapping(value="/upload", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Document> upload(@AuthenticationPrincipal User me, @RequestPart("file") MultipartFile file) throws IOException {
    return ResponseEntity.ok(files.upload(me, file));
  }
  @GetMapping
  public List<Document> myFiles(@AuthenticationPrincipal User me){ return files.list(me); }
  @GetMapping("/{id}")
  public ResponseEntity<Resource> download(@AuthenticationPrincipal User me, @PathVariable Long id){
    Document d = repo.findById(id).filter(doc -> doc.getOwner().getId().equals(me.getId())).orElse(null);
    if(d==null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    File f = new File(d.getStoragePath());
    if(!f.exists()) return ResponseEntity.notFound().build();
    FileSystemResource r = new FileSystemResource(f);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+d.getFilename())
        .contentType(MediaType.APPLICATION_PDF)
        .body(r);
  }
}
