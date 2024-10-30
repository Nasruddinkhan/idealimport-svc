package ca.com.idealimport.service.users.entity;

import ca.com.idealimport.common.dto.DropDownDto;
import ca.com.idealimport.common.util.DropDownDtoListConverter;
import ca.com.idealimport.service.role.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, length = 60, updatable = false)
    private String userName;

    @Column(name = "first_name",  length = 60)
    private String firstName;

    @Column(name = "last_name",  length = 30)
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true, length = 100)
    private String email;
    @Column(name = "mobile_no", unique = true, length = 15)
    private String mobileNo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(name = "additional_permission", columnDefinition = "TEXT")
    @Convert(converter = DropDownDtoListConverter.class)
    private List<DropDownDto> additionalPermission;

}
