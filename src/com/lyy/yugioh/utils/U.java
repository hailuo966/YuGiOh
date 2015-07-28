package com.lyy.yugioh.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.lyy.yugioh.db.CardEntity;

public class U {
	public static ConcurrentHashMap<String, Bitmap> imageBuffer = new ConcurrentHashMap<String, Bitmap>();
	public static ArrayList<CardEntity> results, upperResult;
	public static CardEntity selectCard;
	public static ArrayList<CardEntity> arrBan0, arrBan1, arrBan2, arrBan00;
	public static HashMap<String, CardEntity> cardMap = new HashMap<String, CardEntity>();
	public static HashMap<String, Integer> currentCardMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> currentExtraCardMap = new HashMap<String, Integer>();
	public static ArrayList<String> deckNames;
	// public static String currentDeckName;
	public static final String IMAGE_PATH = "/yugioh/images/";
	public static final String DECK_PATH = "/yugioh/deck/";
	public static String imagePath;
	public static String deckPath;
	public static Bitmap BM_DEFAULT;
	public static HashMap<Integer, String> races, attributes;

	public static void init(Context context) {
		String sd = SDCardUtil.getExtSdCardPath(context);
		if (TextUtils.isEmpty(sd)) {
			sd = SDCardUtil.getSdCardPath(context);
		}
		imagePath = sd + IMAGE_PATH;
		deckPath = sd + DECK_PATH;
		BM_DEFAULT = BitmapFactory.decodeFile(IMAGE_PATH + "0.jpg");
		// races = new HashMap<Integer, String>();
		// attributes = new HashMap<Integer, String>();
		// for (int i = 0; i < 24; i++) {
		// races.put((int) Math.pow(2, i), CARDRACES[i]);
		// }
	}

	public static final String ACTION_SET = "com.lyy.yugioh.set";

