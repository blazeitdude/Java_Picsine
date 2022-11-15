package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private Long Id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime dateTime;


    public Message(Long id, User author, Chatroom room, String text, LocalDateTime dateTime) {
        Id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Message{" + "\n" +
                "   Id=" + Id + ",\n" +
                "   author=" + author + ",\n" +
                "   room=" + room + ",\n" +
                "   text='" + text + '\'' + ",\n" +
                "   dateTime=" + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                '}';
    }
}
