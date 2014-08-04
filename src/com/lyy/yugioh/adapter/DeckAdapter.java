package com.lyy.yugioh.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.lyy.yugioh.R;
import com.lyy.yugioh.db.CardEntity;
import com.lyy.yugioh.utils.U;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class DeckAdapter extends BaseAdapter
{
	private ArrayList<CardEntity> cards;
	private Context context;
	private LayoutInflater inflater;
	private ViewHolder holder;

	public DeckAdapter(Context context, ArrayList<CardEntity> cards)
	{
		this.context = context;
		this.cards = cards;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	private class ViewHolder
	{
		ImageView iv;
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
		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_grid_image, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.item_iv_deck);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		String path = U.IMAGE_PATH + (cards.get(position).get_id() - 1) + ".jpg";
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
		return convertView;
	}

}
