package com.example.portal.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="documents")
public class Document {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
  @Column(nullable=false) String filename;
  @Column(nullable=false) String storagePath;
  @ManyToOne(optional=false) @JoinColumn(name="owner_id") User owner;
  @Column(nullable=false) Instant uploadedAt = Instant.now();
  public Long getId(){return id;} public String getFilename(){return filename;} public String getStoragePath(){return storagePath;}
  public User getOwner(){return owner;} public Instant getUploadedAt(){return uploadedAt;}
  public void setFilename(String f){this.filename=f;} public void setStoragePath(String s){this.storagePath=s;} public void setOwner(User o){this.owner=o;}
}
