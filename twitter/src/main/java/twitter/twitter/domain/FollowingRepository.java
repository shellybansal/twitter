package twitter.twitter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FollowingRepository extends JpaRepository<Following,Integer> {
    List<Following> findByUserTName(String userTName);

    @Query("select count(f) from Following f where userTName=?1 and followingTName=?2")
    int alreadyFriends(String userTName,String followingTName);
    @Transactional
    @Modifying
    @Query("delete from Following f where f.userTName=?1 and f.followingTName=?2")
    void unFollowMethod(String userTName,String unFollow);

}
