package twitter.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import twitter.twitter.domain.Users;
import twitter.twitter.domain.UsersRepository;

import java.util.List;

@Service
public class UsersUserDetailService implements UserDetailsService {
    private final UsersRepository userRepository;

    @Autowired
    public UsersUserDetailService(UsersRepository usersRepository) {
        this.userRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserTName( username );
        if (null == user) {
            throw new UsernameNotFoundException( "No user present with username: " + username );
        } else {
            List<String> userRoles = new ArrayList<String>();
            userRoles.add( "user" );
            return new UsersUserDetails( user, userRoles );
        }
    }
}

