package twitter.twitter.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter.twitter.domain.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UsersService{
    UsersRepository usersRepository;
    TweetsRepository tweetsRepository;
    FollowingRepository followingRepository;
    TweetSearchRepository tweetSearchRepository;
    LikedByRepository likedByRepository;


    @Autowired
    UsersService(UsersRepository usersRepository, TweetsRepository tweetsRepository,FollowingRepository followingRepository,TweetSearchRepository tweetSearchRepository,LikedByRepository likedByRepository){
        this.usersRepository=usersRepository;
        this.tweetsRepository=tweetsRepository;
        this.followingRepository=followingRepository;
        this.tweetSearchRepository=tweetSearchRepository;
        this.likedByRepository=likedByRepository;
    }

    //custom save during registration
    public String saveRegister(Users users){
        String userTName=users.getUserTName();
        String email=users.getEmail();
        Users users1=usersRepository.findByUserTName(userTName);
        Users users2=usersRepository.findByEmail(email);
        //to check exsistance of userTname or Email
        if(users1==null&&users2==null){
            Users users3=usersRepository.save(users);
            return "saved";
        }
        else if(users1==null){
            return "email exist";
        }
        else
            return "twitter name exist";
    }

    //for login credentials
    public List<Users> login(Users users){
        String userTName=users.getUserTName();
        String userPassword=users.getUserPassword();
        List<Users> searchedLogin=usersRepository.searchLogin(userTName,userPassword);
        return searchedLogin;

    }

    //for user profile
    public Users findAll(Users users){
        String userTName=users.getUserTName();
        Users users1=usersRepository.findByUserTName(userTName);
        return users1;
    }

    //for populating personal details in DB
    public int userDetails(Users users){
        String userTName=users.getUserTName();
        String fName=users.getfName();
        String lName=users.getlName();
        String city=users.getCity();
        String about=users.getAbout();
        String mobile=users.getMobile();
        int age=users.getAge();
        int list1=usersRepository.userDetails(userTName,fName,lName,city,age,about,mobile);
        return list1;
    }

    //for friend suggestions
    public List<Users>friendSuggestedMethod(Users users){
        String userTName=users.getUserTName();
        Users users1=usersRepository.findByUserTName( userTName );
        String userTName1=users1.getUserTName();
        String city=users1.getCity();
        int age=users1.getAge();
        String fName=users1.getfName();
        String lName=users1.getlName();
        List<Users> friendSuggested=usersRepository.friendSuggestedMethod(userTName1,city,age,lName,fName);
        return friendSuggested;
    }

    //for start following someone(add friend)
    public String startFollowingMethod(String[] users){
        Following following=new Following(users[0],users[1]);
        Following following1=followingRepository.save(following);
        return following1.getFollowingTName();
    }

    //search user
    public List<Object[]> searchUser(String[] searchBy) {
        String searchByContent = searchBy[1];
        String searchedBy1 = searchBy[0];
        if(searchByContent.contains( "#" )){
            List<Object[]> searchResult=tweetSearchRepository.searchBy(searchByContent);
            return searchResult;

        }
        else{
            ArrayList<Object[]> result=new ArrayList<Object[]>();
            List<Object[]> searchResult = usersRepository.userSearch( searchedBy1, searchByContent );
            for (Object[] obj:searchResult) {
                int count=followingRepository.alreadyFriends(searchedBy1,(String)obj[0]);
                Object[] newArr = new Object[7];
                System.arraycopy(obj, 0, newArr, 0, 5);
                if(count==0){
                    newArr[6]=false;
                }
                else{
                    newArr[6]=true;
                }
                result.add(newArr);

            }
            return result;
        }

    }

    //view friend List
     public List<Users> searchFriendList(String userTName){
        List<Following> followingList=followingRepository.findByUserTName(userTName);
        List<Users> users1=new ArrayList<Users>();
        for (Following following:followingList){
            Users users=usersRepository.findByUserTName( following.getFollowingTName());
            users1.add(users);
        }
        return users1;
     }

     //method to unfollow a friend
    public void unFollowMethod(String[] data){
        String userTName=data[0];
        String unFollow=data[1];
        followingRepository.unFollowMethod(userTName,unFollow);

    }

    //for posting a tweet and adding it to a table by hashtag values
    public Tweets postTweetMethod(String[] data){
        Tweets tweets=new Tweets(data[1],new Date(  ),data[0]);
        //saving a tweet
        Tweets tweets1=tweetsRepository.save(tweets);
        //finding hashtags in content
        if(data[1].contains("#")){
            //splits in to words
            String[] tweetSplit=data[1].split( " " );
            for (String split:tweetSplit){
                //search words with #
                if(split.contains( "#")){
                    //check for dublications
                    if(!tweetSearchRepository.exists(split,tweets1.getTweetId())){
                        TweetSearch tweetSearch=new TweetSearch(tweets1.getTweetId(),split);
                        tweetSearchRepository.save(tweetSearch);
                    }
                }
            }
        }
        return tweets1;
    }

    //for rendering tweets in user timeline
    public ArrayList<ArrayList<Object[]>> yourTweetsMethod(String data) {
        ArrayList<ArrayList<Object[]>> result = new ArrayList<ArrayList<Object[]>>();
        //fetching tweet details
        ArrayList<Object[]> tweets = tweetsRepository.findAllByUserTName( data );
        //for counting number of likes
        for (Object[] twee : tweets) {
            //count of number of likes
            int count = likedByRepository.countLikes( (int) twee[3] );
            //storing likes count and likecheck for user in a string array
            Object[] arr1 = new Object[1];
            arr1[0] = "" + count;
            String[] likedBy = likedByRepository.findByTwitterId( (int) twee[3] );
            ArrayList<Object[]> arr = new ArrayList<Object[]>();
            //tweet details
            arr.add( twee );
            // likes count and likeCheckResult
            arr.add( arr1 );
            arr.add( likedBy );
            result.add( arr );
        }
        return result;
    }

    //for deleting a tweet
    public int deleteTweetMethod(int tweetId){
        int deletedcount=likedByRepository.delete(tweetId);
        int deletedcount1=tweetSearchRepository.delete(tweetId);
        tweetsRepository.delete(tweetId);
        return  deletedcount;
    }

    //for rendering stalker tweets
    public ArrayList<ArrayList<Object[]>>stalkTweetsMethod(String data){
        ArrayList<ArrayList<Object[]>> result=new ArrayList<ArrayList<Object[]>>( );
        //fetching stalk tweets in tweets
        ArrayList<Object[]> tweets=tweetsRepository.stalkTweetsMethod(data);
        //for counting number of likes
        for (Object[] twee : tweets) {
            //count of number of likes
            int count=likedByRepository.countLikes((int)twee[0]);
            //storing likes count and likecheck for user in a string array
            Object[] arr1=new Object[2];
            arr1[0]=""+count;
            //user already liked the post if true
            if(likedByRepository.likeCheck((int)twee[0],data)){
                boolean likeCheckResult=true;
                arr1[1]=likeCheckResult;
            }
            else {
                boolean likeCheckresult = false;
                arr1[1]=likeCheckresult;
            }
            String[] likedBy=likedByRepository.findByTwitterId((int)twee[0]);
            ArrayList<Object[]> arr=new ArrayList<Object[]>();
            //tweet details
            arr.add(twee);
            // likes count and likeCheckResult
            arr.add(arr1);
            arr.add(likedBy);
            result.add(arr);

        }
        return result;
    }

    //for liking a tweet
    public LikedBy likeTweetMethod(String[] data){
        LikedBy likedBy=new LikedBy(Integer.parseInt(data[1]),data[0]);
        LikedBy likedBy1=likedByRepository.save(likedBy);
        return likedBy1;
    }

    //for unliking a tweet
    public int unLikeTweetMethod(String[] data){
        int result=likedByRepository.unLikeTweetMethod(Integer.parseInt(data[1]),data[0]);
        return result;
    }
}