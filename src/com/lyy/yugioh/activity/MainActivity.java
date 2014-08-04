package com.lyy.yugioh.activity;

import java.util.ArrayList;
import java.util.Set;
import com.lyy.yugioh.R;
import com.lyy.yugioh.adapter.CardAdapter;
import com.lyy.yugioh.db.CardEntity;
import com.lyy.yugioh.db.OrmliteDbHelper;
import com.lyy.yugioh.utils.U;
import com.slidingmenu.lib.SlidingMenu;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements IActivity, OnItemClickListener, TextWatcher, Callback, OnClickListener
{

	private EditText met_search;
	private ListView mlv_cards;
	private TextView mtv_count;
	private CardAdapter mAdapter;
	private ArrayList<CardEntity> cards;
	private Handler handler;
	private boolean isExit = false;
	private SlidingMenu slidingMenu;
	private ViewHolder viewHolder;
	private Builder bType, bType2, bCamp, bRare, bBan, bAttribute, bRace;
	private AlertDialog adType, adType2, adCamp, adRare, adBan, adAttribute, adRace;
	private ListView lvCamp, lvType2, lvBan, lvAttribute;
	private GridView gvType, gvRare, gvRace;

	private String cardName, cardCamp, cardRare, cardBan, cardType, cardType2, cardStarNum, cardAttribute, cardRace, cardAtk, cardDef;
	public static ImageView iv_add;

	private class ViewHolder
	{
		TextView tv_startSearch, tv_search, tv_reset, tv_banList, tv_cardSet;
		EditText et_name, et_camp, et_rare, et_ban, et_type, et_type2, et_starNum, et_attribute, et_race, et_atk, et_def;
		LinearLayout layout_search, layout_monster;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
		initVars();
		setListeners();
	}

	@Override
	public void findViews()
	{
		met_search = (EditText) findViewById(R.id.search);
		mlv_cards = (ListView) findViewById(R.id.cards);
		mtv_count = (TextView) findViewById(R.id.count);
	}

	@Override
	public void initVars()
	{
		handler = new Handler(this);
		if (U.results != null)
		{
			cards = U.results;
			mAdapter = new CardAdapter(this, cards);
			mlv_cards.setAdapter(mAdapter);
			mtv_count.setText("显示" + cards.size() + "张卡片");
		}
		initSlidingMenu();
		initDialog();
	}

	@Override
	public void setListeners()
	{
		met_search.addTextChangedListener(this);
		mlv_cards.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
	{
		U.selectCard = cards.get(position);
		Intent intent = new Intent();
		intent.putExtra("screenOrientation", "v");
		intent.setClass(this, InfoActivity.class);
		startActivity(intent);
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		String text = met_search.getText().toString().toLowerCase();
		if (!TextUtils.isEmpty(text))
		{
			cards = new ArrayList<CardEntity>();
			for (int i = 0; i < U.upperResult.size(); i++)
			{
				CardEntity entity = U.upperResult.get(i);
				if (entity.getSCCardAttribute().contains(text) || entity.getSCCardBan().contains(text) || entity.getSCCardDepict().contains(text) || entity.getSCCardName().contains(text)
						|| entity.getSCCardRace().contains(text) || entity.getSCCardRare().contains(text) || entity.getSCCardType().contains(text) || entity.getCardStarNumStr().contains(text))
				{
					cards.add(U.results.get(i));
				}
			}
		} else
		{
			cards = U.results;
		}
		mAdapter = new CardAdapter(this, cards);
		mlv_cards.setAdapter(mAdapter);
		mtv_count.setText("显示" + cards.size() + "张卡片");

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{

	}

	@Override
	public void onBackPressed()
	{
		if (!isExit)
		{
			isExit = true;
			U.sysOut(this, "再按一次退出查卡器");
			handler.sendEmptyMessageDelayed(0, 3000);
		} else
		{
			try
			{
				OrmliteDbHelper.getConnection().close();
				OrmliteDbHelper.getInstance().close();
				if (U.imageBuffer.size() > 0)
				{
					Set<String> keys = U.imageBuffer.keySet();
					for (String key : keys)
					{
						Bitmap bm = U.imageBuffer.get(key);
						if (bm != null && !bm.isRecycled())
						{
							bm.recycle();
							bm = null;
						}
					}
					U.imageBuffer.clear();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	@Override
	public boolean handleMessage(Message msg)
	{
		switch (msg.what)
		{
		case 0:
			isExit = false;
			break;
		default:
			break;
		}
		return false;
	}

	public void initSlidingMenu()
	{
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.8f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.layout_sliding);

		viewHolder = new ViewHolder();
		viewHolder.tv_startSearch = (TextView) slidingMenu.findViewById(R.id.tv_startSearch);
		viewHolder.tv_banList = (TextView) slidingMenu.findViewById(R.id.tv_banList);
		viewHolder.tv_cardSet = (TextView) slidingMenu.findViewById(R.id.tv_cardSet);
		viewHolder.tv_search = (TextView) slidingMenu.findViewById(R.id.tv_search);
		viewHolder.tv_reset = (TextView) slidingMenu.findViewById(R.id.tv_reset);
		viewHolder.et_name = (EditText) slidingMenu.findViewById(R.id.et_name);
		viewHolder.et_camp = (EditText) slidingMenu.findViewById(R.id.et_camp);
		viewHolder.et_rare = (EditText) slidingMenu.findViewById(R.id.et_rare);
		viewHolder.et_ban = (EditText) slidingMenu.findViewById(R.id.et_ban);
		viewHolder.et_type = (EditText) slidingMenu.findViewById(R.id.et_type);
		viewHolder.et_type2 = (EditText) slidingMenu.findViewById(R.id.et_type2);
		viewHolder.et_starNum = (EditText) slidingMenu.findViewById(R.id.et_starNum);
		viewHolder.et_attribute = (EditText) slidingMenu.findViewById(R.id.et_attribute);
		viewHolder.et_race = (EditText) slidingMenu.findViewById(R.id.et_race);
		viewHolder.et_atk = (EditText) slidingMenu.findViewById(R.id.et_atk);
		viewHolder.et_def = (EditText) slidingMenu.findViewById(R.id.et_def);
		viewHolder.layout_search = (LinearLayout) slidingMenu.findViewById(R.id.layout_search);
		viewHolder.layout_monster = (LinearLayout) slidingMenu.findViewById(R.id.layout_monster);

		viewHolder.tv_startSearch.setOnClickListener(this);
		viewHolder.tv_banList.setOnClickListener(this);
		viewHolder.tv_cardSet.setOnClickListener(this);
		viewHolder.et_camp.setOnClickListener(this);
		viewHolder.et_type.setOnClickListener(this);
		viewHolder.et_type2.setOnClickListener(this);
		viewHolder.tv_search.setOnClickListener(this);
		viewHolder.tv_reset.setOnClickListener(this);
		viewHolder.et_rare.setOnClickListener(this);
		viewHolder.et_ban.setOnClickListener(this);
		viewHolder.et_attribute.setOnClickListener(this);
		viewHolder.et_race.setOnClickListener(this);

	}

	public void initDialog()
	{
		// 卡片归属窗口初始化
		bCamp = new Builder(this).setTitle("卡片归属");
		adCamp = bCamp.create();
		lvCamp = new ListView(this);
		lvCamp.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.item_grid_text, U.CARDCAMP));
		lvCamp.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> gv, View v, int position, long arg3)
			{
				String camp = U.CARDCAMP[position];
				viewHolder.et_camp.setText(camp);
				adCamp.dismiss();
			}
		});
		adCamp.setView(lvCamp, 0, 0, 0, 0);

		// 卡片类型窗口初始化
		bType = new Builder(this).setTitle("卡片类型");
		adType = bType.create();
		gvType = new GridView(this);
		gvType.setNumColumns(2);
		gvType.setAdapter(new ArrayAdapter<String>(MainActivity.this, R.layout.item_grid_text, U.CARDTYPE));
		gvType.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> gv, View v, int position, long arg3)
			{
				String type = U.CARDTYPE[position];
				viewHolder.et_type.setText(type);
				if (type.contains("怪兽"))
				{
					viewHolder.layout_monster.setVisibility(View.VISIBLE);
					viewHolder.et_type2.setEnabled(true);
				} else
				{
					viewHolder.layout_monster.setVisibility(View.GONE);
					viewHolder.et_type2.setEnabled(false);
					viewHolder.et_type2.setText("不限");
				}
				adType.dismiss();
			}
		});
		adType.setView(gvType, 0, 0, 0, 0);

		// 卡片类型2窗口初始化
		bType2 = new Builder(this);
		adType2 = bType2.create();
		lvType2 = new ListView(this);
		lvType2.setAdapter(new ArrayAdapter<String>(this, R.layout.item_grid_text, U.CARDTYPE2));
		lvType2.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				String type2 = U.CARDTYPE2[position];
				viewHolder.et_type2.setText(type2);
				adType2.dismiss();
			}
		});
		adType2.setView(lvType2, 0, 0, 0, 0);

		// 罕见程度窗口初始化
		bRare = new Builder(this).setTitle("罕见程度");
		adRare = bRare.create();
		gvRare = new GridView(this);
		gvRare.setNumColumns(2);
		gvRare.setAdapter(new ArrayAdapter<String>(this, R.layout.item_grid_text, U.CARDRARE));
		gvRare.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				String rare = U.CARDRARE[position];
				viewHolder.et_rare.setText(rare);
				adRare.dismiss();
			}
		});
		adRare.setView(gvRare, 0, 0, 0, 0);

		// 禁限卡表窗口初始化
		bBan = new Builder(this);
		adBan = bBan.create();
		lvBan = new ListView(this);
		lvBan.setAdapter(new ArrayAdapter<String>(this, R.layout.item_grid_text, U.CARDBAN));
		lvBan.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				String ban = U.CARDBAN[position];
				viewHolder.et_ban.setText(ban);
				adBan.dismiss();
			}
		});
		adBan.setView(lvBan, 0, 0, 0, 0);

		// 卡片属性窗口初始化
		bAttribute = new Builder(this);
		adAttribute = bAttribute.create();
		lvAttribute = new ListView(this);
		lvAttribute.setAdapter(new ArrayAdapter<String>(this, R.layout.item_grid_text, U.CARDATTRIBUTE));
		lvAttribute.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				String attribute = U.CARDATTRIBUTE[position];
				viewHolder.et_attribute.setText(attribute);
				adAttribute.dismiss();
			}
		});
		adAttribute.setView(lvAttribute, 0, 0, 0, 0);

		// 卡片种族窗口初始化
		bRace = new Builder(this);
		adRace = bRace.create();
		gvRace = new GridView(this);
		gvRace.setNumColumns(2);
		gvRace.setAdapter(new ArrayAdapter<String>(this, R.layout.item_grid_text, U.CARDRACE));
		gvRace.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				String race = U.CARDRACE[position];
				viewHolder.et_race.setText(race);
				adRace.dismiss();
			}
		});
		adRace.setView(gvRace, 0, 0, 0, 0);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_startSearch:
			if (viewHolder.layout_search.getVisibility() == View.GONE)
			{
				viewHolder.layout_search.setVisibility(View.VISIBLE);
			} else
			{
				viewHolder.layout_search.setVisibility(View.GONE);
			}
			break;
		case R.id.tv_banList:
			Intent intentBan = new Intent();
			intentBan.setClass(this, BanActivity.class);
			startActivity(intentBan);
			break;
		case R.id.tv_cardSet:
			Intent intentSet = new Intent();
			intentSet.setClass(this, CardSetActivity.class);
			startActivity(intentSet);
			break;
		case R.id.tv_search:
			cards = new ArrayList<CardEntity>();
			cardName = viewHolder.et_name.getText().toString();
			cardCamp = viewHolder.et_camp.getText().toString();
			cardRare = viewHolder.et_rare.getText().toString();
			cardBan = viewHolder.et_ban.getText().toString();
			cardType = viewHolder.et_type.getText().toString();
			cardType2 = viewHolder.et_type2.getText().toString();
			cardStarNum = viewHolder.et_starNum.getText().toString();
			cardAttribute = viewHolder.et_attribute.getText().toString();
			cardRace = viewHolder.et_race.getText().toString();
			cardAtk = viewHolder.et_atk.getText().toString();
			cardDef = viewHolder.et_def.getText().toString();
			for (CardEntity entity : U.results)
			{
				if (isSearched(entity))
					cards.add(entity);
			}
			mAdapter = new CardAdapter(this, cards);
			mlv_cards.setAdapter(mAdapter);
			mtv_count.setText("显示" + cards.size() + "张卡片");
			break;
		case R.id.tv_reset:
			viewHolder.et_name.setText("");
			viewHolder.et_camp.setText("不限");
			viewHolder.et_rare.setText("不限");
			viewHolder.et_ban.setText("不限");
			viewHolder.et_type.setText("不限");
			viewHolder.et_type2.setText("不限");
			viewHolder.et_type2.setEnabled(false);
			viewHolder.et_starNum.setText("");
			viewHolder.et_attribute.setText("不限");
			viewHolder.et_race.setText("不限");
			viewHolder.et_atk.setText("");
			viewHolder.et_def.setText("");
			break;
		case R.id.et_camp:
			adCamp.show();
			break;
		case R.id.et_type:
			adType.show();
			break;
		case R.id.et_type2:
			adType2.show();
			break;
		case R.id.et_rare:
			adRare.show();
			break;
		case R.id.et_ban:
			adBan.show();
			break;
		case R.id.et_race:
			adRace.show();
			break;
		case R.id.et_attribute:
			adAttribute.show();
			break;

		default:
			break;
		}
	}

	public boolean isSearched(CardEntity entity)
	{
		boolean flag = false;

		if (TextUtils.isEmpty(cardName))
			flag = true;
		else if (entity.getSCCardName().contains(cardName))
			flag = true;
		else
			return false;

		if (cardCamp.equals("不限"))
			flag = true;
		else if (entity.getCardCamp().equals(cardCamp))
			flag = true;
		else
			return false;

		if (cardRare.equals("不限"))
			flag = true;
		else if (entity.getSCCardRare().contains(cardRare))
			flag = true;
		else
			return false;

		if (cardBan.equals("不限"))
			flag = true;
		else if (entity.getSCCardBan().startsWith(cardBan))
			flag = true;
		else
			return false;

		if (cardType.equals("不限"))
			flag = true;
		else if (entity.getSCCardType().equals(cardType))
			flag = true;
		else
			return false;

		if (cardType.contains("怪兽"))
		{
			if (cardType2.equals("不限"))
				flag = true;
			else if (entity.getSCDCardType().contains(cardType2))
				flag = true;
			else
				return false;

			if (TextUtils.isEmpty(cardStarNum))
				flag = true;
			else if (U.isNum(cardStarNum))
			{
				double d = Double.parseDouble(cardStarNum);
				if (d == entity.getCardStarNum())
					flag = true;
				else
					return false;
			} else
				return false;

			if (cardAttribute.equals("不限"))
				flag = true;
			else if (entity.getSCCardAttribute().contains(cardAttribute))
				flag = true;
			else
				return false;

			if (cardRace.equals("不限"))
				flag = true;
			else if (entity.getSCCardRace().contains(cardRace))
				flag = true;
			else
				return false;

			if (TextUtils.isEmpty(cardAtk))
				flag = true;
			else if (U.isNum(cardAtk))
			{
				double d = Double.parseDouble(cardAtk);
				if (d == entity.getCardAtk())
					flag = true;
				else
					return false;
			} else
				return false;

			if (TextUtils.isEmpty(cardDef))
				flag = true;
			else if (U.isNum(cardDef))
			{
				double d = Double.parseDouble(cardDef);
				if (d == entity.getCardDef())
					flag = true;
				else
					return false;
			} else
				return false;
		}

		return flag;
	}

}
