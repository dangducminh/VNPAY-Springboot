package com.example.vnpay;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PaymentConfig {
	public static String vnp_Version = "2.1.0"; // Phiên bản api mà merchant kết nối. Phiên bản hiện tại là : 2.0.1 và 2.1.0
	public static String vnp_Command = "pay";//Mã API sử dụng, mã cho giao dịch thanh toán là: pay
	public static String vnp_TmnCode = "BJ9ZILWP"; //Mã website của merchant trên hệ thống của VNPAY. Ví dụ: 2QXUI4J4
	public static String vnp_CurrCode = "VND";
	public static String vnp_Locale = "vn";
	public static String vnp_OrderType = "100000";
	public static String vnp_ReturnUrl = "http://localhost:8080/vnpay_jsp/vnpay_return.jsp";
	public static String vnp_HashSecret = "HEMVEOBMAWSXSMKKUTEDHPFWEPXSTVRY";
	public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

	public static String getOTP(int len) {
		Random rnd = new Random();
		String chars = "0123456789";
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}

	public static String Sha256(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(message.getBytes("UTF-8"));

			// converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}

			digest = sb.toString();

		} catch (UnsupportedEncodingException ex) {
			digest = "";
			// Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
			// null, ex);
		} catch (NoSuchAlgorithmException ex) {
			// Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE,
			// null, ex);
			digest = "";
		}
		return digest;
	}

	public static String hmacSHA512(final String key, final String data) {
		try {

			if (key == null || data == null) {
				throw new NullPointerException();
			}
			final Mac hmac512 = Mac.getInstance("HmacSHA512");
			byte[] hmacKeyBytes = key.getBytes();
			final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
			hmac512.init(secretKey);
			byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
			byte[] result = hmac512.doFinal(dataBytes);
			StringBuilder sb = new StringBuilder(2 * result.length);
			for (byte b : result) {
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();

		} catch (Exception ex) {
			return "";
		}
	}

	public static String getIpAddress(HttpServletRequest request) {
		String ipAdress;
		try {
			ipAdress = request.getHeader("X-FORWARDED-FOR");
			if (ipAdress == null) {
				ipAdress = request.getRemoteAddr();
			}
		} catch (Exception e) {
			ipAdress = "Invalid IP:" + e.getMessage();
		}
		return ipAdress;
	}
}
