package ca.com.idealimport.service.mail.entity;

import ca.com.idealimport.common.util.JsonMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "mail_template")
public class MailTemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "subject")
    private String subject;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "required_data")
    @Convert(converter = JsonMapConverter.class)
    private Map<String, String> requiredData;

}
