package com.shtaigaway.dailyjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.shtaigaway.dailyjournal.task.Task;
import com.shtaigaway.dailyjournal.task.TimedTask;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends Activity implements DelayedConfirmationView.DelayedConfirmationListener {

    private Queue<Task> tasks;
    private static final long DEFAULT_TASK_TIME = 5000;
    private boolean isConfirmationRunning;
    private DelayedConfirmationView confirmationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tasks = new LinkedList<>(Arrays.asList(
                new TimedTask("Happiness", "Simply smile", DEFAULT_TASK_TIME),
                new TimedTask("Inspiration", "Do one thing every day that scares you", DEFAULT_TASK_TIME),
                new Task("Gratitude", "Say 3 things you are grateful for"),
                new Task("Training", "Workout for 5 minutes")
        ));

        confirmationView = (DelayedConfirmationView) findViewById(R.id.task_delay_button);
        confirmationView.setListener(this);
        startNextTask();
    }

    private void startNextTask() {
        Task task = tasks.poll();
        TextView taskNameView = (TextView) findViewById(R.id.task_name);
        taskNameView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        TextView taskMessageView = (TextView) findViewById(R.id.task_message);
        taskMessageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        taskNameView.setText(task.getName());
        taskMessageView.setText(task.getMessage());
        if (task instanceof TimedTask) {
            isConfirmationRunning = true;
            TimedTask timedTask = (TimedTask) task;
            confirmationView.setImageResource(R.drawable.stop_button);
            confirmationView.setTotalTimeMs(timedTask.getTime());
            confirmationView.start();
        } else {
            stopConfirmationView();
        }
    }

    private void stopConfirmationView() {
        isConfirmationRunning = false;
        confirmationView.reset();
        confirmationView.setImageResource(R.drawable.next_button);
    }

    @Override
    public void onTimerFinished(View view) {
        startNextTaskIfAvailable();
    }

    private void startNextTaskIfAvailable() {
        if (tasks.isEmpty()) {
            showSuccessAndFinish();
        } else {
            startNextTask();
        }
    }

    private void showSuccessAndFinish() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE,
                ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE,
                getString(R.string.journal_complete));
        startActivity(intent);
        finish();
    }

    @Override
    public void onTimerSelected(View view) {
        if (isConfirmationRunning) {
            stopConfirmationView();
        } else {
            startNextTaskIfAvailable();
        }
    }
}
