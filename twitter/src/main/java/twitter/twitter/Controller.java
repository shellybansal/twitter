package twitter.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter.twitter.domain.Following;
import twitter.twitter.domain.LikedBy;
import twitter.twitter.domain.Tweets;
import twitter.twitter.domain.Users;
import twitter.twitter.service.UsersService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * main controller class to serve request for tweet
 */
@RestController
public class Controller {
    @Autowired
    UsersService usersService;
    //logged in user
    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
    //registration Api
    @RequestMapping(value = "/registration", method=RequestMethod.POST)
    public ResponseEntity registration(@RequestBody Users users, HttpServletResponse response){
        String msg=usersService.saveRegister(users);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "message", msg );
        return new ResponseEntity(data, HttpStatus.OK);
    }


    @RequestMapping(value="/login",method=RequestMethod.POST)
    public ResponseEntity login(@RequestBody Users users){
        List<Users> result=usersService.login(users);
        return new ResponseEntity(result,HttpStatus.OK);

    }

    @RequestMapping(value="/viewProfile",method=RequestMethod.POST)
    public ResponseEntity viewProfile(@RequestBody Users users){
        Users result=usersService.findAll(users);
        return new ResponseEntity(result,HttpStatus.OK);
    }

    @RequestMapping(value="/userDetails", method=RequestMethod.POST)
    public ResponseEntity userDetailsSave(@RequestBody Users users){
        int list1=usersService.userDetails(users);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put( "message", list1 );
        return new ResponseEntity( data,HttpStatus.OK );
    }

    @RequestMapping(value="/friendSuggestion",method=RequestMethod.POST)
    public ResponseEntity friendSuggestion(@RequestBody Users users){
        List<Users> friendSuggested=usersService.friendSuggestedMethod(users);
        return new ResponseEntity( friendSuggested,HttpStatus.OK );
    }
    @RequestMapping(value="/startFollowing",method=RequestMethod.POST)
    public ResponseEntity startFollowing(@RequestBody String[] users){
        String added=usersService.startFollowingMethod(users);
        Map<String, String> data = new HashMap<String,String>();
        data.put( "added", added );
        return new ResponseEntity(data,HttpStatus.OK);
    }

    //for searching a user
    @RequestMapping(value="/searchUser",method=RequestMethod.POST)
    public ResponseEntity searchUser(@RequestBody String[] searchBy){
        List<Object[]> searchResult=usersService.searchUser(searchBy);
        return new ResponseEntity( searchResult,HttpStatus.OK );
    }

    //for viewing friends list
    @RequestMapping(value="/manageFriends",method=RequestMethod.POST)
    public ResponseEntity manageFriends(@RequestBody String userTName){
        List<Users> friendList=usersService.searchFriendList(userTName);
        return new ResponseEntity(friendList,HttpStatus.OK);
    }

    //unfollow a friend
    @RequestMapping(value="/unFollow",method =RequestMethod.POST)
    public ResponseEntity unFollow(@RequestBody String[] data){
        usersService.unFollowMethod(data);
        Map<String, String> deleted = new HashMap<String,String>();
        deleted.put( "message", "deleted" );
        return new ResponseEntity(deleted,HttpStatus.OK);
    }

    //for posting your tweet
    @RequestMapping(value="/postTweet",method=RequestMethod.POST)
    public ResponseEntity postTweet(@RequestBody String[] data){
        Tweets tweets=usersService.postTweetMethod(data);
        Map<String,String> tweeted=new HashMap<String ,String>();
        tweeted.put("message",tweets.getTweetContent());
        return new ResponseEntity( tweeted,HttpStatus.OK );
    }

    //for rendering timeline tweets of a user
    @RequestMapping(value="/yourTweets",method=RequestMethod.POST)
    public ResponseEntity yourTweets(@RequestBody String data){
        ArrayList<ArrayList<Object[]>> tweets=usersService.yourTweetsMethod(data);
        return new ResponseEntity( tweets,HttpStatus.OK );
    }

    //for rendering stalker tweets of a user
    @RequestMapping(value="/stalkTweets",method=RequestMethod.POST)
    public ResponseEntity stalkTweets(@RequestBody String data){
        ArrayList<ArrayList<Object[]>> tweets=usersService.stalkTweetsMethod(data);
        return new ResponseEntity(tweets,HttpStatus.OK );
    }

    /**
     * api to like tweet return the user who liked the tweet
     * @param data tweet ids
     * @return response entity
     */
    //for liking a tweet of a follower
    @RequestMapping(value="/likeTweet",method=RequestMethod.POST)
    public ResponseEntity likeTweet(@RequestBody String[] data){
        LikedBy likedBy=usersService.likeTweetMethod(data);
        Map<String,String> result=new HashMap<String ,String>();
        result.put("message",likedBy.getFollowerTName());
        return new ResponseEntity(result,HttpStatus.OK );
    }

    //for unlike a tweet of a follower
    @RequestMapping(value="/unLikeTweet",method=RequestMethod.POST)
    public ResponseEntity unLikeTweet(@RequestBody String[] data){
        int result=usersService.unLikeTweetMethod(data);
        Map<String,Integer> result1=new HashMap<String ,Integer>();
        result1.put("message",result);
        return new ResponseEntity(result1,HttpStatus.OK );
    }

    //for deleting your post
    @RequestMapping(value="/deleteTweet",method=RequestMethod.POST)
    public ResponseEntity deleteTweet(@RequestBody int data){
        int deleted=usersService.deleteTweetMethod(data);
        Map<String,Integer> result=new HashMap<String ,Integer>();
        result.put("count",deleted);
        return new ResponseEntity(result,HttpStatus.OK );
    }



}
