package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdChatrooms;
    private List<Chatroom> socializedChatrooms;


    public User(Long id, String login, String password, List<Chatroom> createdChatrooms, List<Chatroom> socializedChatrooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChatrooms = createdChatrooms;
        this.socializedChatrooms = socializedChatrooms;
    }

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChatrooms = null;
        this.socializedChatrooms = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreatedChatrooms() {
        return createdChatrooms;
    }

    public void setCreatedChatrooms(List<Chatroom> createdChatrooms) {
        this.createdChatrooms = createdChatrooms;
    }

    public List<Chatroom> getSocializedChatrooms() {
        return socializedChatrooms;
    }

    public void setSocializedChatrooms(List<Chatroom> socializedChatrooms) {
        this.socializedChatrooms = socializedChatrooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdChatrooms, socializedChatrooms);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdChatrooms=" + createdChatrooms +
                ", socializedChatrooms=" + socializedChatrooms +
                '}';
    }
}
