package art.coded.spotpost.model.entity;

import java.util.List;

/**
 * An Event object instance with the required tags for interfacing with Dao and Room
 */
public class Session {

    public static final String KEY_USERID = "userId";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_PAGES = "pages";
    public static final String KEY_STARTTIME = "startTime";

    String userId;
    Integer duration;
    List<String> pages;
    Long startTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
