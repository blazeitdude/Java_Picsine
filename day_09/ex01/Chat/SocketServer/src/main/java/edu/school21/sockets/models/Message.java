package edu.school21.sockets.models;

import java.time.LocalDateTime;
import java.util.Objects;
import edu.school21.chat.models.Chatroom;

public class Message {
    private Long id;
    private User author;
    private Chatroom chatroom;
    private String message;
    private LocalDateTime datetime;

    public Message(Long id, User author, Chatroom chatroom, String message, LocalDateTime datetime) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.message = message;
        this.datetime = datetime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return datetime;
    }

    public void setLocalDateTime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id.equals(message1.id) && author.equals(message1.author) && chatroom.equals(message1.chatroom) && message.equals(message1.message) && datetime.equals(message1.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chatroom, message, datetime);
    }

    @Override
    public String toString() {
        return "Message : {" +
                "\n\tid=" + id +
                ",\n\tauthor=" + author +
                ",\n\troom=" + chatroom +
                ",\n\ttext='" + message + '\'' +
                ",\n\tdateTime=" + datetime +
                "\n}";
    }
}
