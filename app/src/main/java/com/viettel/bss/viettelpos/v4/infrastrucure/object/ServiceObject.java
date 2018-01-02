package com.viettel.bss.viettelpos.v4.infrastrucure.object;

public class ServiceObject {
	private String telecom_service_id;
	private String tel_service_code;
	private String tel_service_name;
	private String description;
	private String create_date;
	private String status;
	private String service_oder;

	public ServiceObject(String telecom_service_id, String tel_service_code,
			String tel_service_name, String description, String create_date,
			String status, String service_oder) {
		super();
		this.telecom_service_id = telecom_service_id;
		this.tel_service_code = tel_service_code;
		this.tel_service_name = tel_service_name;
		this.description = description;
		this.create_date = create_date;
		this.status = status;
		this.service_oder = service_oder;
	}

	public String getTelecom_service_id() {
		return telecom_service_id;
	}

	public void setTelecom_service_id(String telecom_service_id) {
		this.telecom_service_id = telecom_service_id;
	}

	public String getTel_service_code() {
		return tel_service_code;
	}

	public void setTel_service_code(String tel_service_code) {
		this.tel_service_code = tel_service_code;
	}

	public String getTel_service_name() {
		return tel_service_name;
	}

	public void setTel_service_name(String tel_service_name) {
		this.tel_service_name = tel_service_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getService_oder() {
		return service_oder;
	}

	public void setService_oder(String service_oder) {
		this.service_oder = service_oder;
	}

}
