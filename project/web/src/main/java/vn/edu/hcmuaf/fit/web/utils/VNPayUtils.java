package vn.edu.hcmuaf.fit.web.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class VNPayUtils {

    public static String vnp_TmnCode = "4RVOCTR9"; // Mã Website (Terminal code) của bạn do VNPAY cung cấp
    public static String vnp_hashSecret = "4T1JNCBNVNJP7O8DE1KR44FCEMQMNCC0"; // Chuỗi bí mật dùng để tạo checksum
    public static String vnp_Returnurl = "https://yourdomain.com/vnpay_return"; // URL trả về sau khi thanh toán
    public static String vnp_PayUrl= "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    // Hàm tạo chuỗi HMAC SHA512
    public static String hmacSHA512(String key, String data) {
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

    // Hàm tạo số ngẫu nhiên
    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Hàm lấy IP client
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
