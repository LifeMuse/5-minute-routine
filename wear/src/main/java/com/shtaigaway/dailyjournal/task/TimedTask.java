package com.shtaigaway.dailyjournal.task;

/**
 * Created by Naughty Spirit <hi@naughtyspirit.co>
 * on 2/18/15.
 */
public class TimedTask extends Task {
    private final long time;

    /**
     * @param time in milliseconds
     */
    public TimedTask(String name, String message, long time) {
        super(name, message);
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
