package com.lyy.yugioh.activity;

/**@作者:刘焰宇
 * @创建时间:2013-6-14上午9:57:29
 * @类名:IActivityInit	
 * @功能描述: activity初始化接口
 * @版本:1.0
 */
public interface IActivity {
	
	/**功能描述:初始化控件
	 * 
	 */
	public void findViews();

	/**功能描述:初始化变量
	 * 
	 */
	public void initVars();

	/**功能描述:设置事件
	 * 
	 */
	public void setListeners();
}
