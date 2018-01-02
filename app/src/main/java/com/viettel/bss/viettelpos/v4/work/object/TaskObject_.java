package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

public class TaskObject_ implements Serializable {
	private String tastId;
	private String taskTypeId;
	private String taskRoadId;
	private String nameTask;
	private String contentTask;
	private String createdDateTask;
	private String finishedDateTask;
	private String imageTask;
	private String videoTask;
	private String statuTask;
	String objectId;
	private boolean status;
	private int progress;

	public TaskObject_(String tastId, String taskTypeId, String nameTask,
			String contentTask, String createdDateTask, String imageTask,
			String videoTask, String statuTask) {
		super();
		this.tastId = tastId;
		this.taskTypeId = taskTypeId;
		this.nameTask = nameTask;
		this.contentTask = contentTask;
		this.createdDateTask = createdDateTask;
		this.imageTask = imageTask;
		this.videoTask = videoTask;
		this.statuTask = statuTask;
	}
	public TaskObject_(String tastId, String taskTypeId, String nameTask,
			String contentTask, String createdDateTask, String imageTask,
			String videoTask, boolean status) {
		super();
		this.tastId = tastId;
		this.taskTypeId = taskTypeId;
		this.nameTask = nameTask;
		this.contentTask = contentTask;
		this.createdDateTask = createdDateTask;
		this.imageTask = imageTask;
		this.videoTask = videoTask;
		this.status = status;
	}
	public TaskObject_(String tastId, String taskTypeId, String nameTask,
			String contentTask, String createdDateTask, String finishedDate,
			String imageTask, String videoTask, int istatus) {
		super();
		this.tastId = tastId;
		this.taskTypeId = taskTypeId;
		this.nameTask = nameTask;
		this.contentTask = contentTask;
		this.createdDateTask = createdDateTask;
		this.finishedDateTask = finishedDate;
		this.imageTask = imageTask;
		this.videoTask = videoTask;
		this.progress = istatus;

	}

	private String scheDateToText;
	public TaskObject_(String tastId, String taskTypeId, String taskRoadId,
			String nameTask, String contentTask, String createdDateTask,
			String scheDateTo, String finishedDate, String imageTask,
			String videoTask, int istatus) {
		super();
		this.tastId = tastId;
		this.taskTypeId = taskTypeId;
		this.taskRoadId = taskRoadId;
		this.nameTask = nameTask;
		this.contentTask = contentTask;
		this.createdDateTask = createdDateTask;
		this.scheDateToText = scheDateTo;
		this.finishedDateTask = finishedDate;
		this.imageTask = imageTask;
		this.videoTask = videoTask;
		this.progress = istatus;
		// act = LoginActivity.getInstance();
		setPolicyProgress();

	}

