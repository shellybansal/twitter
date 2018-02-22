package twitter.twitter.domain;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name="tweets")
public class Tweets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tweet_id")
    int tweetId;
    @Column(name="tweet_time")
    Date tweetTime;
    @Column(name="tweet_content")
    String tweetContent;
    @Column(name="user_t_name")
    String userTName;

    Tweets(){}
    Tweets(int tweetId,String tweetContent,Date tweetTime,String userTName){
        this.tweetId=tweetId;
        this.tweetTime=tweetTime;
        this.tweetContent=tweetContent;
        this.userTName=userTName;
    }
    public Tweets(String tweetContent, Date tweetTime, String userTName){
        this.tweetTime=tweetTime;
        this.tweetContent=tweetContent;
        this.userTName=userTName;
    }


    //getter Setters
    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public Date getTweetTime() {
        return tweetTime;
    }

    public void setTweetTime(Date tweetTime) {
        this.tweetTime = tweetTime;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public String getUserTName() {
        return userTName;
    }

    public void setUserTName(String userTName) {
        this.userTName = userTName;
    }
}