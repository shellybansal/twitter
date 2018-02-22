package twitter.twitter.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface UsersRepository extends JpaRepository<Users,Long>{
    Users findByUserTName(String userTName);
    Users findByEmail(String email);

    //to check login credentials
    @Transactional
    @Modifying
    @Query("select userTName,userPassword from Users  where userTName=?1 and userPassword=?2")
    List<Users> searchLogin(String userTName,String userPassword);

    @Transactional
    @Modifying
    @Query("update Users a set a.fName=?2,a.lName=?3,a.city=?4,a.age=?5,a.about=?6,a.mobile=?7 where a.userTName=?1 ")
    int userDetails(String userTName,String fName,String lName,String city,int age,String about,String mobile);

    //query of finding friend suggestion
    @Transactional
    @Modifying
    @Query("select u from Users u " +
            "where(u.city=?2 or u.age=?3 or u.lName=?4 or u.fName=?5)" +
            "and u.userTName!=?1 " +
            "and u.userTName not in (select f.followingTName from Following f where userTName=?1)")
    List<Users> friendSuggestedMethod(String userTName,String city,int age,String lName,String fName);

    //query for searching a user
    @Query("select distinct u.userTName,u.email,u.fName,u.lName,u.city from Users u where userTName!=?1 and (u.userTName like %?2% or u.fName like %?2%)")
    List<Object[]> userSearch(String searchedBy,String searchByName);



}