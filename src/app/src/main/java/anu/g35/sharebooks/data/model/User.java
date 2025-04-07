package anu.g35.sharebooks.data.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the user action data structure
 *
 * @author u7703248 Chuang Ma
 * @since 2024-04-19
 */
public class User implements Serializable, Cloneable{
    private String id;
    private String name;
    private String password;
    private List<String> fans = new ArrayList<>();
    private List<String> following = new ArrayList<>();
    private List<Long> like_books = new ArrayList<>();
    private String biography;
    private String avatar;
    private String address;
    private String coordinates;

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getFans() {
        return fans;
    }

    public void addFan(String fanId){
        if (!this.fans.contains(fanId)) {
            this.fans.add(fanId);
        }
    }

    public void removeFan(String fanId){
        if (this.fans.contains(fanId)) {
            this.fans.remove(fanId);
        }
    }

    public List<String> getFollowing() {
        return following;
    }

    public void addFollow(String FollowId){
        if (!this.following.contains(FollowId)){
            this.following.add(FollowId);
        }
    }

    public void removeFollow(String FollowId){
        if (this.following.contains(FollowId)){
            this.following.remove(FollowId);
        }
    }

    public List<Long> getLikeBooks() {
        return like_books;
    }

    public void addLikeBook(Long bookISBN){
        if (!this.like_books.contains(bookISBN)){
            this.like_books.add(bookISBN);
        }
    }

    public void removeLikeBook(Long bookISBN){
        if (this.like_books.contains(bookISBN)) {
            this.like_books.remove(bookISBN);
        }
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", fans='" + fans + '\'' +
                ", following='" + following + '\'' +
                ", like_books='" + like_books + '\'' +
                ", biography='" + biography + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }

}