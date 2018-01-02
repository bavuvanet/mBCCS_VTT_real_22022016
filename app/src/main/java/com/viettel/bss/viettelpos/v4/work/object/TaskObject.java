package com.viettel.bss.viettelpos.v4.work.object;

public class TaskObject {
	private String tastId;
	private String taskTypeId;
	private String nameTask;
	private String contentTask;
	private String createdDateTask;
	private String imageTask;
	private String videoTask;
	private String statuTask;
	private String finishDate;
	
	public TaskObject(String tastId, String taskTypeId, String nameTask,
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
	
	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
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

}
