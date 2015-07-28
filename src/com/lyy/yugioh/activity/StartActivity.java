package com.lyy.yugioh.activity;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.lyy.yugioh.R;
import com.lyy.yugioh.R.layout;
import com.lyy.yugioh.db.CardEntity;
import com.lyy.yugioh.db.OrmliteDbHelper;
import com.lyy.yugioh.utils.U;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;

public class StartActivity extends Activity implements Callback {
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		OrmliteDbHelper.copyDataBase(this, 0);
		OrmliteDbHelper.initHelper(this);
		U.init(this);
		handler = new Handler(this);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Dao<CardEntity, Integer> dao = OrmliteDbHelper.getDao(StartActivity.this, CardEntity.class);
					dao.setAutoCommit(OrmliteDbHelper.getConnection(), false);
					Savepoint savepoint = OrmliteDbHelper.getConnection().setSavePoint("getData");
					QueryBuilder<CardEntity, Integer> builder = dao.queryBuilder();
					U.results = (ArrayList<CardEntity>) builder.orderBy("SCCardName", true).query();
					OrmliteDbHelper.getConnection().commit(savepoint);
					if (U.results.size() > 0) {
						U.upperResult = new ArrayList<CardEntity>();
						U.arrBan0 = new ArrayList<CardEntity>();
						U.arrBan1 = new ArrayList<CardEntity>();
						U.arrBan2 = new ArrayList<CardEntity>();
						U.arrBan00 = new ArrayList<CardEntity>();
						for (CardEntity cardEntity : U.results) {
							CardEntity entity = new CardEntity();
							entity.setSCCardAttribute(cardEntity.getSCCardAttribute().toLowerCase());
							entity.setSCCardBan(cardEntity.getSCCardBan().toLowerCase());
							entity.setSCCardDepict(cardEntity.getSCCardDepict().toLowerCase());
							entity.setSCCardName(cardEntity.getSCCardName().toLowerCase());
							entity.setSCCardRace(cardEntity.getSCCardRace().toLowerCase());
							entity.setSCCardRare(cardEntity.getSCCardRare().toLowerCase());
							entity.setSCCardType(cardEntity.getSCCardType().toLowerCase());
							entity.setCardStarNum(cardEntity.getCardStarNum());
							U.upperResult.add(entity);

							if (cardEntity.getSCCardBan().startsWith("禁止"))
								U.arrBan0.add(cardEntity);
							else if (cardEntity.getSCCardBan().startsWith("限制"))
								U.arrBan1.add(cardEntity);
							else if (cardEntity.getSCCardBan().startsWith("准限"))
								U.arrBan2.add(cardEntity);
							else if (cardEntity.getSCCardBan().startsWith("观赏"))
								U.arrBan00.add(cardEntity);

							U.cardMap.put(cardEntity.getSCCardName(), cardEntity);
						}

						if (U.results.size() == U.upperResult.size())
							handler.sendEmptyMessage(0);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public boolean handleMessage(Message msg) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		this.finish();
		return false;
	}
}