	public static final String[] CARDTYPE = new String[]{"不限", "通常怪兽", "效果怪兽", "仪式怪兽", "融合怪兽", "同调怪兽", "XYZ怪兽", "通常魔法", "装备魔法", "永续魔法", "场地魔法", "速攻魔法", "仪式魔法", "通常陷阱", "永续陷阱", "反击陷阱"};
	public static final String[] CARDTYPE2 = new String[]{"不限", "卡通", "灵魂", "同盟", "调整", "二重", "反转", "摇摆"};
	public static final String[] CARDRACE = new String[]{"不限", "水", "雷", "龙", "炎", "兽", "鱼", "机械", "昆虫", "植物", "恶魔", "战士", "岩石", "天使", "鸟兽", "海龙", "不死", "恐龙", "魔法师", "兽战士", "爬虫类", "幻神兽", "念动力", "创造神"};
	// public static final String[] CARDRACES = new String[]{"不限", "战士", "魔法使", "天使", "恶魔", "不死", "机械", "水", "炎", "岩石", "鸟兽", "植物", "昆虫", "雷", "龙", "兽", "兽战士", "恐龙", "鱼", "海龙", "爬虫", "念动力", "幻神兽", "创造神", "幻龙"};
	public static final String[] CARDCAMP = new String[]{"不限", "OCG、TCG", "OCG", "TCG"};
	public static final String[] CARDRARE = new String[]{"不限", "平卡N", "银字R", "黄金GR", "平罕NR", "面闪SR", "金字UR", "爆闪PR", "全息HR", "鬼闪GHR", "平爆NPR", "红字RUR", "斜碎SCR", "银碎SER", "金碎USR", "立体UTR"};
	public static final String[] CARDATTRIBUTE = new String[]{"不限", "地", "水", "炎", "风", "光", "暗", "神"};
	public static final String[] CARDBAN = new String[]{"不限", "无限制", "准限制", "限制", "禁止", "观赏"};

	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public static void sysOut(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 功能描述:根据卡组名称，获取卡组中的内容，并将数据缓存在currentExtraCardMap和currentCardMap中
	 * 
	 * @param deckName
	 */
	public static void getDeck(String deckName) {
		File deck = new File(DECK_PATH + deckName + ".txt");
		if (deck.isFile() && deck.exists()) { // 判断文件是否存在
			U.currentCardMap.clear();
			U.currentExtraCardMap.clear();
			String encode = getCharset(deck);
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(deck), encode));
				String lineTxt = null;
				while ((lineTxt = readFile(reader)) != null) {
					if (!TextUtils.isEmpty(lineTxt) && !lineTxt.equals("主卡组") && !lineTxt.equals("额外卡组")) {
						String[] str = lineTxt.split("×");
						String cardName = str[0];
						int cardNum = Integer.parseInt(str[1]);
						String type = U.cardMap.get(cardName).getSCCardType();
						if (type.equals("XYZ怪兽") || type.equals("融合怪兽") || type.equals("同调怪兽"))
							U.currentExtraCardMap.put(cardName, cardNum);
						else
							U.currentCardMap.put(cardName, cardNum);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 功能描述:通过卡组映射将卡组保存在txt文件中
	 * 
	 * @param deckName
	 */
	public static void saveDeck(String deckName) {
		File deck = new File(DECK_PATH + deckName + ".txt");
		if (deck.isFile() && deck.exists()) { // 判断文件是否存在

			BufferedWriter writer = null;
			try {
				String encode = getCharset(deck);
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(deck), encode));
				writer.write("");
				writer.append("主卡组");
				writer.newLine();
				if (currentCardMap.size() > 0) {
					Set<String> keys = currentCardMap.keySet();
					for (String key : keys) {
						writer.append(key + "×" + currentCardMap.get(key));
						writer.newLine();
					}
				}
				writer.append("额外卡组");
				writer.newLine();
				if (currentExtraCardMap.size() > 0) {
					Set<String> keys = currentExtraCardMap.keySet();
					for (String key : keys) {
						writer.append(key + "×" + currentExtraCardMap.get(key));
						writer.newLine();
					}
				}
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 功能描述:将卡组映射转换成卡片集合
	 * 
	 * @param deckMap
	 * @return
	 */
	public static ArrayList<CardEntity> deckMapToArr(HashMap<String, Integer> deckMap) {
		ArrayList<CardEntity> deckArr = null;

		if (deckMap != null && deckMap.size() > 0) {
			deckArr = new ArrayList<CardEntity>();
			Set<String> keys = deckMap.keySet();
			for (String key : keys) {
				int count = deckMap.get(key);
				for (int i = 0; i < count; i++) {
					deckArr.add(U.cardMap.get(key));
				}
			}
		}
		return deckArr;
	}

	// 获取文件的编码格式
	public static String getCharset(File fileName) {
		BufferedInputStream bin = null;
		String code = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(fileName));
			byte[] b = new byte[10];
			bin.read(b, 0, b.length);
			String first = toHex(b);
			// 这里可以看到各种编码的前几个字符是什么，gbk编码前面没有多余的

			if (first.startsWith("EFBBBF")) {
				code = "UTF-8";
			} else if (first.startsWith("FEFF00")) {
				code = "UTF-16BE";
			} else if (first.startsWith("FFFE")) {
				code = "Unicode";
			} else {
				code = "GBK";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return code;
	}

	// 将字节数组转换成字符串
	public static String toHex(byte[] byteArray) {
		int i;
		StringBuffer buf = new StringBuffer("");
		int len = byteArray.length;
		for (int offset = 0; offset < len; offset++) {
			i = byteArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().toUpperCase();
	}

	/**
	 * 功能描述:读取文件的一行
	 * 
	 * @param reader
	 * @return
	 */
	public static String readFile(BufferedReader reader) {
		String content = null;
		try {
			content = reader.readLine();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 考虑到编码格式
		return content;
	}

	/**
	 * 功能描述:卡组排序
	 * 
	 * @param cards
	 * @return
	 */
	public static ArrayList<CardEntity> sortCards(ArrayList<CardEntity> cards) {
		ArrayList<CardEntity> result = null;

		ArrayList<CardEntity> monsters = new ArrayList<CardEntity>();
		ArrayList<CardEntity> spells = new ArrayList<CardEntity>();
		ArrayList<CardEntity> traps = new ArrayList<CardEntity>();

		if (cards != null && cards.size() > 0) {
			for (CardEntity cardEntity : cards) {
				if (cardEntity.getSCCardType().contains("怪兽"))
					monsters.add(cardEntity);
				else if (cardEntity.getSCCardType().contains("魔法"))
					spells.add(cardEntity);
				else
					traps.add(cardEntity);
			}

			Collections.sort(monsters, new Comparator<CardEntity>() {

				@Override
				public int compare(CardEntity lhs, CardEntity rhs) {
					return rhs.getCardStarNum() - lhs.getCardStarNum();
				}
			});

			result = new ArrayList<CardEntity>();
			for (CardEntity entity : monsters) {
				result.add(entity);
			}
			for (CardEntity entity : spells) {
				result.add(entity);
			}
			for (CardEntity entity : traps) {
				result.add(entity);
			}
		}

		return result;
	}
}
