package com.chn.halo.core;

import android.app.Activity;

import java.util.Stack;

/**
 *
 * @description Activity 管理类
 *
 * @author Halo-CHN
 *
 * @mail halo-chn@outlook.com
 *
 * @date 2015年6月18日
 *
 * @version 1.0
 *
 */
public class ActivityController {

	/* Activity栈 */
	private static Stack<Activity> stack = new Stack<Activity>();

	/**
	 * @return 获取当前运行的所有Activity
	 */
	public static Stack<Activity> getAllActivities() {
		return stack;
	}

	/**
	 * 移除所有activity
	 */
	public static void popAll() {
		while (!stack.isEmpty()) {
			removeTop();
		}
	}

	/**
	 * 移除位于栈顶的activity
	 */
	public static void removeTop() {
		if (!stack.isEmpty()) {
			Activity activity = stack.pop();
			if (activity != null && !activity.isFinishing()) {
				activity.finish();
			}
		}
	}

	/**
	 * 移除指定activity
	 * 
	 * @param activity
	 *            指定要移除的activity
	 */
	public static void remove(Activity activity) {
		if (!stack.isEmpty()) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
			stack.remove(activity);
		}
	}

	/**
	 * 移除并关闭指定某一类的activity
	 * 
	 * @param aClass
	 *            类名称
	 */
	public static void removeByClass(Class<? extends Activity> aClass) {
		Stack<Activity> newStack = new Stack<Activity>();
		for (Activity a : stack) {
			if (null != a) {
				if (a.getClass().equals(aClass)) {
					if (!a.isFinishing()) {
						a.finish();
					}
				} else {
					newStack.push(a);
				}
			}
		}
		stack = newStack;
	}

	/**
	 * @return 获取在栈顶的activity
	 */
	public static Activity getCurrentActivity() {
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	/**
	 * 添加activity到栈中
	 * 
	 * @param activity
	 *            添加的activity
	 */
	public static void add(Activity activity) {
		if (null != activity)
			stack.push(activity);
	}

	/**
	 * 保留某一类的activity，其它的都关闭并移出栈
	 * 
	 * @param aClass
	 *            类名称
	 */
	public static void retainByClass(Class<? extends Activity> aClass) {
		Stack<Activity> newStack = new Stack<Activity>();
		for (Activity a : stack) {
			if (a.getClass().equals(aClass)) {
				newStack.push(a);
			} else {
				if (!a.isFinishing()) {
					a.finish();
				}
			}
		}
		stack = newStack;
	}
}