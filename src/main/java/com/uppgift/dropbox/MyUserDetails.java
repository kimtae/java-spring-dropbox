package com.uppgift.dropbox;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

@Data
public class MyUserDetails implements UserDetails{
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    //get the db values and set it to user
    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        authorities = null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
