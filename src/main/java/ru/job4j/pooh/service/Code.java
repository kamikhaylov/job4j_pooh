package ru.job4j.pooh.service;

public enum Code {
    POST ("POST"),
    GET ("GET"),
    STATUS_200 ("200"),
    STATUS_204 ("204");

    private String title;

    Code(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
