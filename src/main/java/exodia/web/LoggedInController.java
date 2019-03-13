package exodia.web;


import exodia.domain.models.binding.DocumentBindingModel;
import exodia.domain.models.service.DocumentServiceModel;
import exodia.domain.models.view.DocumentDetailsViewModel;
import exodia.domain.models.view.DocumentHomeViewModel;
import exodia.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LoggedInController {

    private final DocumentService documentService;
    private final ModelMapper modelmapper;
    private final Validator validator;

    @Autowired
    public LoggedInController(DocumentService documentService, ModelMapper modelmapper, Validator validator) {
        this.documentService = documentService;
        this.modelmapper = modelmapper;
        this.validator = validator;
    }

    @GetMapping("/home")
    public ModelAndView home(ModelAndView modelAndView, HttpSession session) {

        if (session.getAttribute("userId") == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        List<DocumentHomeViewModel> models = this.documentService.findAllDocuments()
                .stream()
                .map(doc -> this.modelmapper.map(doc, DocumentHomeViewModel.class))
                .collect(Collectors.toList());

        modelAndView.setViewName("home");
        modelAndView.addObject("documents", models);

        return modelAndView;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        modelAndView.setViewName("schedule");

        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView scheduleConfirm(ModelAndView modelAndView, @ModelAttribute DocumentBindingModel model) {

        DocumentServiceModel savedModel = this.documentService.saveDocument(this.modelmapper.map(model, DocumentServiceModel.class));
        if (savedModel != null && documentIfValid(savedModel)) {
            modelAndView.setViewName("redirect:/details?id=" + savedModel.getId());
        } else {
            modelAndView.setViewName("schedule");
        }
        return modelAndView;
    }

    private boolean documentIfValid(DocumentServiceModel savedModel) {
        return this.validator.validate(savedModel).size() == 0;
    }

    @GetMapping("/details")
    public ModelAndView details(@ModelAttribute("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        DocumentDetailsViewModel document = this.modelmapper.map(this.documentService.findById(id), DocumentDetailsViewModel.class);

        if (document == null) {
            throw new IllegalArgumentException("DocumentNotFound");
        }

        modelAndView.setViewName("details");
        modelAndView.addObject("document", document);

        return modelAndView;
    }

    @GetMapping("/print")
    public ModelAndView print(@ModelAttribute("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("userId") == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        DocumentDetailsViewModel model = this.modelmapper.map(this.documentService.findById(id), DocumentDetailsViewModel.class);

        if (model == null) {
            throw new IllegalArgumentException("Wrong id");
        } else

            modelAndView.setViewName("print");
        modelAndView.addObject("model", model);

        return modelAndView;
    }

    @PostMapping("/print")
    public ModelAndView printConfirm(@ModelAttribute("id") String id, ModelAndView modelAndView) {

        if (this.documentService.findById(id) == null) {
            throw new IllegalArgumentException("DocumentNotFound");
        }

        this.documentService.removeDocumentById(id);

        modelAndView.setViewName("redirect:/home");

        return modelAndView;
    }
    
    @GetMapping("/logout") 
    public ModelAndView logout(ModelAndView modelAndView , HttpSession session){ 
        if (session.getAttribute("userId") == null){ 
        m   odelAndView.setViewName("redirect:/login"); 
        } else { 
            session.invalidate(); 
            modelAndView.setViewName("redirect:/"); 
        } 
        return modelAndView; 
    } 
}
