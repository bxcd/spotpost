package art.coded.spotpost.model.entity;

import java.util.List;

public class SessionSet {

    public static final String KEY_USERID = "userId";

    String userId;
    List<Session> sessions;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
