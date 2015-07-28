package com.lyy.yugioh.adapter;

import java.util.ArrayList;

import com.lyy.yugioh.R;
import com.lyy.yugioh.R.id;
import com.lyy.yugioh.R.layout;
import com.lyy.yugioh.activity.MainActivity;
import com.lyy.yugioh.db.CardEntity;
import com.lyy.yugioh.utils.U;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CardAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<CardEntity> cards;
	private ViewHolder holder;
	private LayoutInflater inflater;

	private int list_type;

	public CardAdapter(Context context, ArrayList<CardEntity> cards)
	{
		this.context = context;
		this.cards = cards;
		this.list_type = list_type;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	private class ViewHolder
	{
		ImageView iv;
		TextView tv;
	}

	@Override
	public int getCount()
	{
		return cards == null ? 0 : cards.size();
	}

	@Override
	public Object getItem(int position)
	{
		return cards == null ? null : cards.get(position);
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
			convertView = inflater.inflate(R.layout.item_list, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.item_image);
			holder.tv = (TextView) convertView.findViewById(R.id.item_text);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		String path = U.imagePath + (cards.get(position).getCardID()) + ".jpg";
		System.out.println(path);
		Bitmap bitmap = U.imageBuffer.get(path);
		if (bitmap == null)
		{
			Options options = new Options();
			options.inSampleSize = 2;
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm != null)
			{
				if (U.imageBuffer.size() > 500)
					U.imageBuffer.clear();
				U.imageBuffer.put(path, bm);
				holder.iv.setImageBitmap(bm);
			} else
			{
				holder.iv.setImageBitmap(U.BM_DEFAULT);
			}
		} else
		{
			holder.iv.setImageBitmap(bitmap);
		}
		holder.tv.setText(cards.get(position).getSCCardName() + "\n" + cards.get(position).getSCCardDepict());

		return convertView;
	}
}
