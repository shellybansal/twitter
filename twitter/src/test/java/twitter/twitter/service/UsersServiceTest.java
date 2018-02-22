package twitter.twitter.service;

import org.apache.catalina.User;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import twitter.twitter.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {
    @InjectMocks
    UsersService usersService;

    @Mock
    TweetsRepository tweetsRepository;
    FollowingRepository followingRepository;
    LikedByRepository likedByRepository;
    TweetSearchRepository tweetSearchRepository;
    UsersRepository usersRepository;


    //@Test
    /*public void saveRegister() {
        Users users = new Users();
        users.setUserTName( "shelly141" );
        users.setfName( "Shelly" );
        users.setlName( "Bansal" );
        users.setAge( 23 );
        users.setEmail( "shellybansal141@gmail.com" );
        users.setCity( "Delhi" );
        users.setMobile( "9999044174" );

        //checking if user already registered
        when( usersRepository.findByUserTName( users.getUserTName() ) ).thenReturn(null).thenReturn( users ).thenReturn( null );
        when( usersRepository.findByEmail( users.getEmail() ) ).thenReturn( null ).thenReturn( null).thenReturn( users );
//        Users users1=usersRepository.findByUserTName(users.getUserTName());
//        Users users2=usersRepository.findByEmail(users.getEmail());
//        Users users3=usersRepository.findByUserTName(users.getUserTName());
//        Users users4=usersRepository.findByEmail(users.getEmail());
//        Users users5=usersRepository.findByUserTName(users.getUserTName());
//        Users users6=usersRepository.findByEmail(users.getEmail());
        if(usersRepository.findByUserTName( users.getUserTName())) && usersRepository.findByEmail( users.getEmail() )){
            return "saved";
        }


        assertEquals( "saved", us);

    }*/

    @Test
    public void login() {
        Users users=new Users(  );
        users.setUserTName( "shelly141" );
        users.setfName( "Shelly" );
        users.setlName( "Bansal" );
        users.setAge( 23 );
        users.setEmail( "shellybansal141@gmail.com" );
        users.setCity( "Delhi" );
        users.setMobile( "9999044174" );
        String userTName=users.getUserTName();
        String userPassword=users.getUserPassword();
        List<Users> users1=new ArrayList<Users>();
        users1.add(users);
        when(usersRepository.searchLogin(userTName,userPassword)).thenReturn(users1);
        assertEquals(usersRepository.searchLogin(userTName,userPassword),usersService.login( users ));

    }

    @Test
    public void findAll() {
    }
}
