package com.mcqportal.service;

import com.mcqportal.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private  Long id;
    private String firstname;
    private  String email;
    private  String password;
    private  User.Role role;
    private  Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,String firstname, String email, String password, User.Role role) {
        this.id = id;
        this.firstname= firstname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authorities = List.of(new SimpleGrantedAuthority(role.name()));
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }


    // Add getter for name (firstname)
    public String getFirstName() {
        return firstname;
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
