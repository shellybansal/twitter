package twitter.twitter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LikedByRepository extends JpaRepository<LikedBy,Integer> {
    //for counting likes
    @Query("select count(l) from LikedBy l where l.tweetId=?1")
    int countLikes(int tweetId);

    //for checking if logged in user liked a tweet
    @Query(value ="SELECT CASE  WHEN count(l)> 0 THEN true ELSE false END FROM LikedBy l where l.tweetId=?1 and followerTName=?2 ")
    boolean likeCheck(int tweetId,String followerTName);

    //for unlike a tweet
    @Transactional
    @Modifying
    @Query("delete from LikedBy where tweetId=?1 and followerTName=?2")
    int unLikeTweetMethod(int tweetId,String followerTname);

    @Query("select u.followerTName from LikedBy u where u.tweetId=?1")
    String[] findByTwitterId(int tweetId);

    @Transactional
    @Modifying
    @Query("delete from LikedBy u where u.tweetId=?1")
    int delete(int TweetId);
}
