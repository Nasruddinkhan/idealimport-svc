package ca.com.idealimport.service.mail.boundry.repository;

import ca.com.idealimport.service.mail.entity.MailTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailTemplateRepo extends JpaRepository<MailTemplateEntity, Integer> {
    List<MailTemplateEntity> findAllByIsActiveIsTrue();

    MailTemplateEntity findByIdAndIsActiveIsTrue(Integer id);

    MailTemplateEntity findByNameAndIsActiveIsTrue(String name);
}