package anu.g35.sharebooks.data.model;

import java.time.LocalDateTime;

/**
 * Defines the user action data structure
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public class UserAction {
    public enum Type {
        SAY("SAY"),
        LIKE("LIKE"),
        DISLIKE("DISLIKE"),
        FOLLOW("FOLLOW"),
        UNFOLLOW("UNFOLLOW"),
        BORROW("BORROW"),
        RETURN("RETURN");


        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    private Type actionType;
    private String userId;
    private String content;
    private String atUserId;
    private Long atBookISBN;
    private LocalDateTime timestamp;

    public UserAction(){ }

    /**
     * Copy a UserAction object
     * @param from UserAction object
     */
    public UserAction( UserAction from) {
        this.actionType = from.actionType;
        this.userId = from.userId;
        this.content = from.content;
        this.atUserId = from.atUserId;
        this.atBookISBN = from.atBookISBN;
        this.timestamp = from.timestamp;
    }

    // Getters and setters
    public Type getActionType() {
        return actionType;
    }

    public void setActionType(Type actionType) {
        this.actionType = actionType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAtUserId() {
        return atUserId;
    }

    public void setAtUserId(String atUserId) {
        this.atUserId = atUserId;
    }

    public Long getAtBookISBN() {
        return atBookISBN;
    }

    public void setAtBookISBN(Long atBookISBN) {
        this.atBookISBN = atBookISBN;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserAction{" +
                "actionType='" + actionType + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", atUserId='" + atUserId + '\'' +
                ", atBookISBN='" + atBookISBN + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}