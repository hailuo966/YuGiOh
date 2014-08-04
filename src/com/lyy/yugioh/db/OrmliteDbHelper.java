package com.lyy.yugioh.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * @作者:刘焰宇
 * @创建时间:2013-6-4上午9:43:52
 * @类名:OrmliteDbHelper
 * @功能描述:使用Ormlite的数据库辅助操作类
 * @版本:1.0
 */
public class OrmliteDbHelper extends OrmLiteSqliteOpenHelper
{

	/**
	 * 数据库所在目录
	 */
	private static final String DBPATH = "/data/data/com.lyy.yugioh/database/";

	/**
	 * 数据库名称
	 */
	private static final String DBNAME = "yugioh.db";

	/**
	 * 数据库版本
	 */
	private static final int VERSION = 1;

	private static OrmliteDbHelper helper = null;
	public static AndroidDatabaseConnection dbConnection;

	public OrmliteDbHelper(Context context)
	{
		super(context, DBPATH + DBNAME, null, VERSION);
	}

	public static void initHelper(Context context)
	{
		if (helper == null)
		{
			helper = new OrmliteDbHelper(context);
			dbConnection = new AndroidDatabaseConnection(helper.getWritableDatabase(), true);
		}
	}

	public static OrmliteDbHelper getInstance()
	{
		return helper;
	}

