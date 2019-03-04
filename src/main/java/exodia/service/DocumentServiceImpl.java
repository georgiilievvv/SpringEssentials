package exodia.service;

import exodia.domain.entities.Document;
import exodia.domain.models.service.DocumentServiceModel;
import exodia.repository.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService{

    private final DocumentRepository DocumentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentServiceImpl(DocumentRepository DocumentRepository, ModelMapper modelMapper) {
        this.DocumentRepository = DocumentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveDocument(DocumentServiceModel DocumentServiceModel) {
        Document Document = this.modelMapper.map(DocumentServiceModel, Document.class);

        return this.DocumentRepository
                .save(Document) != null;
    }

    @Override
    public void removeDocument(String id) {

    }

    @Override
    public List<DocumentServiceModel> findAllDocuments() {
        return this.DocumentRepository.findAll().stream()
                .map(d -> this.modelMapper.map(d, DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentServiceModel findById(String id) {
        return this.modelMapper.map(this.DocumentRepository.findById(id), DocumentServiceModel.class);
    }
}
