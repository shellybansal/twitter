package twitter.twitter.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="following")
public class Following implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "following_id")
    int followingId;
    @Column(name = "user_t_name")
    String userTName;
    @Column(name = "following_t_name")
    String followingTName;

    public Following() {
    }

    public Following(String userTName, String followingTName) {
        this.userTName = userTName;
        this.followingTName = followingTName;
    }

    public Following(int followingId, String userTName, String followingTName) {
        this.followingId = followingId;
        this.userTName = userTName;
        this.followingTName = followingTName;
    }

    public int getFollowingId() {
        return followingId;
    }

    public void setFollowingId(int followingId) {
        this.followingId = followingId;
    }

    public String getUserTName() {
        return userTName;
    }

    public void setUserTName(String userTName) {
        this.userTName = userTName;
    }

    public String getFollowingTName() {
        return followingTName;
    }

    public void setFollowingTName(String followingTName) {
        this.followingTName = followingTName;
    }
}