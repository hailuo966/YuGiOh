package com.lyy.yugioh.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "YGODATA")
public class CardEntity
{
	@DatabaseField(generatedId = true)
	private int _id;// 自增id，主键
	@DatabaseField
	private int CardID; // ==_id，==图片id+1
	@DatabaseField
	private String CardPhAl;// （空）
	@DatabaseField
	private String CardCamp;// 卡片归属，tcg、ocg
	@DatabaseField
	private String JPCardName;// 卡片日文名
	@DatabaseField
	private String SCCardName;// 卡片简体中文名
	@DatabaseField
	private String TCCardName;// 卡片繁体中文名
	@DatabaseField
	private String ENCardName;// 卡片英文名
	@DatabaseField
	private String ENCardName2;// 卡片大写英文名
	@DatabaseField
	private String JPCardType;// 卡片日文类型（空）
	@DatabaseField
	private String SCCardType;// 卡片中文类型 魔法、陷阱、怪兽
	@DatabaseField
	private String TCCardType;// 卡片中文繁体类型
	@DatabaseField
	private String ENCardType;// 卡片英文类型
	@DatabaseField
	private String JPDCardType;// （空）
	@DatabaseField
	private String SCDCardType;// （空）
	@DatabaseField
	private String TCDCardType;// （空）
	@DatabaseField
	private String ENDCardType;// （空）
	@DatabaseField
	private String JPCardRace;// （空）
	@DatabaseField
	private String SCCardRace;// 卡片简体中文种族
	@DatabaseField
	private String TCCardRace;// 卡片繁体中文种族
	@DatabaseField
	private String ENCardRace;// 卡片英文种族
	@DatabaseField
	private String CardBagNum;// 卡包号
	@DatabaseField
	private String JPCardAttribute;// （空）
	@DatabaseField
	private String SCCardAttribute;// 卡片简体中文属性
	@DatabaseField
	private String TCCardAttribute;// 卡片繁体中文属性
	@DatabaseField
	private String ENCardAttribute;// 卡片英文属性
	@DatabaseField
	private int CardStarNum;//
	@DatabaseField
	private String SCCardRare;// 卡片简体中文种类 平卡、金字、立体。。。
	@DatabaseField
	private String TCCardRare;// 卡片繁体中文种类
	@DatabaseField
	private String ENCardRare;// 卡片英文种类
	@DatabaseField
	private int CardAtk;// 攻击力
	@DatabaseField
	private String CardAtk2;// 攻击力2
	@DatabaseField
	private int CardDef;// 守备力
	@DatabaseField
	private String CardDef2;// 守备力2
	@DatabaseField
	private String JPCardDepict;// 日文描述
	@DatabaseField
	private String SCCardDepict;// 简体中文描述
	@DatabaseField
	private String TCCardDepict;// 繁体中文描述
	@DatabaseField
	private String ENCardDepict;// 英文描述
	@DatabaseField
	private String SCCardBan;// 卡片简体中文限制
	@DatabaseField
	private String TCCardBan;// 卡片繁体中文限制
	@DatabaseField
	private String ENCardBan;// 卡片英文限制
	@DatabaseField
	private int CardIsYKDT;//
	@DatabaseField
	private int CardIsTKEN;//
	@DatabaseField
	private String CardIsCZN;//
	@DatabaseField
	private String CardPass;// 卡片密码
	@DatabaseField
	private String CardAdjust;//
	@DatabaseField
	private int CardLover;//
	@DatabaseField
	private String CardUnion;// 卡片关联id
	@DatabaseField
	private String CardOnceName;// 卡片过时名称
	@DatabaseField
	private String CardAbbrName;// 卡片别称
	@DatabaseField
	private String CardEfficeType;//

	public int get_id()
	{
		return _id;
	}

	public void set_id(int _id)
	{
		this._id = _id;
	}

	public int getCardID()
	{
		return CardID;
	}

	public void setCardID(int cardID)
	{
		CardID = cardID;
	}

	public String getCardPhAl()
	{
		return CardPhAl == null ? "" : CardPhAl;
	}

	public void setCardPhAl(String cardPhAl)
	{
		CardPhAl = cardPhAl;
	}

	public String getCardCamp()
	{
		return CardCamp == null ? "" : CardCamp;
	}

	public void setCardCamp(String cardCamp)
	{
		CardCamp = cardCamp;
	}

	public String getJPCardName()
	{
		return JPCardName == null ? "" : JPCardName;
	}

	public void setJPCardName(String jPCardName)
	{
		JPCardName = jPCardName;
	}

	public String getSCCardName()
	{
		return SCCardName == null ? "" : SCCardName;
	}

