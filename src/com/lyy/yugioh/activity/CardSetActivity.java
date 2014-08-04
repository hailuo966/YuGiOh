package com.lyy.yugioh.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.lyy.yugioh.R;
import com.lyy.yugioh.adapter.CardSetAdapter;
import com.lyy.yugioh.utils.U;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CardSetActivity extends Activity implements IActivity, OnItemClickListener, OnClickListener
{

	public static ListView lv_set;
	private TextView tv_add;
	public static CardSetAdapter adapter;
	public static int selectedIndex = -1;
	private View mView;
	private boolean isBoxVisible;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_set);

		findViews();
		initVars();
		setListeners();

	}

	@Override
	public void findViews()
	{
		lv_set = (ListView) findViewById(R.id.set_list);
		tv_add = (TextView) findViewById(R.id.tv_set_add);
	}

	@Override
	public void initVars()
	{
		File deck = new File(U.DECK_PATH);
		if (deck.isDirectory())
		{
			String[] decks = deck.list();
			if (decks.length > 0)
			{
				U.deckNames = new ArrayList<String>();
				for (int i = 0; i < decks.length; i++)
				{
					decks[i] = decks[i].replace(".txt", "");
					U.deckNames.add(decks[i]);
				}
				adapter = new CardSetAdapter(this, U.deckNames);
				lv_set.setAdapter(adapter);
			}
		}
	}

	@Override
	public void setListeners()
	{
		lv_set.setOnItemClickListener(this);
		tv_add.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> gv, View view, int position, long arg3)
	{
		selectedIndex = position;
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout_set_action);
		if (mView == null)
		{
			mView = view;
			layout.setVisibility(View.VISIBLE);
			isBoxVisible = true;
			if (position == adapter.getCount() - 1)
			{
				lv_set.setSelection(lv_set.getBottom());
			}
		} else
		{
			if (mView == view)
			{
				if (isBoxVisible == false)
				{
					layout.setVisibility(View.VISIBLE);
					isBoxVisible = true;
					if (position == adapter.getCount() - 1)
					{
						lv_set.setSelection(lv_set.getBottom());
					}
				} else
				{
					layout.setVisibility(View.GONE);
					isBoxVisible = false;
				}
			} else
			{
				LinearLayout layout1 = (LinearLayout) mView.findViewById(R.id.layout_set_action);
				layout1.setVisibility(View.GONE);
				isBoxVisible = false;
				mView = view;
				layout.setVisibility(View.VISIBLE);
				isBoxVisible = true;
				if (position == adapter.getCount() - 1)
				{
					lv_set.setSelection(lv_set.getBottom());
				}
			}

		}
	}

	@Override
	public void onClick(View v)
	{
		final EditText editText = new EditText(this);
		Builder builder = new Builder(this);
		builder.setTitle("创建卡组").setPositiveButton("创建", new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				String name = editText.getText().toString();
				if (!TextUtils.isEmpty(name))
				{
					File file = new File(U.DECK_PATH + name + ".txt");
					if (!file.exists())
					{
						try
						{
							boolean flag = file.createNewFile();
							U.deckNames.add(name);
							adapter.notifyDataSetChanged();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}).setNegativeButton("取消", null);
		AlertDialog dialog = builder.create();
		dialog.setView(editText, 0, 0, 0, 0);
		dialog.show();

	}
}
