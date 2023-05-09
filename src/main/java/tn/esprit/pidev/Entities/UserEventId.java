package tn.esprit.pidev.Entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class UserEventId implements Serializable {
    private Long userId;
    private int eventId;

    UserEventId()
    {

    }

    public UserEventId(Long userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    
    
    // getters and setters, equals(), and hashCode() methods
}