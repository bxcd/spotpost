package art.coded.spotpost.model.entity;

/**
 * An Event object instance with the required tags for interfacing with Dao and Room
 */
public class Event {

    public static final String KEY_EVENTID = "eventId";
    public static final String KEY_URL = "url";
    public static final String KEY_VISITORID = "visitorId";
    public static final String KEY_TIMESTAMP = "timestamp";

    Integer eventId;
    String url;
    String visitorId;
    Long timestamp;

    public Integer getEventId() {
        return eventId;
    }

    public String getUrl() {
        return url;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setVisitorId(String visitorId) { this.visitorId = visitorId; }
}