	public static AndroidDatabaseConnection getConnection()
	{
		return dbConnection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onCreate(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource)
	 */
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1)
	{

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper#onUpgrade(android
	 * .database.sqlite.SQLiteDatabase,
	 * com.j256.ormlite.support.ConnectionSource, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @功能描述 如果数据库不存在，则从assets文件夹复制数据库到指定位置
	 * @输入参数
	 * @反馈值
	 */
	public static void copyDataBase(Context context, int Flag)
	{
		String outFileName = DBPATH + DBNAME;
		if (Flag == 0)
		{
			try
			{
				File file = new File(outFileName);
				// 如果文件不存在,创建文件
				if (!file.exists())
				{
					File dir = new File(DBPATH);
					// 先检查目录,如果目录不存在则创建目录
					if (!dir.exists())
					{
						// 创建目录
						if (!dir.mkdirs())
						{
							throw new Exception("创建数据库目录：" + DBPATH + "失败");
						}
					}

					// 创建文件
					if (file.createNewFile())
					{
						InputStream myInput = context.getAssets().open(DBNAME);
						OutputStream myOutput = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						int length;
						while ((length = myInput.read(buffer)) > 0)
						{
							myOutput.write(buffer, 0, length);
							myOutput.flush();
						}
						myOutput.close();
						myInput.close();
					} else
					{
						throw new Exception("创建数据库文件：" + outFileName + "失败");
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (Flag == 1)
		{
			try
			{
				File file = new File(outFileName);
				// 如果文件不存在,创建文件
				if (file.exists())
				{
					file.delete();
				}
				File dir = new File(DBPATH);
				// 先检查目录,如果目录不存在则创建目录
				if (!dir.exists())
				{
					// 创建目录
					if (!dir.mkdirs())
					{
						throw new Exception("创建数据库目录：" + DBPATH + "失败");
					}
				}

				// 创建文件
				if (file.createNewFile())
				{
					InputStream myInput = context.getAssets().open(DBNAME);
					OutputStream myOutput = new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					int length;
					while ((length = myInput.read(buffer)) > 0)
					{
						myOutput.write(buffer, 0, length);
						myOutput.flush();
					}
					myOutput.close();
					myInput.close();
				} else
				{
					throw new Exception("创建数据库文件：" + outFileName + "失败");
				}

			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @功能描述 删除数据库
	 * @输入参数
	 * @反馈值
	 */
	public static void deleteDataBase()
	{
		String fileFullPath = DBPATH + DBNAME;
		try
		{
			File file = new File(fileFullPath);
			file.delete();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 功能描述:获取实体类的DAO实例
	 * 
	 * @param <D>
	 * @param <T>
	 * @param context
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public static <D extends com.j256.ormlite.dao.Dao<T, ?>, T> D getDao(Context context, Class<T> clazz) throws SQLException
	{
		return helper.getDao(clazz);
	}

	/**
	 * 
	 * @功能描述 执行查询语句,将结果保存在一个字符串数组列表中
	 * @输入参数
	 * @反馈值 字符串数组列表
	 */
	public ArrayList<String[]> getDataset(String sql, String[] selectionArgs)
	{
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.rawQuery(sql, selectionArgs);
		ArrayList<String[]> ar = new ArrayList<String[]>();
		ar = getDataset(c);
		c.close();
		db.close();
		return ar;
	}

	/**
	 * 
	 * @功能描述 将游标的数据转存到一个字符串数组列表中 如果列的数据空，设置默认值
	 * @输入参数 游标
	 * @反馈值 字符串数组列表
	 */
	static ArrayList<String[]> getDataset(final Cursor c)
	{
		ArrayList<String[]> ar = new ArrayList<String[]>();
		if (c.moveToFirst())
		{
			int nNumcol = c.getColumnCount();
			do
			{

				String[] values = new String[nNumcol];
				for (int i = 0; i < nNumcol; i++)
				{
					String temp = c.getString(i);
					values[i] = TextUtils.isEmpty(temp) ? "null" : temp;
				}
				ar.add(values);
			} while (c.moveToNext());
		}
		return ar;
	}

	/**
	 * 执行非查询语句
	 * 
	 * @param 非查询sql语句
	 */
	public void execSQL(String sql) throws SQLException
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql);
		db.close();
	}

	/**
	 * 功能描述:执行非查询语句
	 * 
	 * @param sql
	 * @param object
	 * @throws SQLException
	 */
	public void execSQL(String sql, Object[] object) throws SQLException
	{
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql, object);
		db.close();
	}

	/**
	 * 以事务方式执行一系列非查询语句
	 * 
	 * @param sqls
	 */
	public void beginTran(List<String> sqls) throws SQLException
	{
		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();
		try
		{
			for (int i = 0; i < sqls.size(); i++)
			{
				db.execSQL(sqls.get(i));
			}
			db.setTransactionSuccessful();
		} finally
		{
			db.endTransaction();
			db.close();
		}
	}

	/**
	 * 根据条件获取某个表的记录的条数
	 * 
	 * @param table
	 * @param selection
	 * @return
	 */
	public int getTotalCount(String table, String selection)
	{
		StringBuilder query = new StringBuilder(120);

		query.append("SELECT * FROM " + table + " WHERE ");
		if (!TextUtils.isEmpty(selection))
		{
			query.append(selection);
		}
		SQLiteDatabase db = getWritableDatabase();
		int ncount = 0;
		Cursor c = db.rawQuery(query.toString(), null);
		if (c.moveToFirst())
			ncount = c.getCount();
		c.close();
		db.close();
		return ncount;
	}

	/**
	 * 插入记录
	 * 
	 * @param table
	 * @param nullColumnHack
	 * @param values
	 * @return
	 */
	public long insert(String table, String nullColumnHack, ContentValues values)
	{
		long lid = 0;
		SQLiteDatabase db = getWritableDatabase();
		lid = db.insert(table, nullColumnHack, values);
		db.close();
		return lid;
	}

	/**
	 * 更新记录
	 * 
	 * @param table
	 * @param values
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs)
	{
		int row = 0;
		SQLiteDatabase db = getWritableDatabase();
		row = db.update(table, values, whereClause, whereArgs);
		db.close();
		return row;
	}

	/**
	 * 删除记录
	 * 
	 * @param table
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public int delete(String table, String whereClause, String[] whereArgs)
	{
		int row = 0;
		SQLiteDatabase db = getWritableDatabase();
		row = db.delete(table, whereClause, whereArgs);
		db.close();
		return row;
	}

}
