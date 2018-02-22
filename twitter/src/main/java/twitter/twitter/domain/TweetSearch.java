package twitter.twitter.domain;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tweetsearch")
public class TweetSearch implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tweet_search_id")
    int tweetSearchId;
    @Column(name="tweet_id")
    int tweetId;
    @Column(name="search_by")
    String searchBy;

    public TweetSearch() {
    }

    public TweetSearch(int tweetId, String searchBy) {
        this.tweetId = tweetId;
        this.searchBy = searchBy;
    }

    public int getTweetSearchId() {
        return tweetSearchId;
    }

    public void setTweetSearchId(int tweetSearchId) {
        this.tweetSearchId = tweetSearchId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }
}
