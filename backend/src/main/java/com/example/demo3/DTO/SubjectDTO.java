package com.example.demo3.DTO;

import java.util.List;

public class SubjectDTO {

    private Long id;
    private String name;

    private List<String> studentUsernames;
    private List<Long> studentIds;


    public SubjectDTO() {
    }

    public SubjectDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

