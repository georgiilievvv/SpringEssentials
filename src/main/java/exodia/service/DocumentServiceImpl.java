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
    public DocumentServiceModel saveDocument(DocumentServiceModel model) {
        Document Document = this.modelMapper.map(model, Document.class);

        try {
            Document document = this.DocumentRepository.saveAndFlush(Document);
            return this.modelMapper.map(document, DocumentServiceModel.class);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void removeDocumentById(String id) {
            this.DocumentRepository.deleteById(id);
    }

    @Override
    public List<DocumentServiceModel> findAllDocuments() {
        return this.DocumentRepository.findAll().stream()
                .map(d -> this.modelMapper.map(d, DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentServiceModel findById(String id) {
        Document document = this.DocumentRepository.findById(id).orElse(null);

        assert document != null;
        return this.modelMapper.map(document, DocumentServiceModel.class);
    }
}
