package com.viettel.bss.viettelpos.v4.work.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.WorkObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FragmentDetailsWork extends Fragment implements OnClickListener {

	private Button btnHome;
    private EditText txtbaocao;
	private WorkObject workObject;
	String status = "";
	private String progressSpin = "";
	private Date dateEnd = null;
	private Date dateStart = null;
	private Location locationStaff;
	private double x = 0L;
	private double y = 0L;
	private float distance = 31;

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.detailwork);
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("TAG", "onActivityCreated FragmentUpdateWork");

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("TAG", "onAttach FragmentDetailsWork");
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("TAG", "onCreateView FragmentUpdateWork");
		// ======get workobject =========
		Bundle mBundle = getArguments();
		if (mBundle != null) {
			workObject = (WorkObject) mBundle
					.getSerializable(Define.KEY_WORKOBJECT);
		}

		View mView = inflater.inflate(R.layout.update_job_layout_details,
				container, false);
		unit(mView);
		return mView;
	}

	private void unit(View view) {

		initLocation();
		distance = checkLocation();

        TextView txtmadiemden = (TextView) view.findViewById(R.id.txtmadiemden);
        TextView txttendiemden = (TextView) view.findViewById(R.id.txttendiemden);
        TextView txtdiachi = (TextView) view.findViewById(R.id.txtdiachi);
        TextView txtnoidung = (TextView) view.findViewById(R.id.txtnoidung);
        TextView txtstartdate = (TextView) view.findViewById(R.id.txtstartdate);
        TextView txtenddate = (TextView) view.findViewById(R.id.txtenddate);
		// spintrangthai = (Spinner) view.findViewById(R.id.spintrangthai);
		txtbaocao = (EditText) view.findViewById(R.id.txtbaocao);
		txtbaocao.setMovementMethod(new ScrollingMovementMethod());
        Button btncapnhat = (Button) view.findViewById(R.id.btnhoanthanh);
        Button btntuchoi = (Button) view.findViewById(R.id.btntuchoi);
		btntuchoi.setOnClickListener(this);
		btncapnhat.setOnClickListener(this);
		// initSpinerStatus();
		if (workObject != null) {
			txtmadiemden.setText(workObject.getPointToId());
			txttendiemden.setText(workObject.getNamePointTo());
			txtdiachi.setText(workObject.getAddress());
			txtdiachi.setMovementMethod(new ScrollingMovementMethod());
			txtnoidung.setText(workObject.getContent());
			txtnoidung.setMovementMethod(new ScrollingMovementMethod());

			String dateStartSp = workObject.getStartDate();
			Log.d("dateStartSp", dateStartSp);
			x = workObject.getX();
			Log.d("toa do x", "" + x);
			y = workObject.getY();
			Log.d("toa do y", "" + y);
			if (dateStartSp != null && !dateStartSp.isEmpty()) {
				String[] splitDate = dateStartSp.split("-", 3);
				if (splitDate.length == 3) {
					txtstartdate.setText(splitDate[2] + "/" + splitDate[1]
							+ "/" + splitDate[0]);
				}
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateStart = sdf.parse(dateStartSp);
			} catch (Exception e) {
				e.printStackTrace();
			}

			String dateEndSp = workObject.getEndDate();
			Log.d("dateEndSp", dateEndSp);
			if (dateEndSp != null && !dateEndSp.isEmpty()) {
				String[] splitDate = dateEndSp.split("-", 3);
				if (splitDate.length == 3) {
					txtenddate.setText(splitDate[2] + "/" + splitDate[1] + "/"
							+ splitDate[0]);
				}
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				dateEnd = sdf.parse(dateEndSp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// === init location update work
	private void initLocation() {
		locationStaff = new Location("Location Staff");

	}

	// check location for update work
	private float checkLocation() {
		com.viettel.bss.viettelpos.v4.object.Location myLocation = CommonActivity
				.findMyLocation(getActivity());
		if (!myLocation.getX().equals("0") && !myLocation.getY().equals("0")) {
			Location location = new Location("");
			location.setLatitude(Double.parseDouble(myLocation.getX()));
			location.setLongitude(Double.parseDouble(myLocation.getY()));
			if (x != 0 && y != 0) {
				Log.i("TAG", "staff location x = " + x + "y = " + y);
				locationStaff.setLatitude(x);
				locationStaff.setLongitude(y);
				if (myLocation != null) {
					distance = location.distanceTo(locationStaff);
				}
			}
		} else {
			CommonActivity.DoNotLocation(getActivity());
		}
		Log.e("check location", distance + "");
		return distance;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnhoanthanh:
			progressSpin = "2";
			// === check distance < 30L
			if (distance < Constant.DISTANCE_VALID) {
				if (txtbaocao.getText().toString() != null
						&& !txtbaocao.getText().toString().isEmpty()) {
					if (StringUtils.CheckCharSpecical(txtbaocao.getText()
                            .toString())) {
						Toast.makeText(
								getActivity(),
								getResources().getString(
										R.string.checkcharspecical),
								Toast.LENGTH_SHORT).show();
					} else {
						if (CommonActivity.isNetworkConnected(getActivity())) {
							Dialog dialog = CommonActivity.createDialog(
									getActivity(),
									getResources().getString(
											R.string.ischeckupdatewwork),
									getResources().getString(
											R.string.updateWork),
									getResources().getString(R.string.ok),
									getResources().getString(R.string.cancel),
									UpdateWorkSyn, null);
							dialog.show();
						} else {
							Dialog dialog = CommonActivity
									.createAlertDialog(
											getActivity(),
											getResources().getString(
													R.string.errorNetwork),
											getResources().getString(
													R.string.app_name));
							dialog.show();
						}
					}
				} else {
					if (CommonActivity.isNetworkConnected(getActivity())) {
						Dialog dialog = CommonActivity.createDialog(
								getActivity(),
								getResources().getString(
										R.string.ischeckupdatewwork),
								getResources().getString(R.string.updateWork),
								getResources().getString(R.string.ok),
								getResources().getString(R.string.cancel),
								UpdateWorkSyn, null);
						dialog.show();
					} else {

						Dialog dialog = CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name));
						dialog.show();

					}
				}
			} else {
				// ======= distance > 30
				Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
				getResources().getString(R.string.distance_not_valid),
						getResources().getString(R.string.updateWork));
				dialog.show();
			}
			break;
		case R.id.btntuchoi:
			progressSpin = "3";

			if (txtbaocao.getText().toString() != null
					&& !txtbaocao.getText().toString().isEmpty()) {
				if (StringUtils.CheckCharSpecical(txtbaocao.getText()
                        .toString())) {
					Toast.makeText(
							getActivity(),
							getResources()
									.getString(R.string.checkcharspecical),
							Toast.LENGTH_SHORT).show();
				} else {
					if (CommonActivity.isNetworkConnected(getActivity())) {
						Dialog dialog = CommonActivity.createDialog(
								getActivity(),
								getResources().getString(
										R.string.ischeckupdatewwork),
								getResources().getString(R.string.updateWork),
								getResources().getString(R.string.ok),
								getResources().getString(R.string.cancel),
								UpdateWorkSyn2, null);
						dialog.show();
					} else {

						Dialog dialog = CommonActivity
								.createAlertDialog(
										getActivity(),
										getResources().getString(
												R.string.errorNetwork),
										getResources().getString(
												R.string.app_name));
						dialog.show();

					}
				}
			} else {
				Toast.makeText(getActivity(),
						getResources().getString(R.string.checkdescription),
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		}
	}

	// ==== update work=============
    private final OnClickListener UpdateWorkSyn = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			UpdateWorkSyn updateWorkSyn = new UpdateWorkSyn(getActivity(),
					workObject);
			updateWorkSyn.execute();
		}
	};
	// ==== update work=============
    private final OnClickListener UpdateWorkSyn2 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			UpdateWorkSyn2 updateWorkSyn = new UpdateWorkSyn2(getActivity(),
					workObject);
			updateWorkSyn.execute();
		}
	};

	// =========WS cap nhat cong viec
	private class UpdateWorkSyn2 extends AsyncTask<WorkObject, Void, String> {
		private Context context = null;
		final ProgressDialog progress;
		WorkObject workObject = null;
		String errorCode = "";
		String description = "";
		final XmlDomParse parse = new XmlDomParse();

		public UpdateWorkSyn2(Context context, WorkObject mWorkObject) {
			this.context = context;
			this.workObject = mWorkObject;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.updating));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(WorkObject... params) {
			return sendRequestUpdateWork(workObject);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result.equals("0")) {
				Log.d("result", result);

				Dialog dialog = CommonActivity.createAlertDialog(
						(Activity) context,
						context.getResources().getString(
								R.string.refuseworksucess), context
								.getResources().getString(R.string.updateWork),
						updateworkclick);
				dialog.show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.updateWork),
							moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), result,
							getResources().getString(R.string.updateWork));
					dialog.show();
				}

			}
		}

		private String sendRequestUpdateWork(WorkObject workObjectitem) {
			String original = "";
			String errorMessage = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateTaskRoadProgress");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateTaskRoadProgress>");
				rawData.append("<updateTaskRoadInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<objectId>").append(workObjectitem.getPointToId());
				rawData.append("</objectId>");
				rawData.append("<taskRoadId>").append(workObjectitem.getWorkid());
				rawData.append("</taskRoadId>");
				if (txtbaocao.getText().toString() != null
						&& !txtbaocao.getText().toString().isEmpty()) {
					rawData.append("<description>").append(txtbaocao.getText().toString());
					rawData.append("</description>");
				}
				rawData.append("<progress>").append(progressSpin);
				rawData.append("</progress>");
				rawData.append("</updateTaskRoadInput>");
				rawData.append("</ws:updateTaskRoadProgress>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_updateTaskRoadProgress");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					return Constant.ERROR_CODE;
				}
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// ===========parser xml ===================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("erorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}
				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}

				if (!output.getErrorCode().equals("0")) {
					original = output.getDescription();
				}
				if (output.getErrorCode().equals("0")) {
					original = output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return original;
		}
	}

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			getActivity().startActivity(intent);
			getActivity().finish();

		}
	};

	// =========WS cap nhat cong viec
	private class UpdateWorkSyn extends AsyncTask<WorkObject, Void, String> {
		private Context context = null;
		final ProgressDialog progress;
		WorkObject workObject = null;
		String errorCode = "";
		String description = "";
		final XmlDomParse parse = new XmlDomParse();

		public UpdateWorkSyn(Context context, WorkObject mWorkObject) {
			this.context = context;
			this.workObject = mWorkObject;
			this.progress = new ProgressDialog(this.context);
			// check font

			this.progress.setMessage(context.getResources().getString(
					R.string.updating));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(WorkObject... params) {
			return sendRequestUpdateWork(workObject);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if (result.equals("0")) {
				Log.d("result", result);

				Dialog dialog = CommonActivity.createAlertDialog(
						(Activity) context,
						context.getResources().getString(
								R.string.updateworksucess), context
								.getResources().getString(R.string.updateWork),
						updateworkclick);
				dialog.show();
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)
						&& !description.isEmpty()) {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.updateWork),
							moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), result,
							getResources().getString(R.string.updateWork));
					dialog.show();
				}

			}
		}

		private String sendRequestUpdateWork(WorkObject workObjectitem) {
			String original = "";
			String errorMessage = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode",
						"mbccs_updateTaskRoadProgress");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:updateTaskRoadProgress>");
				rawData.append("<updateTaskRoadInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<objectId>").append(workObjectitem.getPointToId());
				rawData.append("</objectId>");
				rawData.append("<taskRoadId>").append(workObjectitem.getWorkid());
				rawData.append("</taskRoadId>");
				if (txtbaocao.getText().toString() != null
						&& !txtbaocao.getText().toString().isEmpty()) {
					rawData.append("<description>").append(txtbaocao.getText().toString());
					rawData.append("</description>");
				}
				rawData.append("<progress>").append(progressSpin);
				rawData.append("</progress>");
				rawData.append("</updateTaskRoadInput>");
				rawData.append("</ws:updateTaskRoadProgress>");

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL,getActivity(),"mbccs_updateTaskRoadProgress");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				if (!output.getError().equals("0")) {
					errorMessage = output.getDescription();
					return Constant.ERROR_CODE;
				}
				original = output.getOriginal();
				Log.d("originalllllllll", original);
				// ===========parser xml ===================
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("erorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}

				VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
						.parseXMLHandler(new VSAMenuHandler(), original);
				output = handler.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}

				if (!output.getErrorCode().equals("0")) {
					original = output.getDescription();
				}
				if (output.getErrorCode().equals("0")) {
					original = output.getErrorCode();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return original;
		}
	}

	// update work

	private final OnClickListener updateworkclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// call Asyntask========
			JobDal jobDal = new JobDal(getActivity());
			try {
				jobDal.open();
				jobDal.updateTaskRoad(workObject.getWorkid(), progressSpin);
				jobDal.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			FragmentUpdateWork.instance.lisWorkObjects
					.remove(FragmentDetailsWork.this.workObject);
			FragmentUpdateWork.instance.lisAdapterListWork
					.notifyDataSetChanged();

			getActivity().onBackPressed();
		}
	};

}
