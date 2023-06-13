package ca.com.idealimport.service.mail.control;

import ca.com.idealimport.service.mail.boundry.repository.MailTemplateRepo;
import ca.com.idealimport.service.mail.entity.MailTemplateEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MailTemplateService {
    private final MailTemplateRepo mailTemplateRepo;

     public List<MailTemplateEntity> getAllTemplates() {
        return mailTemplateRepo.findAllByIsActiveIsTrue();
    }


    public MailTemplateEntity getMailTemplateById(Integer id) {
        return mailTemplateRepo.findByIdAndIsActiveIsTrue(id);
    }


    public MailTemplateEntity getMailTemplateByName(String name) {
        return mailTemplateRepo.findByNameAndIsActiveIsTrue(name);
    }
}
