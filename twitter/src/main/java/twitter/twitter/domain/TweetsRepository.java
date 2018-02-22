package twitter.twitter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TweetsRepository extends JpaRepository<Tweets,Integer>{

    @Query("select t.userTName,t.tweetContent,to_char(t.tweetTime, 'YYYY-MM-DD HH24:MI'),t.tweetId from Tweets t where t.userTName=?1 order by t.tweetTime desc")
    ArrayList<Object[]>findAllByUserTName(String userTName);

    @Query("select t.tweetId,t.tweetContent,to_char(t.tweetTime, 'YYYY-MM-DD HH24:MI'),t.userTName from Tweets t where t.userTName in(select f.followingTName from Following f where f.userTName=?1) order by t.tweetTime desc")
    ArrayList<Object[]>stalkTweetsMethod(String userTName);
}
