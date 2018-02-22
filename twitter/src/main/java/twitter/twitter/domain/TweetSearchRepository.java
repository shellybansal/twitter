package twitter.twitter.domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TweetSearchRepository extends JpaRepository<TweetSearch,Integer> {
    @Query(value ="SELECT CASE  WHEN count(t)> 0 THEN true ELSE false END FROM TweetSearch t where t.searchBy=?1 and tweetId=?2 ")
    boolean exists(String searchBy,int tweetId);

    @Transactional
    @Modifying
    @Query("delete from TweetSearch u where u.tweetId=?1")
    int delete(int TweetId);
    @Query("select t.tweetContent,t.userTName,t.tweetId from Tweets t where t.tweetId in(select s.tweetId from TweetSearch s where s.searchBy like %?1%)")
    List<Object[]> searchBy(String hash);
}
