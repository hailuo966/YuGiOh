package com.lyy.yugioh.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.lyy.yugioh.R;
import com.lyy.yugioh.adapter.CardAdapter;
import com.lyy.yugioh.adapter.DeckAdapter;
import com.lyy.yugioh.db.CardEntity;
import com.lyy.yugioh.utils.U;

public class DeckActivity extends Activity implements IActivity, OnItemClickListener, OnClickListener, TextWatcher
{
	private TextView tv_mainDeck_count, tv_mainDeck_clear, tv_subDeck_count, tv_subDeck_clear, tv_extraDeck_count, tv_extraDeck_clear, tv_update;
	private GridView gv_mainDeck, gv_subDeck, gv_extraDeck;
	private EditText et_search;
	private ListView lv_cards;

	private File deck;
	private ArrayList<CardEntity> cardArr, extraArr;
	private DeckAdapter cardAdapter, extraAdapter;
	private CardAdapter mAdapter;
	private ArrayList<CardEntity> cards;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deck);
		findViews();
		initVars();
		setListeners();
	}

	@Override
	public void findViews()
	{
		tv_mainDeck_count = (TextView) findViewById(R.id.tv_mainDeck_count);
		tv_mainDeck_clear = (TextView) findViewById(R.id.tv_mainDeck_clear);
		tv_subDeck_count = (TextView) findViewById(R.id.tv_subDeck_count);
		tv_subDeck_clear = (TextView) findViewById(R.id.tv_subDeck_clear);
		tv_extraDeck_count = (TextView) findViewById(R.id.tv_extraDeck_count);
		tv_extraDeck_clear = (TextView) findViewById(R.id.tv_extraDeck_clear);
		gv_mainDeck = (GridView) findViewById(R.id.gv_mainDeck);
		gv_subDeck = (GridView) findViewById(R.id.gv_subDeck);
		gv_extraDeck = (GridView) findViewById(R.id.gv_extraDeck);

		gv_mainDeck.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gv_subDeck.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gv_extraDeck.setSelector(new ColorDrawable(Color.TRANSPARENT));

		et_search = (EditText) findViewById(R.id.et_deck_search);
		lv_cards = (ListView) findViewById(R.id.lv_deck_cards);

		tv_update = (TextView) findViewById(R.id.tv_deck_update);

	}

	@Override
	public void initVars()
	{
		if (U.results != null)
		{
			cards = U.results;
			mAdapter = new CardAdapter(this, cards);
			lv_cards.setAdapter(mAdapter);
		}
		if (CardSetActivity.selectedIndex != -1)
		{
			deck = new File(U.DECK_PATH + U.deckNames.get(CardSetActivity.selectedIndex) + ".txt");
			if (deck.exists())
			{
				U.getDeck(U.deckNames.get(CardSetActivity.selectedIndex));
				if (U.currentCardMap.size() > 0)
				{
					cardArr = U.deckMapToArr(U.currentCardMap);
					cardArr = U.sortCards(cardArr);

				} else
				{
					cardArr = new ArrayList<CardEntity>();
				}
				cardAdapter = new DeckAdapter(this, cardArr);
				gv_mainDeck.setAdapter(cardAdapter);
				if (U.currentExtraCardMap.size() > 0)
				{
					extraArr = U.deckMapToArr(U.currentExtraCardMap);

				} else
				{
					extraArr = new ArrayList<CardEntity>();
				}
				extraAdapter = new DeckAdapter(this, extraArr);
				gv_extraDeck.setAdapter(extraAdapter);
				tv_mainDeck_count.setText("" + cardArr.size());
				tv_extraDeck_count.setText("" + extraArr.size());
			}
		}
	}

	@Override
	public void setListeners()
	{
		tv_mainDeck_clear.setOnClickListener(this);
		tv_subDeck_clear.setOnClickListener(this);
		tv_extraDeck_clear.setOnClickListener(this);
		gv_mainDeck.setOnItemClickListener(this);
		gv_subDeck.setOnItemClickListener(this);
		gv_extraDeck.setOnItemClickListener(this);
		et_search.addTextChangedListener(this);
		tv_update.setOnClickListener(this);
		lv_cards.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.tv_mainDeck_clear:
			if (tv_update.getText().toString().equals("编辑"))
			{
				U.sysOut(this, "请先选择【编辑】");
			} else
			{
				U.currentCardMap.clear();
				cardArr.clear();
				cardAdapter.notifyDataSetChanged();
				tv_mainDeck_count.setText("0");
			}
			break;
		case R.id.tv_subDeck_clear:
			break;
		case R.id.tv_extraDeck_clear:
			if (tv_update.getText().toString().equals("编辑"))
			{
				U.sysOut(this, "请先选择【编辑】");
			} else
			{
				U.currentExtraCardMap.clear();
				extraArr.clear();
				extraAdapter.notifyDataSetChanged();
				tv_extraDeck_count.setText("0");
			}
			break;
		case R.id.tv_deck_update:
			if (tv_update.getText().toString().equals("编辑"))
			{
				tv_update.setText("保存");
			} else
			{
				U.saveDeck(U.deckNames.get(CardSetActivity.selectedIndex));
				tv_update.setText("编辑");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> gv, View v, int position, long arg3)
	{
		switch (gv.getId())
		{
		case R.id.gv_mainDeck:
			if (tv_update.getText().toString().equals("编辑"))
			{
				U.selectCard = cardArr.get(position);
				Intent intent = new Intent();
				intent.putExtra("screenOrientation", "h");
				intent.setClass(this, InfoActivity.class);
				startActivity(intent);
			} else
			{
				CardEntity entity = cardArr.get(position);
				int count = U.currentCardMap.get(entity.getSCCardName());
				if (--count > 0)
					U.currentCardMap.put(entity.getSCCardName(), count);
				else
					U.currentCardMap.remove(entity.getSCCardName());
				cardArr.remove(position);
				cardAdapter.notifyDataSetChanged();
				tv_mainDeck_count.setText("" + cardArr.size());
			}
			break;
		case R.id.gv_subDeck:
			break;
		case R.id.gv_extraDeck:
			if (tv_update.getText().toString().equals("编辑"))
			{
				U.selectCard = extraArr.get(position);
				Intent intent = new Intent();
				intent.putExtra("screenOrientation", "h");
				intent.setClass(this, InfoActivity.class);
				startActivity(intent);
			} else
			{
				CardEntity entity = extraArr.get(position);
				int count = U.currentExtraCardMap.get(entity.getSCCardName());
				if (--count > 0)
					U.currentExtraCardMap.put(entity.getSCCardName(), count);
				else
					U.currentExtraCardMap.remove(entity.getSCCardName());
				extraArr.remove(position);
				extraAdapter.notifyDataSetChanged();
				tv_extraDeck_count.setText("" + extraArr.size());
			}
			break;
		case R.id.lv_deck_cards:
			if (tv_update.getText().toString().equals("编辑"))
			{
				U.selectCard = cards.get(position);
				Intent intent = new Intent();
				intent.putExtra("screenOrientation", "h");
				intent.setClass(this, InfoActivity.class);
				startActivity(intent);
			} else
			{
				CardEntity entity = cards.get(position);
				String type = entity.getSCCardType();
				String ban = entity.getSCCardBan();
				if (type.equals("XYZ怪兽") || type.equals("融合怪兽") || type.equals("同调怪兽"))
				{
					if (U.currentExtraCardMap.size() < 15)
					{
						if (!ban.startsWith("禁止") && !ban.startsWith("观赏"))
						{
							if (U.currentExtraCardMap.get(entity.getSCCardName()) == null)
							{
								U.currentExtraCardMap.put(entity.getSCCardName(), 1);
								extraArr.add(entity);
								extraAdapter.notifyDataSetChanged();
								tv_extraDeck_count.setText("" + extraArr.size());
							} else
							{
								int cardNum = U.currentExtraCardMap.get(entity.getSCCardName());
								if (ban.startsWith("限制"))
								{
									U.sysOut(this, "该卡已满");
								} else if (ban.startsWith("准限"))
								{
									if (cardNum < 2)
									{
										U.currentExtraCardMap.put(entity.getSCCardName(), ++cardNum);
										extraArr.add(entity);
										extraAdapter.notifyDataSetChanged();
										tv_extraDeck_count.setText("" + extraArr.size());
									} else
										U.sysOut(this, "该卡已满");
								} else
								{
									if (cardNum < 3)
									{
										U.currentExtraCardMap.put(entity.getSCCardName(), ++cardNum);
										extraArr.add(entity);
										extraAdapter.notifyDataSetChanged();
										tv_extraDeck_count.setText("" + extraArr.size());
									} else
										U.sysOut(this, "该卡已满");
								}

							}
						} else
						{
							U.sysOut(this, "该卡不能加入卡组");
						}
					} else
					{
						U.sysOut(this, "额外卡组已满");
					}
				} else
				{
					if (U.currentCardMap.size() < 60)
					{
						if (!ban.startsWith("禁止") && !ban.startsWith("观赏"))
						{
							if (U.currentCardMap.get(entity.getSCCardName()) == null)
							{
								U.currentCardMap.put(entity.getSCCardName(), 1);
								cardArr.add(entity);
								cardAdapter.notifyDataSetChanged();
								tv_mainDeck_count.setText("" + cardArr.size());
							} else
							{
								int cardNum = U.currentCardMap.get(entity.getSCCardName());
								if (ban.startsWith("限制"))
								{
									U.sysOut(this, "该卡已满");
								} else if (ban.startsWith("准限"))
								{
									if (cardNum < 2)
									{
										U.currentCardMap.put(entity.getSCCardName(), ++cardNum);
										cardArr.add(entity);
										cardAdapter.notifyDataSetChanged();
										tv_mainDeck_count.setText("" + cardArr.size());
									} else
										U.sysOut(this, "该卡已满");
								} else
								{
									if (cardNum < 3)
									{
										U.currentCardMap.put(entity.getSCCardName(), ++cardNum);
										cardArr.add(entity);
										cardAdapter.notifyDataSetChanged();
										tv_mainDeck_count.setText("" + cardArr.size());
									} else
										U.sysOut(this, "该卡已满");
								}

							}
						} else
						{
							U.sysOut(this, "该卡不能加入卡组");
						}
					} else
					{
						U.sysOut(this, "卡组已满");
					}
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		String text = et_search.getText().toString().toLowerCase();
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
		lv_cards.setAdapter(mAdapter);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{

	}

}
