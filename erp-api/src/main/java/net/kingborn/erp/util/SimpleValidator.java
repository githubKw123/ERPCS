package net.kingborn.erp.util;

import net.kingborn.core.util.StrKit;

import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;


/**
 * 一个简单的验证类
 * 
 * @author David Chen
 *
 */
public class SimpleValidator {

	private static final Pattern PATTERN_EMAIL = Pattern
			.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	private static final Pattern PATTERN_NICKNAME = Pattern.compile("^[\\x{4e00}-\\x{9fa5}a-zA-z0-9_.]+$");
	private static final Pattern PATTERN_USER_NAME = Pattern.compile("^[\\x{4e00}-\\x{9fa5}a-zA-z0-9_]+$");
	private static final Pattern PATTERN_PASSWORD = Pattern.compile("^[\\S]{6,20}$");
	private static final Pattern PATTERN_TRUENAME = Pattern.compile("^[\\x{4e00}-\\x{9fa5}]{2,5}$");
	private static final Pattern PATTERN_ID_CARD = Pattern.compile("^\\d{17}[0-9xX]$");
	private static final Pattern PATTERN_BANK_CARD_ID = Pattern.compile("^(\\d{16}|\\d{19})$");
	private static final Pattern PATTERN_MOBILE = Pattern.compile("^1\\d{10}$");
	private static final Pattern PATTERN_PHONE = Pattern.compile("^(\\d{4}-|\\d{3}-)?(\\d{8}|\\d{7})$");
	private static final Pattern PATTERN_DATE = Pattern
			.compile("^(\\d{4}|\\d{2})-((0([1-9]))|(1[0-2]))-((0[1-9])|([12]([0-9]))|(3[0|1]))$");
	private static final Pattern PATTERN_QQ = Pattern.compile("^[1-9]\\d{4,}$");
	private static final Pattern PATTERN_INTEGER = Pattern.compile("^[+-]?\\d{1,9}$");
	private static final Pattern PATTERN_MONEY = Pattern
			.compile("^(([+-]?[1-9]{1}\\d*)|([+-]?[0]{1}))(\\.(\\d){1,2})?$");
	private static final Pattern PATTERN_DATE_TIME = Pattern
			.compile("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$");
	private static final Pattern PATTERN_TIME = Pattern
			.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");
	private static final Pattern PATTERN_SITE = Pattern
			.compile("^(http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?$");
	private static final Pattern PATTERN_OSS_VIDEO_URL = Pattern.compile("^data\\/static\\/video\\/([a-zA-Z0-9_\\-\\.]+)(\\.mp4)$");

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		return PATTERN_EMAIL.matcher(email).matches();
	}

	/**
	 * 校验用户名
	 * 
	 * @param userName
	 * @return
	 */
	public static boolean validateUserName(String userName) {
		if (StrKit.isBlank(userName)) {
			return false;
		}

		if (validateInteger(userName)) {
			return false;
		}

		int length = userName.length();
		return length > 4 && length < 20 && PATTERN_USER_NAME.matcher(userName).matches();
	}

	/**
	 * 校验昵称
	 * 
	 * @param nickname
	 * @return
	 */
	public static boolean validateNickname(String nickname) {
		if (StrKit.isBlank(nickname)) {
			return false;
		}

		try {
			// TODO 一个汉字会被认为长度为3
			int length = nickname.getBytes("utf-8").length;
			return length > 3 && length < 20 && PATTERN_NICKNAME.matcher(nickname).matches();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return
	 */
	public static boolean validatePassword(String password) {
		return PATTERN_PASSWORD.matcher(password).matches();
	}

	/**
	 * 校验真实姓名
	 * 
	 * @param truename
	 * @return
	 */
	public static boolean validateTruename(String truename) {
		return PATTERN_TRUENAME.matcher(truename).matches();
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return
	 */
	public static boolean validateIdCard(String idCard) {
		return PATTERN_ID_CARD.matcher(idCard).matches();
	}

	/**
	 * 校验银行卡
	 * 
	 * @return
	 */
	public static boolean validateBankCardId(String bankCardId) {
		return PATTERN_BANK_CARD_ID.matcher(bankCardId).matches();
	}

	/**
	 * 校验手机
	 * 
	 * @return
	 */
	public static boolean validateMobile(String mobile) {
		return PATTERN_MOBILE.matcher(mobile).matches();
	}

	/**
	 * 校验电话
	 * 
	 * @return
	 */
	public static boolean validatePhone(String phone) {
		return PATTERN_PHONE.matcher(phone).matches();
	}

	/**
	 * 校验日期
	 * 
	 * @return
	 */
	public static boolean validateDate(String date) {
		return PATTERN_DATE.matcher(date).matches();
	}

	/**
	 * 校验QQ
	 * 
	 * @param qq
	 * @return
	 */
	public static boolean validateQq(String qq) {
		return PATTERN_QQ.matcher(qq).matches();
	}

	/**
	 * 校验自然数
	 * 
	 * @param intergerStr
	 * @return
	 */
	public static boolean validateInteger(String intergerStr) {
		return PATTERN_INTEGER.matcher(intergerStr).matches();
	}

	/**
	 * 校验金额
	 * 
	 * @param moneyStr
	 * @return
	 */
	public static boolean validateMoney(String moneyStr) {
		return PATTERN_MONEY.matcher(moneyStr).matches();
	}

	/**
	 * 校验日期时间
	 * 
	 * @param dateTime
	 * @return
	 */
	public static boolean validateDateTime(String dateTime) {
		return PATTERN_DATE_TIME.matcher(dateTime).matches();
	}

	/**
	 * 校验网址
	 * 
	 * @param site
	 * @return
	 */
	public static boolean validateSite(String site) {
		return PATTERN_SITE.matcher(site).matches();
	}

	/**
	 * 校验日期时间
	 *
	 * @param time
	 * @return
	 */
	public static boolean validateTime(String time) {
		return PATTERN_TIME.matcher(time).matches();
	}

	/**
	 * 验证oss视频资源地址
	 *
	 * @param ossVidelUrl
	 * @return
	 */
	public static boolean validateOssVideoUrl(String ossVidelUrl) {
		return PATTERN_OSS_VIDEO_URL.matcher(ossVidelUrl).matches();
	}
}
