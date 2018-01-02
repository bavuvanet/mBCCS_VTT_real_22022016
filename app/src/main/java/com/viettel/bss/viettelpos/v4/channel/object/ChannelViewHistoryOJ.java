package com.viettel.bss.viettelpos.v4.channel.object;

public class ChannelViewHistoryOJ {
	private String date;
	private String content;
	private String taskName;
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public ChannelViewHistoryOJ() {
		
	}

	public ChannelViewHistoryOJ(String date, String content) {
		this.content = content;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
