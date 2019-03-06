package exodia.service;

import exodia.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    DocumentServiceModel saveDocument(DocumentServiceModel userServiceModel);

    void removeDocumentById(String id);

    List<DocumentServiceModel> findAllDocuments();

    DocumentServiceModel findById(String id);
}
