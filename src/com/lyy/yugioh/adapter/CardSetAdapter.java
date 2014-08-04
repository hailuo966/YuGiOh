package com.lyy.yugioh.adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.lyy.yugioh.R;
import com.lyy.yugioh.activity.CardSetActivity;
import com.lyy.yugioh.activity.DeckActivity;
import com.lyy.yugioh.utils.U;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class CardSetAdapter extends BaseAdapter
{
	private ArrayList<String> decks;
	private Context context;
	private LayoutInflater inflater;
	private ViewHolder holder;

	public CardSetAdapter(Context context, ArrayList<String> decks)
	{
		this.context = context;
		this.decks = decks;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	private class ViewHolder
	{
		TextView tv_name, tv_update, tv_rename, tv_delete;
	}

	@Override
	public int getCount()
	{
		return decks == null ? 0 : decks.size();
	}

	@Override
	public Object getItem(int position)
	{
		return decks == null ? null : decks.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final int index = position;
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_list_set, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.item_tv_deck_name);
			holder.tv_update = (TextView) convertView.findViewById(R.id.item_tv_deck_update);
			holder.tv_rename = (TextView) convertView.findViewById(R.id.item_tv_deck_rename);
			holder.tv_delete = (TextView) convertView.findViewById(R.id.item_tv_deck_delete);
			convertView.setTag(holder);

		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_name.setText(decks.get(position));

		holder.tv_update.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(context, DeckActivity.class);
				context.startActivity(intent);
			}
		});

		holder.tv_rename.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				final EditText editText = new EditText(context);
				Builder builder = new Builder(context);
				builder.setTitle("修改卡组名称").setPositiveButton("修改", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						String name = editText.getText().toString();
						if (!TextUtils.isEmpty(name))
						{
							File file = new File(U.DECK_PATH + U.deckNames.get(index) + ".txt");
							if (file.exists())
							{
								boolean flag = file.renameTo(new File(U.DECK_PATH + name + ".txt"));
								if (flag)
								{
									U.deckNames.set(index, name);
								}
								CardSetActivity.adapter = new CardSetAdapter(context, U.deckNames);
								CardSetActivity.lv_set.setAdapter(CardSetActivity.adapter);
							}
						}
					}
				}).setNegativeButton("取消", null);
				AlertDialog dialog = builder.create();
				dialog.setView(editText, 0, 0, 0, 0);
				dialog.show();
			}
		});

		holder.tv_delete.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Builder builder = new Builder(context);
				builder.setTitle("删除卡组").setMessage("删除后卡组不可恢复，确定删除吗？").setPositiveButton("删除", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						File file = new File(U.DECK_PATH + U.deckNames.get(index) + ".txt");
						if (file.exists())
						{
							file.delete();
						}
						U.deckNames.remove(index);
						CardSetActivity.adapter = new CardSetAdapter(context, U.deckNames);
						CardSetActivity.lv_set.setAdapter(CardSetActivity.adapter);
					}
				}).setNegativeButton("取消", null);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});

		return convertView;
	}

}
