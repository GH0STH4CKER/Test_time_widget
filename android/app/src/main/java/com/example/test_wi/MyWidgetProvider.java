package com.example.test_wi;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;

public class MyWidgetProvider extends AppWidgetProvider {

    private static final int JOB_ID = 1; // Unique job ID for the periodic task

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // Schedule the background job for periodic time update
        scheduleTimeUpdateJob(context);

        // Update all widget instances
        ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // Set a placeholder text or the initial time
            views.setTextViewText(R.id.widget_text, "Loading...");

            // Push the updated views to the widget
            appWidgetManager.updateAppWidget(widgetId, views);
        }
    }

    private void scheduleTimeUpdateJob(Context context) {
        // Create a job scheduler to run the TimeUpdateService every minute
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, new ComponentName(context, TimeUpdateService.class))
                .setPeriodic(60000) // Set periodic update every 60 seconds (1 minute)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // Job can run with any network type
                .setPersisted(true) // Keep job running after reboot
                .build();

        // Get the JobScheduler system service and schedule the job
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
    }
}
