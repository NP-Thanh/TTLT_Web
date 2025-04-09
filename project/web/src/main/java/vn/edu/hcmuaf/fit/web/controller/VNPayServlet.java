package vn.edu.hcmuaf.fit.web.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.utils.VNPayUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet(name = "VNPayServlet", value = "/VNPay")
public class VNPayServlet extends HttpServlet {
    private static final String VNP_TMN_CODE = "4RVOCTR9";
    private static final String VNP_HASH_SECRET = "4T1JNCBNVNJP7O8DE1KR44FCEMQMNCC0";
    private static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static final String VNP_RETURN_URL = "http://localhost:8080/web/home";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
//        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_OrderInfo = "Thanh toan don hang 23131";
//        String orderType = request.getParameter("ordertype");
        String orderType = "billpayment";
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_IpAddr = VNPayUtils.getIpAddress(request);
        String vnp_TmnCode = VNP_TMN_CODE;

//        int amount = Integer.parseInt(request.getParameter("amount")) * 100;
        int amount = 1000000;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
//        String bank_code = request.getParameter("bankcode");
        String bank_code = "NCB";
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VNP_RETURN_URL);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
//        vnp_Params.put("vnp_Bill_Mobile", request.getParameter("txt_billing_mobile"));
//        vnp_Params.put("vnp_Bill_Email", request.getParameter("txt_billing_email"));
//        String fullName = (request.getParameter("txt_billing_fullname")).trim();
        String fullName = "Nguyen Le";
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);

        }
//        vnp_Params.put("vnp_Bill_Address", request.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Bill_City", request.getParameter("txt_bill_city"));
//        vnp_Params.put("vnp_Bill_Country", request.getParameter("txt_bill_country"));
        if (request.getParameter("txt_bill_state") != null && !request.getParameter("txt_bill_state").isEmpty()) {
            vnp_Params.put("vnp_Bill_State", request.getParameter("txt_bill_state"));
        }
        // Invoice
//        vnp_Params.put("vnp_Inv_Phone", request.getParameter("txt_inv_mobile"));
//        vnp_Params.put("vnp_Inv_Email", request.getParameter("txt_inv_email"));
//        vnp_Params.put("vnp_Inv_Customer", request.getParameter("txt_inv_customer"));
//        vnp_Params.put("vnp_Inv_Address", request.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Inv_Company", request.getParameter("txt_inv_company"));
//        vnp_Params.put("vnp_Inv_Taxcode", request.getParameter("txt_inv_taxcode"));
//        vnp_Params.put("vnp_Inv_Type", request.getParameter("cbo_inv_type"));
        //Build data to hash and querystring
        List<String> fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=').append(fieldValue);
                hashData.append('&');
            }
        }
        // Xoá dấu `&` cuối
        if (hashData.length() > 0) {
            hashData.setLength(hashData.length() - 1);
        }
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append('&');
            }
        }
        // Xoá dấu `&` cuối
        if (query.length() > 0) {
            query.setLength(query.length() - 1);
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = null;
        try {
            vnp_SecureHash = VNPayUtils.hmacSHA512(VNP_HASH_SECRET, String.valueOf(hashData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("HashData = " + hashData.toString());
        System.out.println("SecureHash = " + vnp_SecureHash);
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNP_URL + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(job));
    }
}