	public void setSCCardName(String sCCardName)
	{
		SCCardName = sCCardName;
	}

	public String getTCCardName()
	{
		return TCCardName == null ? "" : TCCardName;
	}

	public void setTCCardName(String tCCardName)
	{
		TCCardName = tCCardName;
	}

	public String getENCardName()
	{
		return ENCardName == null ? "" : ENCardName;
	}

	public void setENCardName(String eNCardName)
	{
		ENCardName = eNCardName;
	}

	public String getENCardName2()
	{
		return ENCardName2 == null ? "" : ENCardName2;
	}

	public void setENCardName2(String eNCardName2)
	{
		ENCardName2 = eNCardName2;
	}

	public String getJPCardType()
	{
		return JPCardType == null ? "" : JPCardType;
	}

	public void setJPCardType(String jPCardType)
	{
		JPCardType = jPCardType;
	}

	public String getSCCardType()
	{
		return SCCardType == null ? "" : SCCardType;
	}

	public void setSCCardType(String sCCardType)
	{
		SCCardType = sCCardType;
	}

	public String getTCCardType()
	{
		return TCCardType == null ? "" : TCCardType;
	}

	public void setTCCardType(String tCCardType)
	{
		TCCardType = tCCardType;
	}

	public String getENCardType()
	{
		return ENCardType == null ? "" : ENCardType;
	}

	public void setENCardType(String eNCardType)
	{
		ENCardType = eNCardType;
	}

	public String getJPDCardType()
	{
		return JPDCardType == null ? "" : JPDCardType;
	}

	public void setJPDCardType(String jPDCardType)
	{
		JPDCardType = jPDCardType;
	}

	public String getSCDCardType()
	{
		return SCDCardType == null ? "" : SCDCardType;
	}

	public void setSCDCardType(String sCDCardType)
	{
		SCDCardType = sCDCardType;
	}

	public String getTCDCardType()
	{
		return TCDCardType == null ? "" : TCDCardType;
	}

	public void setTCDCardType(String tCDCardType)
	{
		TCDCardType = tCDCardType;
	}

	public String getENDCardType()
	{
		return ENDCardType == null ? "" : ENDCardType;
	}

	public void setENDCardType(String eNDCardType)
	{
		ENDCardType = eNDCardType;
	}

	public String getJPCardRace()
	{
		return JPCardRace == null ? "" : JPCardRace;
	}

	public void setJPCardRace(String jPCardRace)
	{
		JPCardRace = jPCardRace;
	}

	public String getSCCardRace()
	{
		return SCCardRace == null ? "" : SCCardRace;
	}

	public void setSCCardRace(String sCCardRace)
	{
		SCCardRace = sCCardRace;
	}

	public String getTCCardRace()
	{
		return TCCardRace == null ? "" : TCCardRace;
	}

	public void setTCCardRace(String tCCardRace)
	{
		TCCardRace = tCCardRace;
	}

	public String getENCardRace()
	{
		return ENCardRace == null ? "" : ENCardRace;
	}

	public void setENCardRace(String eNCardRace)
	{
		ENCardRace = eNCardRace;
	}

	public String getCardBagNum()
	{
		return CardBagNum == null ? "" : CardBagNum;
	}

	public void setCardBagNum(String cardBagNum)
	{
		CardBagNum = cardBagNum;
	}

	public String getJPCardAttribute()
	{
		return JPCardAttribute == null ? "" : JPCardAttribute;
	}

	public void setJPCardAttribute(String jPCardAttribute)
	{
		JPCardAttribute = jPCardAttribute;
	}

	public String getSCCardAttribute()
	{
		return SCCardAttribute == null ? "" : SCCardAttribute;
	}

	public void setSCCardAttribute(String sCCardAttribute)
	{
		SCCardAttribute = sCCardAttribute;
	}

	public String getTCCardAttribute()
	{
		return TCCardAttribute == null ? "" : TCCardAttribute;
	}

	public void setTCCardAttribute(String tCCardAttribute)
	{
		TCCardAttribute = tCCardAttribute;
	}

	public String getENCardAttribute()
	{
		return ENCardAttribute == null ? "" : ENCardAttribute;
	}

	public void setENCardAttribute(String eNCardAttribute)
	{
		ENCardAttribute = eNCardAttribute;
	}

	public String getCardStarNumStr()
	{
		return CardStarNum + "星";
	}

	public int getCardStarNum()
	{
		return CardStarNum;
	}

	public void setCardStarNum(int cardStarNum)
	{
		CardStarNum = cardStarNum;
	}

	public String getSCCardRare()
	{
		return SCCardRare == null ? "" : SCCardRare;
	}

	public void setSCCardRare(String sCCardRare)
	{
		SCCardRare = sCCardRare;
	}

