package twitter.twitter.domain;


import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.Id;

@Entity
@Table(name="likedby")
public class LikedBy {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="liked_by_id")
    int likedById;
    @Column(name="tweet_id")
    int tweetId;
    @Column(name="follower_t_name")
    String followerTName;

    //constructor
    public LikedBy() {
    }

    public LikedBy(int tweetId, String followerTName) {
        this.tweetId = tweetId;
        this.followerTName = followerTName;
    }

    public LikedBy(int likedById, int tweetId, String followerTName) {
        this.likedById = likedById;
        this.tweetId = tweetId;
        this.followerTName = followerTName;
    }

    //getter setter

    public int getLikedById() {
        return likedById;
    }

    public void setLikedById(int likedById) {
        this.likedById = likedById;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getFollowerTName() {
        return followerTName;
    }

    public void setFollowerTName(String followerTName) {
        this.followerTName = followerTName;
    }
}
