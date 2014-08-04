package com.lyy.yugioh.activity;

import java.util.ArrayList;

import com.lyy.yugioh.R;
import com.lyy.yugioh.R.layout;
import com.lyy.yugioh.R.menu;
import com.lyy.yugioh.adapter.CardAdapter;
import com.lyy.yugioh.db.CardEntity;
import com.lyy.yugioh.utils.U;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class BanActivity extends Activity implements IActivity
{
	private RadioGroup group;
	private RadioButton rb_ban0, rb_ban1, rb_ban2, rb_ban00;
	private ListView lv_ban;
	private TextView tv_count;
	private ArrayList<CardEntity> currentArr;
	private CardAdapter currentAdapter, adapterBan0, adapterBan1, adapterBan2, adapterBan00;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ban);
		findViews();
		initVars();
		setListeners();

	}

	@Override
	public void findViews()
	{
		group = (RadioGroup) findViewById(R.id.radioGroup_ban);
		rb_ban0 = (RadioButton) findViewById(R.id.radioButton_ban0);
		rb_ban1 = (RadioButton) findViewById(R.id.radioButton_ban1);
		rb_ban2 = (RadioButton) findViewById(R.id.radioButton_ban2);
		rb_ban00 = (RadioButton) findViewById(R.id.radioButton_ban00);
		lv_ban = (ListView) findViewById(R.id.ban_list);
		tv_count = (TextView) findViewById(R.id.ban_count);

	}

	@Override
	public void initVars()
	{

		adapterBan0 = new CardAdapter(this, U.arrBan0);
		adapterBan1 = new CardAdapter(this, U.arrBan1);
		adapterBan2 = new CardAdapter(this, U.arrBan2);
		adapterBan00 = new CardAdapter(this, U.arrBan00);

		currentArr = U.arrBan0;
		currentAdapter = adapterBan0;
		lv_ban.setAdapter(currentAdapter);
		tv_count.setText("显示" + currentArr.size() + "张卡片");
	}

	@Override
	public void setListeners()
	{
		group.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				switch (checkedId)
				{
				case R.id.radioButton_ban0:
					currentArr = U.arrBan0;
					currentAdapter = adapterBan0;
					lv_ban.setAdapter(adapterBan0);
					tv_count.setText("显示" + currentArr.size() + "张卡片");
					break;
				case R.id.radioButton_ban1:
					currentArr = U.arrBan1;
					currentAdapter = adapterBan1;
					lv_ban.setAdapter(currentAdapter);
					tv_count.setText("显示" + currentArr.size() + "张卡片");
					break;
				case R.id.radioButton_ban2:
					currentArr = U.arrBan2;
					currentAdapter = adapterBan2;
					lv_ban.setAdapter(currentAdapter);
					tv_count.setText("显示" + currentArr.size() + "张卡片");
					break;
				case R.id.radioButton_ban00:
					currentArr = U.arrBan00;
					currentAdapter = adapterBan00;
					lv_ban.setAdapter(currentAdapter);
					tv_count.setText("显示" + currentArr.size() + "张卡片");
					break;
				default:
					break;
				}
			}
		});

		lv_ban.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				U.selectCard = currentArr.get(position);
				Intent intent = new Intent();
				intent.setClass(BanActivity.this, InfoActivity.class);
				startActivity(intent);
			}
		});
	}
}