	public String getTCCardRare()
	{
		return TCCardRare == null ? "" : TCCardRare;
	}

	public void setTCCardRare(String tCCardRare)
	{
		TCCardRare = tCCardRare;
	}

	public String getENCardRare()
	{
		return ENCardRare == null ? "" : ENCardRare;
	}

	public void setENCardRare(String eNCardRare)
	{
		ENCardRare = eNCardRare;
	}

	public int getCardAtk()
	{
		return CardAtk;
	}

	public void setCardAtk(int cardAtk)
	{
		CardAtk = cardAtk;
	}

	public String getCardAtk2()
	{
		return CardAtk2 == null ? "" : CardAtk2;
	}

	public void setCardAtk2(String cardAtk2)
	{
		CardAtk2 = cardAtk2;
	}

	public int getCardDef()
	{
		return CardDef;
	}

	public void setCardDef(int cardDef)
	{
		CardDef = cardDef;
	}

	public String getCardDef2()
	{
		return CardDef2 == null ? "" : CardDef2;
	}

	public void setCardDef2(String cardDef2)
	{
		CardDef2 = cardDef2;
	}

	public String getJPCardDepict()
	{
		return JPCardDepict == null ? "" : JPCardDepict;
	}

	public void setJPCardDepict(String jPCardDepict)
	{
		JPCardDepict = jPCardDepict;
	}

	public String getSCCardDepict()
	{
		return SCCardDepict == null ? "" : SCCardDepict;
	}

	public void setSCCardDepict(String sCCardDepict)
	{
		SCCardDepict = sCCardDepict;
	}

	public String getTCCardDepict()
	{
		return TCCardDepict == null ? "" : TCCardDepict;
	}

	public void setTCCardDepict(String tCCardDepict)
	{
		TCCardDepict = tCCardDepict;
	}

	public String getENCardDepict()
	{
		return ENCardDepict == null ? "" : ENCardDepict;
	}

	public void setENCardDepict(String eNCardDepict)
	{
		ENCardDepict = eNCardDepict;
	}

	public String getSCCardBan()
	{
		return SCCardBan == null ? "" : SCCardBan;
	}

	public void setSCCardBan(String sCCardBan)
	{
		SCCardBan = sCCardBan;
	}

	public String getTCCardBan()
	{
		return TCCardBan == null ? "" : TCCardBan;
	}

	public void setTCCardBan(String tCCardBan)
	{
		TCCardBan = tCCardBan;
	}

	public String getENCardBan()
	{
		return ENCardBan == null ? "" : ENCardBan;
	}

	public void setENCardBan(String eNCardBan)
	{
		ENCardBan = eNCardBan;
	}

	public int getCardIsYKDT()
	{
		return CardIsYKDT;
	}

	public void setCardIsYKDT(int cardIsYKDT)
	{
		CardIsYKDT = cardIsYKDT;
	}

	public int getCardIsTKEN()
	{
		return CardIsTKEN;
	}

	public void setCardIsTKEN(int cardIsTKEN)
	{
		CardIsTKEN = cardIsTKEN;
	}

	public String getCardIsCZN()
	{
		return CardIsCZN == null ? "" : CardIsCZN;
	}

	public void setCardIsCZN(String cardIsCZN)
	{
		CardIsCZN = cardIsCZN;
	}

	public String getCardPass()
	{
		return CardPass == null ? "" : CardPass;
	}

	public void setCardPass(String cardPass)
	{
		CardPass = cardPass;
	}

	public String getCardAdjust()
	{
		return CardAdjust == null ? "" : CardAdjust;
	}

	public void setCardAdjust(String cardAdjust)
	{
		CardAdjust = cardAdjust;
	}

	public int getCardLover()
	{
		return CardLover;
	}

	public void setCardLover(int cardLover)
	{
		CardLover = cardLover;
	}

	public String getCardUnion()
	{
		return CardUnion == null ? "" : CardUnion;
	}

	public void setCardUnion(String cardUnion)
	{
		CardUnion = cardUnion;
	}

	public String getCardOnceName()
	{
		return CardOnceName == null ? "" : CardOnceName;
	}

	public void setCardOnceName(String cardOnceName)
	{
		CardOnceName = cardOnceName;
	}

	public String getCardAbbrName()
	{
		return CardAbbrName == null ? "" : CardAbbrName;
	}

	public void setCardAbbrName(String cardAbbrName)
	{
		CardAbbrName = cardAbbrName;
	}

	public String getCardEfficeType()
	{
		return CardEfficeType == null ? "" : CardEfficeType;
	}

	public void setCardEfficeType(String cardEfficeType)
	{
		CardEfficeType = cardEfficeType;
	}

}
