package com.lyy.yugioh.activity;

import com.lyy.yugioh.R;
import com.lyy.yugioh.R.id;
import com.lyy.yugioh.R.layout;
import com.lyy.yugioh.utils.U;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends Activity implements IActivity
{
	private TextView mtv_name, mtv_attribute, mtv_info, mtv_depict;
	private ImageView miv_image;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);

		findViews();
		initVars();
		setListeners();
	}

	@Override
	public void findViews()
	{
		mtv_name = (TextView) findViewById(R.id.card_name);
		mtv_attribute = (TextView) findViewById(R.id.card_attribute);
		mtv_info = (TextView) findViewById(R.id.card_info);
		mtv_depict = (TextView) findViewById(R.id.card_depict);
		miv_image = (ImageView) findViewById(R.id.card_image);

	}

	@Override
	public void initVars()
	{
		String screenOrientation = getIntent().getStringExtra("screenOrientation");
		if (!TextUtils.isEmpty(screenOrientation))
		{
			if (screenOrientation.equals("h"))
			{
				this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			} else
			{
				this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}

		if (U.selectCard != null)
		{
			String name = "中文名：" + U.selectCard.getSCCardName() + "\n日文名：" + U.selectCard.getJPCardName() + "\n英文名：" + U.selectCard.getENCardName();
			String attribute = "卡片归属：" + U.selectCard.getCardCamp() + "\n卡片密码：" + U.selectCard.getCardPass() + "\n卡片种类：" + U.selectCard.getSCCardType() + "\n卡片限制：" + U.selectCard.getSCCardBan()
					+ "\n罕见程度：" + U.selectCard.getSCCardRare();
			String info = null;
			if (U.selectCard.getSCCardType().contains("怪兽"))
			{
				info = "星数：" + U.selectCard.getCardStarNum() + "星\n种族：" + U.selectCard.getSCCardRace() + "\n属性：" + U.selectCard.getSCCardAttribute() + "\n攻：" + U.selectCard.getCardAtk() + "\t守："
						+ U.selectCard.getCardDef();
			}
			String depict = "效果：" + U.selectCard.getSCCardDepict();
			String path = U.imagePath + (U.selectCard.getCardID()) + ".jpg";
			Bitmap bitmap = BitmapFactory.decodeFile(path);
			mtv_name.setText(name);
			mtv_attribute.setText(attribute);
			if (!TextUtils.isEmpty(info))
			{
				mtv_info.setText(info);
			} else
			{
				mtv_info.setVisibility(View.GONE);
			}
			mtv_depict.setText(depict);
			if (bitmap != null)
			{
				miv_image.setImageBitmap(bitmap);
			}
		}
	}

	@Override
	public void setListeners()
	{

	}

}
