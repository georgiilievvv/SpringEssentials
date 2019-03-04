package exodia.service;

import exodia.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {

    boolean saveDocument(DocumentServiceModel userServiceModel);

    void removeDocument(String id);

    List<DocumentServiceModel> findAllDocuments();

    DocumentServiceModel findById(String id);
}
