package com.lyy.yugioh.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CARDSET")
public class CardSetEntity
{
	@DatabaseField(generatedId = true)
	private int _id;
	@DatabaseField
	private int CardID;
	@DatabaseField
	private int CardSetID;
	@DatabaseField
	private String CardSetName;
	@DatabaseField
	private int CardSetType;

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

	public int getCardSetID()
	{
		return CardSetID;
	}

	public void setCardSetID(int cardSetID)
	{
		CardSetID = cardSetID;
	}

	public String getCardSetName()
	{
		return CardSetName == null ? "" : CardSetName;
	}

	public void setCardSetName(String cardSetName)
	{
		CardSetName = cardSetName;
	}

	public int getCardSetType()
	{
		return CardSetType;
	}

	public void setCardSetType(int cardSetType)
	{
		CardSetType = cardSetType;
	}

}
