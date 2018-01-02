package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

class PolicyOJ implements Serializable {
	String[] rowquery = new String[]{"t.task_id", "t.task_type_id", "t.name",
			"t.content", "t.image_attachment", "t.video_attachment",
			"tr.task_road_id", "tr.progress", "tr.real_finished_date", "tr.x",
			"tr.y"};
	String name, content, imgUrl, videoUrl;
	String realFinishedDate;
	double x, y;
	boolean done;
	public PolicyOJ(String name, String content, String imgUrl,
			String videoUrl, String realFinishedDate, boolean done, double x,
			double y) {

	}
}
