package com.example.database;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.hpslide.YPWall;


public class LauncherActivty extends Activity implements View.OnClickListener {

	// Instance variables
	protected GoHomeAfterDelay mDelayTask = null;
	public static LauncherActivty main;
	public DatabaseController dbController = new DatabaseController(this);
	// Methods

	/**
	 * Go back to the home activity and finish the current activity.
	 * 
	 * @param context
	 *            Context
	 * @param doFinish
	 *            boolean
	 * @return void
	 */

	public void goHome(boolean doFinish) {
		
		main = this;
		Log.d("Launcher Activity",
				dbController.getTokenSetting());
		if (dbController.isFirstRun()) {
			dbController.initializeSettings();
			final Intent intent = new Intent(this, YPWall.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			if (doFinish)
				finish();

		}   
		}
		/**
	 * Handle a click on a view.
	 * 
	 */

	public void onClick(View v) {
		if (mDelayTask != null) {
			mDelayTask.cancel(true);
		}
		goHome(true);
	}

	/**
	 * onCreate - called when the activity is first created.
	 * 
	 */

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	

		// Set up to go to the home screen if a user touches the image.
		
		mDelayTask = new GoHomeAfterDelay(this);
		mDelayTask.execute();
	}

	/**
	 * onDestroy The final call you receive before your activity is destroyed.
	 * 
	 */

	protected void onDestroy() {
		if (mDelayTask != null) {
			mDelayTask.cancel(true);
			mDelayTask.disconnect();
		}
		mDelayTask = null;
		super.onDestroy();
	}

	// Internal classes

	/**
	 * This AsyncTask pauses N milliseconds and then calls goHome.
	 * 
	 */
	public class GoHomeAfterDelay extends AsyncTask<Integer, Integer, Integer> {
		private boolean mCancelled = false;
		private LauncherActivty mActivity = null;

		/**
		 * Return a new AsyncTask that is connected to the activity given.
		 * 
		 * @param context
		 *            LauncherActivity - the activity that this task is working
		 *            with
		 */

		public GoHomeAfterDelay(LauncherActivty context) {
			mActivity = context;
		}

		/**
		 * Disconnect the task from the activity it was set up to work with.
		 * Doing this means that no more calls are made to update the UI thread.
		 * In the UI thread, you can use this method from your activity code.
		 * 
		 * @return void
		 */

		public void disconnect() {
			mActivity = null;
		} // end disconnect

		/**
		 * Perform the task, which is to wait N milliseconds. Return 1 if the
		 * wait completed or 0 if the task was cancelled.
		 * 
		 */

		@Override
		protected Integer doInBackground(Integer... delayValues) {
			int delay = delayValues[0];
			try {
				Thread.sleep(delay);
			} catch (InterruptedException ex) {
			}

			if (isCancelled())
				return 0;
			else
				return 1;
		}

		/**
		 * Report that the task has been cancelled.
		 * 
		 */

		@Override
		protected void onCancelled() {
			mCancelled = true;
		}

		/**
		 * Report that the task has finished. This method runs in the UI thread.
		 */

		@Override
		protected void onPostExecute(Integer moveCount) {
			if (!mCancelled) {
				if (mActivity != null)
					mActivity.goHome(true);
			}
			mActivity.mDelayTask = null;
		}

	} // end AsyncTask subclass

} // end class