	private final String tag = "task object_";
	// Activity act;
	// Calendar cal;
    private int colorId;
	private final long oneDayInMillSec = 86400 * 1000;
	// TRUONG HOP CHUA TRUYEN THONG
	public void setPolicyProgress() {
		// TODO Auto-generated method stub

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		Activity act = LoginActivity.getInstance();
		// lay ra gio ko chuan hien tai, chenh lech 1900

		String arrScheDateTo[] = scheDateToText.split("-");
		if (arrScheDateTo.length < 3) {
			return;
		}
		int year = Integer.valueOf(arrScheDateTo[0]);
		int month = Integer.valueOf(arrScheDateTo[1]);
		int date = Integer.valueOf(arrScheDateTo[2]);

		Date dateScheTo = new Date(year, month, date);

		// if(dateScheTo.compareTo(dateNow) < 0){
		// iProgress = Constant.PROGRESS_LATE;
		// }else{
		// iProgress = Constant.com
		// }
		if (progress == 1) {
			Date dateNow = new Date(cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
			numOfDateLate_notDone = (int) ((dateNow.getTime() - dateScheTo
					.getTime()) / oneDayInMillSec);
			Log.e(tag, dateNow.getTime() + " " + dateScheTo.getTime());
			Log.e(tag, "scheDateTo---" + year + ", " + month + ", " + date);
			Log.e(tag,
					"now---" + cal.get(Calendar.YEAR) + ", "
							+ cal.get(Calendar.MONTH) + ", "
							+ cal.get(Calendar.DATE));
			// SOM HON <= 2 NGAY
			if (numOfDateLate_notDone < Constant.DATE_REMAIN) {
				iProgress = Constant.PROGRESS_COMING_FAR;
				// chua truyen thong, con x ngay
				progressText = act
						.getString(Constant.arrResIdWarnDate[iProgress])
						+ ", "
						+ act.getString(R.string.remain).toLowerCase()
						+ " "
						+ -numOfDateLate_notDone
						+ " "
						+ act.getString(R.string.date).toLowerCase();
				colorId = R.color.brown_light;
				Log.e(tag, "color ---brown");
			} else if (numOfDateLate_notDone < 0) {
				// toi han, con 1 ngay
				iProgress = Constant.PROGRESS_COMING;
				progressText = act
						.getString(Constant.arrResIdWarnDate[iProgress])
						+ ", "
						+ act.getString(R.string.remain).toLowerCase()
						+ " "
						+ -numOfDateLate_notDone
						+ " "
						+ act.getString(R.string.date).toLowerCase();
				colorId = R.color.orange;
				Log.e(tag, "color ---red");
			} else if (numOfDateLate_notDone == 0) {
				// toi han, hom nay
				iProgress = Constant.PROGRESS_COMING;
				progressText = act
						.getString(Constant.arrResIdWarnDate[iProgress])
						+ ", " + act.getString(R.string.today).toLowerCase();
				colorId = R.color.orange;
				Log.e(tag, "color ---red");
			} else {
				// qua han x ngay
				iProgress = Constant.PROGRESS_LATE;
				progressText = act
						.getString(Constant.arrResIdWarnDate[iProgress])
						+ " "
						+ numOfDateLate_notDone
						+ " "
						+ act.getString(R.string.date).toLowerCase();
				colorId = R.color.red_normal;
				Log.e(tag, "color ---red normal");
			}
		} else {
			iProgress = Constant.PROGRESS_DONE_OK;
			progressText =
			// act
			// .getString(Constant.ARR_PROGRESS_RESID[iProgress])
			// + "\n"
			// +
			act.getString(R.string.date) + ": " + finishedDateTask;
			colorId = R.color.green;
			Log.e(tag, "color ---green");

			// String arrDateDone[] = finishedDateTask.split("-");
			// int yearDone = Integer.valueOf(arrDateDone[0]);
			// int monthDone = Integer.valueOf(arrDateDone[1]);
			// int dayDone = Integer.valueOf(arrDateDone[2]);
			// Date dateDone = new Date(yearDone, monthDone, dayDone);
			// numOfDateLate_done = (int) ((dateDone.getTime() - dateScheTo
			// .getTime()) / oneDayInMillSec);
			// // Date dateDone = new Date
			//
			// if (numOfDateLate_done <= 0) {
			// // HOAN THANH DUNG HAN
			// iProgress = Constant.PROGRESS_DONE_OK;
			// progressText =
			// // act
			// // .getString(Constant.ARR_PROGRESS_RESID[iProgress])
			// // + "\n"
			// // +
			// act.getString(R.string.date) + ": " + finishedDateTask;
			// colorId = R.color.green;
			// Log.e(tag, "color ---green");
			//
			// } else {
			// iProgress = Constant.PROGRESS_DONE_LATE;
			// // progressText = act
			// // .getString(Constant.ARR_PROGRESS_RESID[iProgress])
			// //
			// // + " "
			// // + numOfDateLate_done
			// // + " "
			// // + act.getString(R.string.date).toLowerCase();
			//
			// // colorId = R.color.red_normal;
			// colorId = R.color.green;
			// Log.e(tag, "color ---red");
			// }
		}

	}

	public int getColorId() {
		return colorId;
	}
	public void setColorId(int colorId) {
		this.colorId = colorId;
	}
	// public static final int ARR_PROGRESS_RESID[] = new int[]{
	// R.string.chua_thong, R.string.policy_sche_coming,
	// R.string.policy_sche_late, R.string.policy_done,
	// R.string.policy_done};
    private int iProgress;
	private int numOfDateLate_notDone = 0;
	int numOfDateLate_done = 0;
	public void setNumOfDateLate(int numOfDateLate) {
		this.numOfDateLate_notDone = numOfDateLate;
	}
	public int getNumOfDateLate() {
		return numOfDateLate_notDone;
	}
	public int getiProgress() {
		return iProgress;
	}
	public void setiProgress(int iProgress) {
		this.iProgress = iProgress;
	}
	public int getProgress() {
		return progress;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	int arrProgress[] = new int[]{R.string.chua_thong, R.string.da_thong,
			R.string.policy_refused};
	public String getProgressText() {
		// return LoginActivity.getInstance().getString(arrProgress[progress -
		// 1]);
		return progressText;
	}
	private String progressText;
	public void setTaskRoadId(String taskRoadId) {
		this.taskRoadId = taskRoadId;
	}
	public String getTaskRoadId() {
		return taskRoadId;
	}
	public void setFinishedDateTask(String finishedDateTask) {
		this.finishedDateTask = finishedDateTask;
	}
	public String getFinishedDateTask() {
		return finishedDateTask;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public boolean getStatus() {
		// return status;
		return progress == 1;
	}
	public String getTastId() {
		return tastId;
	}

	public void setTastId(String tastId) {
		this.tastId = tastId;
	}

	public String getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(String taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public String getNameTask() {
		return nameTask;
	}

	public void setNameTask(String nameTask) {
		this.nameTask = nameTask;
	}

	public String getContentTask() {
		return contentTask;
	}

	public void setContentTask(String contentTask) {
		this.contentTask = contentTask;
	}

	public String getCreatedDateTask() {
		return createdDateTask;
	}

	public void setCreatedDateTask(String createdDateTask) {
		this.createdDateTask = createdDateTask;
	}

	public String getImageTask() {
		return imageTask;
	}

	public void setImageTask(String imageTask) {
		this.imageTask = imageTask;
	}

	public String getVideoTask() {
		return videoTask;
	}

	public void setVideoTask(String videoTask) {
		this.videoTask = videoTask;
	}

	public String getStatuTask() {
		return statuTask;
	}

	public void setStatuTask(String statuTask) {
		this.statuTask = statuTask;
	}

	public String getAllContent() {
		String res = tastId + ", ";
		res += taskTypeId + ", ";
		res += nameTask + ", ";
		res += contentTask + ", ";
		res += createdDateTask + ", ";
		res += imageTask + ", ";
		res += videoTask + ", ";
		res += statuTask + ", ";
		// res +=+ ", ";
		// res +=+ ", ";
		// res +=+ ", ";

		return res;
	}
}
