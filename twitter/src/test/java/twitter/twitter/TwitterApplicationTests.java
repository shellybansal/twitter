package twitter.twitter.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.runners.MockitoJUnitRunner;
import twitter.twitter.domain.Users;
import twitter.twitter.domain.UsersRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersServiceTest {

    @InjectMocks
    UsersService usersService;
    @Mock
    UsersRepository usersRepository;
    @Test
    public void testLogin() {
        Users users=new Users(  );
        users.setUserTName( "shelly141" );
        users.setfName( "Shelly" );
        users.setlName( "Bansal" );
        users.setAge( 23 );
        users.setEmail( "shellybansal141@gmail.com" );
        users.setCity( "Delhi" );
        users.setMobile( "9999044174" );
        users.setUserPassword( "shellyba" );
        String userTName=users.getUserTName();
        String userPassword=users.getUserPassword();
        List<Users> users1=new ArrayList<Users>();
        users1.add(users);
        when(usersRepository.searchLogin(userTName,userPassword)).thenReturn( users1 );
        assertEquals(usersRepository.searchLogin( userTName,userPassword ),usersService.login( users ));
    }


    @Test
    public void testFindAll(){
        Users users=new Users(  );
        users.setUserTName( "shelly141" );
        users.setfName( "Shelly" );
        users.setlName( "Bansal" );
        users.setAge( 23 );
        users.setEmail( "shellybansal141@gmail.com" );
        users.setCity( "Delhi" );
        users.setMobile( "9999044174" );
        users.setUserPassword( "shellyba" );
        when(usersRepository.findByUserTName(users.getUserTName())).thenReturn( users );
        assertEquals(usersRepository.findByUserTName(users.getUserTName()),usersService.findAll(users));
    }

    @Test
    //for populating personal details in DB
    public void testUserDetails(){
        Users users=new Users(  );
        users.setUserTName( "shelly141" );
        users.setfName( "Shelly" );
        users.setlName( "Bansal" );
        users.setAge( 23 );
        users.setEmail( "shellybansal141@gmail.com" );
        users.setCity( "Delhi" );
        users.setMobile( "9999044174" );
        users.setUserPassword( "shellyba" );
        users.setAbout( "hello" );
        String userTName=users.getUserTName();
        String fName=users.getfName();
        String lName=users.getlName();
        String city=users.getCity();
        String about=users.getAbout();
        String mobile=users.getMobile();
        int age=users.getAge();
        when(usersRepository.userDetails(userTName,fName,lName,city,age,about,mobile)).thenReturn( 1 );
        assertEquals(usersRepository.userDetails(userTName,fName,lName,city,age,about,mobile),usersService.userDetails( users ));
    }


    @Test
    //for friend suggestions
    public void friendSuggestedMethod(){
        Users users=new Users( );
        users.setUserTName( "shelly141" );
        users.setfName( "Shelly" );
        users.setlName( "Bansal" );
        users.setAge( 23 );
        users.setEmail( "shellybansal141@gmail.com" );
        users.setCity( "Delhi" );
        users.setMobile( "9999044174" );
        users.setUserPassword( "shellyba" );
        users.setAbout( "hello" );
        Users users1=new Users(  );
        users1.setUserTName( "shelly142" );
        users1.setfName( "Shelly" );
        users1.setlName( "Bansal" );
        users1.setAge( 23 );
        users1.setEmail( "shellybansal141@gmail.com" );
        users1.setCity( "Delhi" );
        users1.setMobile( "9999044174" );
        users1.setUserPassword( "shellyba" );
        users1.setAbout( "hello" );
        when(usersRepository.findByUserTName(users.getUserTName())).thenReturn( users);
        String userTName1=usersRepository.findByUserTName(users.getUserTName()).getUserTName();
        String city=usersRepository.findByUserTName(users.getUserTName()).getCity();
        int age=usersRepository.findByUserTName(users.getUserTName()).getAge();
        String fName=usersRepository.findByUserTName(users.getUserTName()).getfName();
        String lName=usersRepository.findByUserTName(users.getUserTName()).getlName();
        List<Users> list=new ArrayList<Users>( );
        list.add( users );
        list.add(users1);
        when(usersRepository.friendSuggestedMethod(userTName1,city,age,lName,fName)).thenReturn(list);
        assertEquals(usersRepository.friendSuggestedMethod(userTName1,city,age,lName,fName),usersService.friendSuggestedMethod( users ) );

    }
}
