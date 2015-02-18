package com.shtaigaway.dailyjournal.task;

/**
 * Created by Naughty Spirit <hi@naughtyspirit.co>
 * on 2/18/15.
 */
public class Task {
    private final String message;
    private final String name;

    public Task(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
