package twitter.twitter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import twitter.twitter.domain.Users;

import java.util.Collection;
import java.util.List;

public class UsersUserDetails extends Users implements UserDetails {
    private static final long serialVersionUID = 1L;
    private List<String> userRoles;

    public UsersUserDetails(Users users, List<String> userRoles) {
        super( users );
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}