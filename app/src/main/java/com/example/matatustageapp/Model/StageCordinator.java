package com.example.matatustageapp.Model;

public class StageCordinator {
    private String content;
    private String title;
public StageCordinator(){

}
    public StageCordinator(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
