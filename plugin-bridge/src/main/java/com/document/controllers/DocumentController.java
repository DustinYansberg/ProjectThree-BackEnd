package com.document.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.document.models.Document;
import com.document.services.DocumentService;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
	this.documentService = documentService;
    }

    @GetMapping("/getdocs/{id}")
    public ResponseEntity<String> getDocuments(@PathVariable String id) {
	System.out.println("I am hitting the getdocs route form the API Gateway");
	return documentService.getDocumentsByIdentity(id);
    }

    @PostMapping("/createdoc")
    public ResponseEntity<String> createDoc(@RequestBody Document document) {
	return documentService.createDoc(document);
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<String> completeDocument(@PathVariable int id) {
	return documentService.completeDoc(id);
    }
}
