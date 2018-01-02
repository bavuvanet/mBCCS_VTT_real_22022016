package com.viettel.bss.viettelpos.v4.sale.activity;

import android.support.v4.app.Fragment;
import android.view.Window;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.adapter.SaleTransAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SaleTransAdapter.OnChangeCheckedState;
import com.viettel.bss.viettelpos.v4.sale.business.TelecomServiceBusiness;
import com.viettel.bss.viettelpos.v4.sale.handler.ListSaleTransHandler;
import com.viettel.bss.viettelpos.v4.sale.handler.TotalMoneyBillHanlder;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;
import com.viettel.bss.viettelpos.v4.sale.object.SaleTrans;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.object.TelecomServiceObject;
import com.viettel.bss.viettelpos.v4.sale.object.TotalMoneyBean;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FragmentCreateInvoice extends Fragment implements OnClickListener,
		OnChangeCheckedState {
	private static final String CREATE_INVOICE_SINGLE = "0";
	private static final String CREATE_INVOICE_GROUP = "1";
	private final String LOG_TAG = FragmentCreateInvoice.class.getName();
	private Spinner spiService;
	private Spinner spiSaleTransType;
	private TextView tvChooseChannel;
	// private EditText edtSaleTransDate;
	private View imgChangeDate;
	private Long telecomServiceId;
	private String transType = "";
    private Staff selectedStaff;
	private ChannelTypeObject channelType;

	private View view;
	private View btnCreateInvoice;
	private View btnViewSaleTrans;
	private View btnSearchSumSaleTrans;
	private String errorMessage;
	private TotalMoneyBean totalMoneyBill;
	private TextView tvMoneyPreTax;
	private TextView tvMoneyAfterTax;
	private TextView tvMoneyDiscount;
	private TextView tvAmount;
	private TextView tvMoneyTax;
	private TextView tvTax;
	private final List<SaleTrans> lstSaleTrans = new ArrayList<>();
	private Dialog dialog;
	private SaleTransAdapter adapter;
	private View btnCreateInvoiceOnDialog;
	private CheckBox checkBoxAll;
	private View btnCancelOnDialog;
	private GetListSaleTransAsy getListSaleTransAsy;
	private CreateInvoiceAsyn createInvoiceAsy;
	private final String CREATE_ALL = "0";
	private final String CREATE_LIST_SALE_TRANS = "1";
	private View imgDeleteChannel;
	private ListView lvSaleTrans;

	private Button btnHome;
	private TextView txtNameActionBar;
	private Boolean isTimeout = false;
	private EditText edtSearch;
	private final String permission = Constant.VSAMenu.SALE_CREATE_INVOICE;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.create_invoice_layout, container,
					false);
			spiService = (Spinner) view.findViewById(R.id.spiService);
			spiSaleTransType = (Spinner) view.findViewById(R.id.spiTransType);
			tvChooseChannel = (TextView) view
					.findViewById(R.id.tvChooseChannel);
			// edtSaleTransDate = (EditText) view
			// .findViewById(R.id.edtSaleTransDate);
			// imgChangeDate = view.findViewById(R.id.imgChangeDate);
			// imgChangeDate.setOnClickListener(this);
			tvChooseChannel.setOnClickListener(this);
			btnSearchSumSaleTrans = view.findViewById(R.id.btnSearch);
			btnCreateInvoice = view.findViewById(R.id.btnOk);
			btnViewSaleTrans = view.findViewById(R.id.btnViewSaleTrans);
			btnSearchSumSaleTrans.setOnClickListener(this);
			btnViewSaleTrans.setOnClickListener(this);
			btnCreateInvoice.setOnClickListener(this);
			tvMoneyPreTax = (TextView) view.findViewById(R.id.tvMoneyBeforTax);
			tvMoneyTax = (TextView) view.findViewById(R.id.tvMoneyTax);
			tvMoneyAfterTax = (TextView) view.findViewById(R.id.tvTotalMoney);
			tvMoneyDiscount = (TextView) view.findViewById(R.id.tvDiscount);
			tvAmount = (TextView) view.findViewById(R.id.tvAmount);
			tvTax = (TextView) view.findViewById(R.id.tvTax);
			imgDeleteChannel = view.findViewById(R.id.imgDeleteChannel);
			imgDeleteChannel.setVisibility(View.GONE);
			imgDeleteChannel.setOnClickListener(this);
			initValue();
		}

		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (createInvoiceAsy != null) {
			createInvoiceAsy.cancel(true);
		}
	}

	private void initValue() {
		tvMoneyPreTax.setText("");
		tvMoneyTax.setText("");
		tvMoneyAfterTax.setText("");
		tvMoneyDiscount.setText("");
		tvAmount.setText("");
		tvTax.setText("");
		btnCreateInvoice.setVisibility(View.GONE);
		btnViewSaleTrans.setVisibility(View.GONE);
		Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
		// edtSaleTransDate.setText(DateTimeUtils.convertDateTimeToString(
		// cal.getTime(), "dd/MM/yyyy"));
		final List<TelecomServiceObject> lstService = TelecomServiceBusiness
				.getAllTelecomService(getActivity());
		ArrayAdapter<String> telecomServiceAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.simple_list_item_single_row, R.id.text1);
		for (TelecomServiceObject telecomServiceObject : lstService) {
			telecomServiceAdapter.add(telecomServiceObject.getName());
		}
		ArrayAdapter<String> saleTransTypeAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.simple_list_item_single_row, R.id.text1);

		spiService.setAdapter(telecomServiceAdapter);

		final List<SaleTrans> lstTransType = new ArrayList<>();
		// Ban le
		lstTransType.add(new SaleTrans("1", getResources().getString(
				R.string.sale_retail)));

		lstTransType.add(new SaleTrans("3", getResources().getString(
				R.string.saleForChannel)));
		lstTransType.add(new SaleTrans("7", getResources().getString(
				R.string.doService)));
		for (SaleTrans saleTrans : lstTransType) {
			saleTransTypeAdapter.add(saleTrans.getSaleTransName());
		}
		spiSaleTransType.setAdapter(saleTransTypeAdapter);
		spiService.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				telecomServiceId = lstService.get(position)
						.getTelecomServiceId();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spiSaleTransType
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						transType = lstTransType.get(position)
								.getSaleTransType();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});
	}

	@Override
	public void onClick(View v) {
		if (v == imgChangeDate) {
			// DatePickerDialog dialog = new DatePickerDialog(getActivity(),
			// datePickerListener, year, month, day);
			// dialog.show();
		} else if (v == tvChooseChannel) {
			FragmentChooseChannel fragment = new FragmentChooseChannel();
			fragment.setTargetFragment(this, 1);
			ReplaceFragment.replaceFragment(getActivity(), fragment, false);
		} else if (v == btnSearchSumSaleTrans) {

			if (!CommonActivity.isNetworkConnected(getActivity())) {
				CommonActivity.createAlertDialog(getActivity(),
						R.string.errorNetwork, R.string.app_name).show();
				return;
			}
			btnCreateInvoice.setVisibility(View.GONE);
			btnViewSaleTrans.setVisibility(View.GONE);
			totalMoneyBill = null;
			tvMoneyPreTax.setText("");
			tvMoneyTax.setText("");
			tvMoneyAfterTax.setText("");
			tvMoneyDiscount.setText("");
			tvAmount.setText("");
			tvTax.setText("");
			lstSaleTrans.clear();
			SearchSumSaleTransAsyn asy = new SearchSumSaleTransAsyn(
					getActivity());
			asy.execute();
		} else if (v == btnCreateInvoice) {
			// edt rong va check false

			refreshCheckBox();

			int numOfSaleTrans = Integer.parseInt(totalMoneyBill.getAmount());
			if (numOfSaleTrans == 1) {

				OnClickListener onClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (!CommonActivity.isNetworkConnected(getActivity())) {
							CommonActivity.createAlertDialog(getActivity(),
									R.string.errorNetwork, R.string.app_name)
									.show();
							return;
						}
						CreateInvoiceAsyn asy = new CreateInvoiceAsyn(
								getActivity());
						asy.execute(CREATE_INVOICE_SINGLE, CREATE_ALL);

					}
				};
				String message = getResources().getString(
						R.string.confirm_create_invoice_single);

				Dialog dialog = CommonActivity.createDialog(getActivity(),
						MessageFormat.format(message, 1), getResources()
								.getString(R.string.app_name), getResources()
								.getString(R.string.ok), getResources()
								.getString(R.string.cancel), onClick, null);
				dialog.show();

				// final AlertDialog.Builder confirm = new AlertDialog.Builder(
				// getActivity());
				// confirm.setTitle(getResources().getString(R.string.app_name));
				//
				// confirm.setMessage(MessageFormat.format(message, 1));
				// confirm.setPositiveButton(
				// getResources().getString(R.string.ok),
				// new DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface arg0, int arg1) {
				// CreateInvoiceAsyn asy = new CreateInvoiceAsyn(
				// getActivity());
				// asy.execute(CREATE_INVOICE_SINGLE, CREATE_ALL);
				// }
				// });
				// confirm.setNegativeButton(
				// getResources().getString(R.string.cancel), null);
				// confirm.create().show();
				return;
			}

			OnClickListener leftClick = new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					OnClickListener onClick1 = new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							if (createInvoiceAsy != null) {
								createInvoiceAsy.cancel(true);
							}
							createInvoiceAsy = new CreateInvoiceAsyn(
									getActivity());
							createInvoiceAsy.execute(CREATE_INVOICE_GROUP,
									CREATE_ALL);

						}
					};
					Dialog dialog1 = CommonActivity.createDialog(
							getActivity(),
							getResources().getString(
									R.string.confirm_create_all_invoice_group),
							getResources().getString(R.string.app_name),
							getResources().getString(R.string.ok),
							getResources().getString(R.string.cancel),
							onClick1, null);
					dialog1.show();

					// final AlertDialog.Builder confirm = new
					// AlertDialog.Builder(
					// getActivity());
					// confirm.setTitle(getResources()
					// .getString(R.string.app_name));
					// confirm.setMessage(getResources().getString(
					// R.string.confirm_create_all_invoice_group));
					// confirm.setPositiveButton(
					// getResources().getString(R.string.ok),
					// new DialogInterface.OnClickListener() {
					//
					// @Override
					// public void onClick(DialogInterface arg0,
					// int arg1) {
					//
					// }
					// });
					// confirm.setNegativeButton(
					// getResources().getString(R.string.cancel), null);
					// confirm.create().show();

				}
			};

			OnClickListener rightClick = new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					OnClickListener onClick1 = new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							CreateInvoiceAsyn asy = new CreateInvoiceAsyn(
									getActivity());
							asy.execute(CREATE_INVOICE_SINGLE, CREATE_ALL);

						}
					};
					Dialog dialog1 = CommonActivity
							.createDialog(
									getActivity(),
									getResources()
											.getString(
													R.string.confirm_create_all_invoice_single),
									getResources().getString(R.string.app_name),
									getResources().getString(R.string.ok),
									getResources().getString(R.string.cancel),
									onClick1, null);
					dialog1.show();

					// final AlertDialog.Builder confirm = new
					// AlertDialog.Builder(
					// getActivity());
					// confirm.setTitle(getResources()
					// .getString(R.string.app_name));
					// confirm.setMessage(getResources().getString(
					// R.string.confirm_create_all_invoice_single));
					// confirm.setPositiveButton(
					// getResources().getString(R.string.ok),
					// new DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(DialogInterface arg0,
					// int arg1) {
					// CreateInvoiceAsyn asy = new CreateInvoiceAsyn(
					// getActivity());
					// asy.execute(CREATE_INVOICE_SINGLE,
					// CREATE_ALL);
					// }
					// });
					// confirm.setNegativeButton(
					// getResources().getString(R.string.cancel), null);
					// confirm.create().show();

				}
			};

			Dialog dialog = CommonActivity.createDialog(getActivity(),
					getResources()
							.getString(R.string.chooseMethodCreateInvoice),
					getResources().getString(R.string.app_name), getResources()
							.getString(R.string.createInvoiceGroup),
					getResources().getString(R.string.createInvoiceSingle),
					leftClick, rightClick);
			dialog.show();

		} else if (v == btnViewSaleTrans) {
			if (checkBoxAll != null) {
				checkBoxAll.setChecked(false);
			}

			if (dialog != null && !dialog.isShowing()) {
				if (lstSaleTrans == null || lstSaleTrans.isEmpty()) {
					if (getListSaleTransAsy != null) {
						getListSaleTransAsy.cancel(true);
					}
					if (!CommonActivity.isNetworkConnected(getActivity())) {
						CommonActivity.createAlertDialog(getActivity(),
								R.string.errorNetwork, R.string.app_name)
								.show();
						return;
					}
					getListSaleTransAsy = new GetListSaleTransAsy(getActivity());
					getListSaleTransAsy.execute();
				}
				dialog.show();
			} else {
				createSaleTransDialog();
				if (getListSaleTransAsy != null) {
					getListSaleTransAsy.cancel(true);
				}
				getListSaleTransAsy = new GetListSaleTransAsy(getActivity());
				getListSaleTransAsy.execute();
			}
		} else if (v == btnCancelOnDialog) {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}

			refreshCheckBox();
		} else if (v == checkBoxAll) {
			if (lstSaleTrans != null && !lstSaleTrans.isEmpty()) {
				for (SaleTrans item : lstSaleTrans) {
					item.setChecked(checkBoxAll.isChecked());
				}
				adapter.notifyDataSetChanged();

			}
		} else if (v == btnCreateInvoiceOnDialog) {
			boolean isChecked = false;
			for (SaleTrans item : lstSaleTrans) {
				if (item.isChecked()) {
					isChecked = true;
					break;
				}
			}
			if (!isChecked) {
				Toast.makeText(
						getActivity(),
						getResources().getString(
								R.string.choose_atleast_sale_trans),
						Toast.LENGTH_LONG).show();
				return;
			}
			if (createInvoiceAsy != null) {
				createInvoiceAsy.cancel(true);
			}
			int numOfSaleTrans = countSaleTrans();
			if (numOfSaleTrans == 1) {
				final AlertDialog.Builder confirm = new AlertDialog.Builder(
						getActivity());
				confirm.setTitle(getResources().getString(R.string.app_name));
				String message = getResources().getString(
						R.string.confirm_create_invoice_single);

				confirm.setMessage(MessageFormat.format(message, 1));
				confirm.setPositiveButton(
						getResources().getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								CreateInvoiceAsyn asy = new CreateInvoiceAsyn(
										getActivity());
								// asy.execute(CREATE_INVOICE_SINGLE,
								// CREATE_ALL);
								asy.execute(CREATE_INVOICE_SINGLE,
										CREATE_LIST_SALE_TRANS);
							}
						});
				confirm.setNegativeButton(
						getResources().getString(R.string.cancel), null);
				confirm.create().show();
				return;
			}
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					getActivity());
			builder.setTitle(getResources().getString(R.string.app_name));
			builder.setMessage(getResources().getString(
					R.string.chooseMethodCreateInvoice));
			builder.setPositiveButton(
					getResources().getString(R.string.createInvoiceGroup),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							final AlertDialog.Builder confirm = new AlertDialog.Builder(
									getActivity());
							confirm.setTitle(getResources().getString(
									R.string.app_name));
							String message = getResources().getString(
									R.string.confirm_create_invoice_group);
							confirm.setMessage(MessageFormat.format(message,
									countSaleTrans()));
							confirm.setPositiveButton(
									getResources().getString(R.string.ok),
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											if (createInvoiceAsy != null) {
												createInvoiceAsy.cancel(true);
											}
											createInvoiceAsy = new CreateInvoiceAsyn(
													getActivity());
											createInvoiceAsy.execute(
													CREATE_INVOICE_GROUP,
													CREATE_LIST_SALE_TRANS);
										}
									});
							confirm.setNegativeButton(
									getResources().getString(R.string.cancel),
									null);
							confirm.create().show();
						}
					});
			builder.setNegativeButton(
					getResources().getString(R.string.createInvoiceSingle),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							final AlertDialog.Builder confirm = new AlertDialog.Builder(
									getActivity());
							confirm.setTitle(getResources().getString(
									R.string.app_name));
							String message = getResources().getString(
									R.string.confirm_create_invoice_single);
							confirm.setMessage(MessageFormat.format(message,
									countSaleTrans()));
							confirm.setPositiveButton(
									getResources().getString(R.string.ok),
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface arg0, int arg1) {
											if (createInvoiceAsy != null) {
												createInvoiceAsy.cancel(true);
											}
											createInvoiceAsy = new CreateInvoiceAsyn(
													getActivity());
											createInvoiceAsy.execute(
													CREATE_INVOICE_SINGLE,
													CREATE_LIST_SALE_TRANS);
										}
									});
							confirm.setNegativeButton(
									getResources().getString(R.string.cancel),
									null);
							confirm.create().show();
						}
					});
			builder.create().show();

		} else if (v == imgDeleteChannel) {
			selectedStaff = null;
			channelType = null;
			tvChooseChannel.setHint(getResources().getString(
					R.string.chooseChannelOrStaff));
			imgDeleteChannel.setVisibility(View.GONE);
		} else {
			switch (v.getId()) {
			case R.id.btnHome:
				CommonActivity.callphone(getActivity(), Constant.phoneNumber);
				break;
			case R.id.relaBackHome:
				getActivity().onBackPressed();
				break;
			default:
				break;
			}
		}
	}

	// private DatePickerDialog.OnDateSetListener datePickerListener = new
	// DatePickerDialog.OnDateSetListener() {
	//
	// public void onDateSet(DatePicker view, int selectedYear,
	// int selectedMonth, int selectedDay) {
	// year = selectedYear;
	// month = selectedMonth;
	// day = selectedDay;
	// StringBuilder strDate = new StringBuilder();
	// if (day < 10) {
	// strDate.append("0");
	// }
	// strDate.append(day).append("/");
	// if (month < 10) {
	// strDate.append("0");
	// }
	// strDate.append(month).append("/");
	// strDate.append(year);
	// edtSaleTransDate.setText(strDate);
	// }
	// };

	private void refreshCheckBox() {

		if (edtSearch != null && checkBoxAll != null) {
			edtSearch.setText("");
			checkBoxAll.setChecked(false);
		}

		if (lstSaleTrans != null && !lstSaleTrans.isEmpty()) {
			for (SaleTrans item : lstSaleTrans) {
				item.setChecked(false);
			}
			adapter.notifyDataSetChanged();

		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
        MainActivity.getInstance().setTitleActionBar(R.string.createInvoice);
		super.onResume();
	}

	private class SearchSumSaleTransAsyn extends
			AsyncTask<Boolean, Void, String> {
		final ProgressDialog progress;
		private final Activity activity;

		public SearchSumSaleTransAsyn(Activity activity) {
			this.activity = activity;
			this.progress = new ProgressDialog(this.activity);
			this.progress.setMessage(getResources().getString(
					R.string.searchingInvoice));
			progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(Boolean... params) {
			return searchSumSaleTrans();
		}

		@Override
		protected void onPostExecute(String result) {
			this.progress.dismiss();
			if (Constant.SUCCESS_CODE.equals(result)) {
				btnCreateInvoice.setVisibility(View.VISIBLE);
				btnViewSaleTrans.setVisibility(View.VISIBLE);
				view.findViewById(R.id.lnResult).setVisibility(View.VISIBLE);
				// Tim kiem thanh cong, cap nhat cac trang thai
				tvMoneyPreTax.setText(getResources().getString(
						R.string.moneyBeforTax)
						+ ": "
						+ StringUtils.formatMoney(totalMoneyBill
								.getMoneyPreTax()));
				tvMoneyTax.setText(getResources().getString(R.string.moneyTax)
						+ ": "
						+ StringUtils.formatMoney(totalMoneyBill.getTax()));
				tvMoneyAfterTax.setText(getResources().getString(
						R.string.moneyTotal)
						+ ": "
						+ StringUtils.formatMoney(totalMoneyBill
								.getMoneyAfterTax()));
				tvMoneyDiscount
						.setText(getResources().getString(R.string.discount)
								+ ": "
								+ StringUtils.formatMoney(totalMoneyBill
										.getDiscount()));
				tvAmount.setText(getResources().getString(R.string.tv_quantity)
						+ ": "
						+ StringUtils.formatMoney(totalMoneyBill.getAmount()));
				tvTax.setText(getResources().getString(R.string.tax) + ": "
						+ totalMoneyBill.getVat() + "%");
			} else if (Constant.ERROR_CODE.equals(result)) {
				OnClickListener onclick = null;
				if (isTimeout) {
					// onclick = new OnClickListener() {
					//
					// @Override
					// public void onClick(View v) {
					// Intent intent = new Intent(getActivity(),
					// LoginActivity.class);
					// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					// | Intent.FLAG_ACTIVITY_NO_HISTORY);
					// startActivity(intent);
					// getActivity().finish();
					// }
					// };
					LoginDialog dialog = new LoginDialog(getActivity(),
							permission);
					dialog.show();
					isTimeout = false;
				}
				// That bai, hien thi thong bao ket qua
				String message = errorMessage;
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(activity,
						message, title, onclick);
				dialog.show();
			}
		}
	}

	private class GetListSaleTransAsy extends AsyncTask<Void, Void, String> {
		private final Activity activity;

		public GetListSaleTransAsy(Activity activity) {
			this.activity = activity;
			dialog.findViewById(R.id.prbLoadSaleTrans).setVisibility(
					View.VISIBLE);
			dialog.findViewById(R.id.lvSaleTrans).setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(Void... params) {
			return getLisSaleTrans();
		}

		@Override
		protected void onPostExecute(String

		result) {
			dialog.findViewById(R.id.prbLoadSaleTrans).setVisibility(View.GONE);
			dialog.findViewById(R.id.lvSaleTrans).setVisibility(View.VISIBLE);
			if (Constant.SUCCESS_CODE.equals(result)) {
				adapter = new SaleTransAdapter(getActivity(), lstSaleTrans,
						FragmentCreateInvoice.this);
				lvSaleTrans.setAdapter(adapter);
			} else if (Constant.ERROR_CODE.equals(result)) {
				OnClickListener onclick = null;
				if (isTimeout) {
					// onclick = new OnClickListener() {
					//
					// @Override
					// public void onClick(View v) {
					// Intent intent = new Intent(getActivity(),
					// LoginActivity.class);
					// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					// | Intent.FLAG_ACTIVITY_NO_HISTORY);
					// startActivity(intent);
					// getActivity().finish();
					// }
					// };
					LoginDialog dialog = new LoginDialog(getActivity(),
							permission);
					dialog.show();
					isTimeout = false;
				}
				// That bai, hien thi thong bao ket qua
				String message = errorMessage;
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(activity,
						message, title, onclick);
				dialog.show();
			}
		}
	}

	private class CreateInvoiceAsyn extends AsyncTask<String, Void, String> {
		final ProgressDialog progress;
		private final Activity activity;

		public CreateInvoiceAsyn(Activity activity) {
			this.activity = activity;
			this.progress = new ProgressDialog(this.activity);
			this.progress.setMessage(getResources().getString(
					R.string.creatingInvoice));
			progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// param[0] hoa don gop, hoa don le
			// params[1] lap hoa don cho tat ca giao dich, cho danh sach giao
			// dich co dinh
			return createInvoice(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			this.progress.dismiss();
			if (Constant.SUCCESS_CODE.equals(result)) {

				lstSaleTrans.clear();
				totalMoneyBill = null;
				view.findViewById(R.id.lnResult).setVisibility(View.GONE);
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				btnCreateInvoice.setVisibility(View.GONE);
				btnViewSaleTrans.setVisibility(View.GONE);
				Toast.makeText(
						activity,
						getResources().getString(R.string.createInvoiceSuccess),
						Toast.LENGTH_SHORT).show();
			} else if (Constant.ERROR_CODE.equals(result)) {
				OnClickListener onclick = null;
				if (isTimeout) {
					// onclick = new OnClickListener() {
					//
					// @Override
					// public void onClick(View v) {
					// Intent intent = new Intent(getActivity(),
					// LoginActivity.class);
					// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					// | Intent.FLAG_ACTIVITY_NO_HISTORY);
					// startActivity(intent);
					// getActivity().finish();
					// }
					// };
					LoginDialog dialog = new LoginDialog(getActivity(),
							permission);
					dialog.show();
					isTimeout = false;
				}

				// That bai, hien thi thong bao ket qua
				String message = errorMessage;
				String title = getString(R.string.app_name);
				Dialog dialog = CommonActivity.createAlertDialog(activity,
						message, title, onclick);
				dialog.show();
			}
		}
	}

	private String searchSumSaleTrans() {
		try {
			StringBuilder rawData = new StringBuilder();
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getSumSaleTrans");
			rawData.append("<ws:getSumSaleTrans>");
			rawData.append("<getSumSaleTransInput>");
			rawData.append("<token>").append(Session.getToken())
					.append("</token>");
			rawData.append("<saleTrans>");
			HashMap<String, String> param = new HashMap<>();
			param.put("saleTransType", transType);
			param.put("telecomServiceId", telecomServiceId + "");
			if (channelType != null && channelType.getChannelTypeId() > 0) {
				param.put("channelTypeId", channelType.getChannelTypeId() + "");
			}
			if (selectedStaff != null) {
				param.put("channelId", selectedStaff.getStaffId() + "");
			}

			rawData.append(input.buildXML(param));
			rawData.append("</saleTrans>");
			// rawData.append("<saleTransDate>");
			// rawData.append(edtSaleTransDate.getText().toString());
			// rawData.append("</saleTransDate>");
			rawData.append("</getSumSaleTransInput>");

			rawData.append("</ws:getSumSaleTrans>");

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.e(LOG_TAG, envelope);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					getActivity(), "mbccs_getSumSaleTrans");
			Log.e(LOG_TAG, response);
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

			String original = output.getOriginal();
			TotalMoneyBillHanlder totalMoneyBillHanlder = (TotalMoneyBillHanlder) CommonActivity
					.parseXMLHandler(new TotalMoneyBillHanlder(), original);
			CommonOutput totalBillOutput = totalMoneyBillHanlder.getItem();

			if (totalBillOutput == null) {
				errorMessage = getResources().getString(R.string.exception);
				return Constant.ERROR_CODE;
			}
			if (totalBillOutput.getToken() != null
					&& !totalBillOutput.getToken().isEmpty()) {
				Session.setToken(totalBillOutput.getToken());
			}
			if (totalBillOutput.getErrorCode() != null
					&& !totalBillOutput.getErrorCode().equals("0")) {
				if (totalBillOutput.getErrorCode().equalsIgnoreCase(
						Constant.INVALID_TOKEN)) {
					isTimeout = true;
				}
				errorMessage = totalBillOutput.getDescription();
				if (errorMessage == null || errorMessage.trim().isEmpty()) {
					errorMessage = totalBillOutput.getErrorCode();
				}
				return Constant.ERROR_CODE;
			}

			if (totalMoneyBillHanlder.getTotalMoneyBill() != null
					&& totalMoneyBillHanlder.getTotalMoneyBill()
							.getMoneyAfterTax() != null
					&& !totalMoneyBillHanlder.getTotalMoneyBill()
							.getMoneyAfterTax().isEmpty()) {
				totalMoneyBill = totalMoneyBillHanlder.getTotalMoneyBill();
			} else {
				errorMessage = getResources().getString(R.string.no_data);
				totalMoneyBill = null;
				return Constant.ERROR_CODE;
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Exception", e);
			errorMessage = getResources().getString(R.string.exception)
					+ e.toString();
			return Constant.ERROR_CODE;
		}

		return Constant.SUCCESS_CODE;
	}

	private String createInvoice(String type, String isAddAll) {
		try {
			StringBuilder rawData = new StringBuilder();
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_createInvoiceAuto");
			rawData.append("<ws:createInvoiceAuto>");
			rawData.append("<createInvoiceInput>");
			rawData.append("<token>").append(Session.getToken())
					.append("</token>");
			rawData.append("<saleTrans>");
			HashMap<String, String> param = new HashMap<>();
			param.put("saleTransType", transType);
			param.put("telecomServiceId", telecomServiceId + "");
			if (channelType != null && channelType.getChannelTypeId() > 0) {
				param.put("channelTypeId", channelType.getChannelTypeId() + "");
			}
			if (selectedStaff != null) {
				param.put("channelId", selectedStaff.getStaffId() + "");
			}
			rawData.append(input.buildXML(param));
			rawData.append("</saleTrans>");

			if (isAddAll.equals(CREATE_LIST_SALE_TRANS)) {
				for (SaleTrans item : lstSaleTrans) {
					if (item.isChecked()) {
						rawData.append("<saleTransId>");
						rawData.append(item.getSaleTransId());
						rawData.append("</saleTransId>");
					}
				}
			}
			// rawData.append("<saleTransDate>");
			// rawData.append(edtSaleTransDate.getText().toString());
			// rawData.append("</saleTransDate>");
			rawData.append("<type>");
			rawData.append(type);
			rawData.append("</type>");

			rawData.append("</createInvoiceInput>");
			rawData.append("</ws:createInvoiceAuto>");

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.e(LOG_TAG, envelope);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					getActivity(), "mbccs_createInvoiceAuto");
			Log.e(LOG_TAG, response);
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

			String original = output.getOriginal();
			TotalMoneyBillHanlder totalMoneyBillHanlder = (TotalMoneyBillHanlder) CommonActivity
					.parseXMLHandler(new TotalMoneyBillHanlder(), original);
			output = totalMoneyBillHanlder.getItem();
			// lay token
			// if (output.getToken() != null
			// && !output.equals("")) {
			// Session.token = (output.getToken());
			// }
			// // end lay token
			// if (!output.getErrorCode().equals("0")) {
			// errorMessage = output.getDescription();
			// return Constant.ERROR_CODE;
			// }
			if (output == null) {
				errorMessage = getResources().getString(R.string.exception);
				return Constant.ERROR_CODE;
			}
			if (output.getToken() != null && !output.getToken().isEmpty()) {
				Session.setToken(output.getToken());
			}
			if (output.getErrorCode() != null
					&& !output.getErrorCode().equals("0")) {
				if (output.getErrorCode().equalsIgnoreCase(
						Constant.INVALID_TOKEN)) {
					isTimeout = true;
				}
				errorMessage = output.getDescription();
				if (errorMessage == null || errorMessage.trim().isEmpty()) {
					errorMessage = output.getErrorCode();
				}
				return Constant.ERROR_CODE;
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Exception", e);
			errorMessage = getResources().getString(R.string.exception)
					+ e.toString();
			return Constant.ERROR_CODE;
		}
		return Constant.SUCCESS_CODE;
	}

	private String getLisSaleTrans() {
		try {
			StringBuilder rawData = new StringBuilder();
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getListSaleTrans");
			rawData.append("<ws:getListSaleTrans>");
			rawData.append("<getListSaleTransInput>");
			rawData.append("<token>").append(Session.getToken())
					.append("</token>");
			rawData.append("<count>")
					.append(Constant.COUNT_SALE_TRANS_PER_ONCE)
					.append("</count>");
			rawData.append("<start>").append(lstSaleTrans.size())
					.append("</start>");
			rawData.append("<saleTrans>");
			HashMap<String, String> param = new HashMap<>();
			param.put("saleTransType", transType);
			param.put("telecomServiceId", telecomServiceId + "");
			if (channelType != null && channelType.getChannelTypeId() > 0) {
				param.put("channelTypeId", channelType.getChannelTypeId() + "");
			}
			if (selectedStaff != null) {
				param.put("channelId", selectedStaff.getStaffId() + "");
			}
			rawData.append(input.buildXML(param));
			rawData.append("</saleTrans>");
			// rawData.append("<saleTransDate>");
			// rawData.append(edtSaleTransDate.getText().toString());
			// rawData.append("</saleTransDate>");
			rawData.append("</getListSaleTransInput>");
			rawData.append("</ws:getListSaleTrans>");

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.e(LOG_TAG, envelope);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					getActivity(), "mbccs_getListSaleTrans");
			Log.e(LOG_TAG, response);
			CommonOutput output = input.parseGWResponse(response);
			if (!output.getError().equals("0")) {
				errorMessage = output.getDescription();
				return Constant.ERROR_CODE;
			}

			String original = output.getOriginal();
			ListSaleTransHandler handler = (ListSaleTransHandler) CommonActivity
					.parseXMLHandler(new ListSaleTransHandler(), original);
			output = handler.getItem();
			// lay token
			if (handler.getToken() != null && !handler.getToken().equals("")) {
				Session.setToken(handler.getToken());
			}
			// // end lay token
			// if (!output.getErrorCode().equals("0")) {
			// errorMessage = output.getDescription();
			// return Constant.ERROR_CODE;
			// }
			if (output == null) {
				errorMessage = getResources().getString(R.string.exception);
				return Constant.ERROR_CODE;
			}
			if (output.getToken() != null && !output.getToken().isEmpty()) {
				Session.setToken(output.getToken());
			}
			if (output.getErrorCode() != null
					&& !output.getErrorCode().equals("0")) {
				if (output.getErrorCode().equalsIgnoreCase(
						Constant.INVALID_TOKEN)) {
					isTimeout = true;
				}
				errorMessage = output.getDescription();
				if (errorMessage == null || errorMessage.trim().isEmpty()) {
					errorMessage = output.getErrorCode();
				}
				return Constant.ERROR_CODE;
			}

			lstSaleTrans.addAll(handler.getListSaleTrans());
		} catch (Exception e) {
			Log.e(LOG_TAG, "Exception", e);
			errorMessage = getResources().getString(R.string.exception)
					+ e.toString();
			return Constant.ERROR_CODE;
		}
		return Constant.SUCCESS_CODE;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			selectedStaff = (Staff) data.getExtras().getSerializable("staff");
			channelType = (ChannelTypeObject) data.getExtras().getSerializable(
					"channelType");

			if (selectedStaff != null) {
				tvChooseChannel.setHint(selectedStaff.getName() + " - "
						+ selectedStaff.getStaffCode());
				imgDeleteChannel.setVisibility(View.VISIBLE);
			} else if (channelType != null
					&& channelType.getChannelTypeId().intValue() != -1) {
				tvChooseChannel.setHint(channelType.getName());
				imgDeleteChannel.setVisibility(View.VISIBLE);
			} else {
				tvChooseChannel.setHint(getResources().getString(
						R.string.chooseChannelOrStaff));
				imgDeleteChannel.setVisibility(View.GONE);
			}
		} else {
			channelType = null;
			selectedStaff = null;
			imgDeleteChannel.setVisibility(View.GONE);
		}
	}

	private void createSaleTransDialog() {
		dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setTitle(getResources().getString(R.string.list_of_sale_trans));
		dialog.setContentView(R.layout.create_invoice_dialog);
		edtSearch = (EditText) dialog.findViewById(R.id.edtSearch);
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				adapter.filter(arg0.toString());
			}
		});

		btnCreateInvoiceOnDialog = dialog.findViewById(R.id.btnCreateInvoice);
		btnCreateInvoiceOnDialog.setOnClickListener(this);
		checkBoxAll = (CheckBox) dialog.findViewById(R.id.checkBoxAll);
		checkBoxAll.setOnClickListener(this);
		btnCancelOnDialog = dialog.findViewById(R.id.btnCancel);
		btnCancelOnDialog.setOnClickListener(this);
		lvSaleTrans = (ListView) dialog.findViewById(R.id.lvSaleTrans);
		dialog.show();
	}

	@Override
	public void onChangeChecked(SaleTrans saleTrans) {
		for (SaleTrans item : lstSaleTrans) {
			if (item.getSaleTransId().compareTo(saleTrans.getSaleTransId()) == 0) {
				item.setChecked(!item.isChecked());
				break;
			}
		}
		boolean isAllCheck = true;
		for (SaleTrans item : lstSaleTrans) {
			if (!item.isChecked()) {
				isAllCheck = false;
				break;
			}
		}
		checkBoxAll.setChecked(isAllCheck);

	}

	private int countSaleTrans() {
		int result = 0;
		for (SaleTrans saleTrans : lstSaleTrans) {
			if (saleTrans.isChecked()) {
				result++;
			}
		}
		return result;
	}
}
