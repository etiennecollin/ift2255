/*
 * Copyright (c) 2023. Etienne Collin #20237904, Nicholas Cooper #20241729, Charlotte Locas #20211755
 */

package com.etiennecollin.ift2255.clientCLI.classes;

public class Comment {
    private String content;
    private String title;
    private Buyer author;
    private int likes;

    public Comment(String content, String title, Buyer author) {
        this.setContent(content);
        this.setTitle(title);
        this.setAuthor(author);
        this.setLikes(0);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Buyer getAuthor() {
        return author;
    }

    public void setAuthor(Buyer author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
