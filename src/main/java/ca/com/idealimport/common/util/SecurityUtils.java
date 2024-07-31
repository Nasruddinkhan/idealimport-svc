package ca.com.idealimport.common.util;

import ca.com.idealimport.common.enums.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class SecurityUtils {
    private SecurityUtils() {
    }

    public static String getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return null;
    }

    public static List<String> getLoggedInUserRole() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    public static boolean isAdmin() {
        List<String> roles = SecurityUtils.getLoggedInUserRole();
        return roles.stream()
                .anyMatch(role -> RoleEnum.ADMIN.name().equals(role) || RoleEnum.SUPER_ADMIN.name().equals(role));
    }

    public static boolean isCustomer() {
        List<String> roles = SecurityUtils.getLoggedInUserRole();
        return roles.stream()
                .anyMatch(role -> RoleEnum.CUSTOMER.name().equals(role));
    }

}
