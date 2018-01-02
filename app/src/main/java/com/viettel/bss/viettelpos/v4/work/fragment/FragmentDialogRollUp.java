package com.viettel.bss.viettelpos.v4.work.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.work.asynctask.AsyncRollUp;
import com.viettel.bss.viettelpos.v4.work.object.RollUpBO;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;
import com.viettel.maps.base.LatLng;
import com.viettel.maps.services.GeoObjItem;
import com.viettel.maps.services.GeoService;
import com.viettel.maps.services.GeoServiceResult;
import com.viettel.maps.services.ServiceStatus;

public class FragmentDialogRollUp extends DialogFragment {
	private RollUpBO bo;
	private EditText edtLocation, edtNote, edtAddress;
	private OnPostExecute<RollUpOutput> onPostExecute;
	private View prb, btnRollUp, view;
	private String address = "";

	public FragmentDialogRollUp(RollUpBO bo,
			OnPostExecute<RollUpOutput> onPostExecute) {
		this.onPostExecute = onPostExecute;
		this.bo = bo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.dialog_roll_up, container, false);
		}
		edtLocation = (EditText) view.findViewById(R.id.edtLocation);
		edtNote = (EditText) view.findViewById(R.id.edtNote);
		edtAddress = (EditText) view.findViewById(R.id.edtAddress);
		prb = view.findViewById(R.id.prb);
		getDialog().setTitle(bo.getProgramName());
		if (!CommonActivity.isNullOrEmpty(bo.getX()) && !"0".equals(bo.getX())
				&& !CommonActivity.isNullOrEmpty(bo.getY())
				&& !"0".equals(bo.getY())) {
			edtLocation.setText(bo.getX() + ", " + bo.getY());
			CommonActivity.checkConnectionMap(getActivity());
			GeoService service = new GeoService();

			service.setLimit(1);
			// Geocoder geocoder = new Geocoder(getActivity(),
			// Locale.getDefault());
			// List<Ad>geocoder.
			// geocoder.getFromLocation(latitude, longitude, maxResults)

			LatLng latLng = new LatLng(Double.parseDouble(bo.getX()),
					Double.parseDouble(bo.getY()));
			service.getAddress(latLng, new GeoService.GeoServiceListener() {
				@Override
				public void onGeoServicePreProcess(GeoService geoService) {
					// Truoc khi thuc hien lay dia chi, hien thi InfoBox thong
					// bao cho

				}

				@Override
				public void onGeoServiceCompleted(GeoServiceResult result) {
					// Sau khi hoan thanh, hien thi dia chi tim duoc
					if (result.getStatus() == ServiceStatus.OK) {
						GeoObjItem item = result.getItem(0);
						address = item.getAddress();
						edtAddress.setText(address);
					} else {
						address = "";
						edtAddress.setText(getString(R.string.invalid));
					}
					prb.setVisibility(View.GONE);
				}
			});

		} else {
			edtLocation.setText(getString(R.string.invalid));
			edtAddress.setText(getString(R.string.invalid));
			prb.setVisibility(View.GONE);
		}
		btnRollUp = view.findViewById(R.id.btnRollUp);
		btnRollUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!CommonActivity.isNullOrEmpty(address)) {
					bo.setAddress(address);
				}
				bo.setNote(edtNote.getText().toString());
				CommonActivity.createDialog(getActivity(),
						R.string.confirm_roll_up, R.string.app_name,
						R.string.cancel, R.string.ok, null, confirm).show();
			}
		});
		return view;

	}

	private OnClickListener confirm = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			AsyncRollUp asy = new AsyncRollUp(bo, onPostExecute, getActivity());
			asy.execute();
		}
	};
}
