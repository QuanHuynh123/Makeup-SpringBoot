package com.example.Makeup.service.common;

import com.example.Makeup.config.VNPAYConfig;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class VNPAYService {

  public record VnpayReturnResult(
      boolean signatureValid,
      boolean paymentSuccess,
      String responseCode,
      String transactionStatus,
      String orderInfo,
      String transactionId,
      String txnRef,
      long amount,
      String payDate) {}

  public String createOrder(
      int total, String orderInfor, String urlReturn, HttpServletRequest request) {
    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String vnp_TxnRef = VNPAYConfig.getRandomNumber(8);
    String vnp_IpAddr = VNPAYConfig.getIpAddress(request);
    String vnp_TmnCode = VNPAYConfig.vnp_TmnCode;
    String orderType = "order-type";

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(total * 100));
    vnp_Params.put("vnp_CurrCode", "VND");

    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", orderInfor);
    vnp_Params.put("vnp_OrderType", orderType);
    vnp_Params.put("vnp_BankCode", "NCB");

    String locate = "vn";
    vnp_Params.put("vnp_Locale", locate);

    urlReturn += VNPAYConfig.vnp_Returnurl;
    vnp_Params.put("vnp_ReturnUrl", urlReturn);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        try {
          hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
          // Build query
          query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
          query.append('=');
          query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = VNPAYConfig.hmacSHA512(VNPAYConfig.vnp_HashSecret, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = VNPAYConfig.vnp_PayUrl + "?" + queryUrl;
    return paymentUrl;
  }

  public int orderReturn(HttpServletRequest request) {
    VnpayReturnResult result = parseAndVerifyReturn(request);
    if (!result.signatureValid()) {
      return -1;
    }
    return result.paymentSuccess() ? 1 : 0;
  }

  public VnpayReturnResult parseAndVerifyReturn(HttpServletRequest request) {
    Map<String, String> fields = new HashMap<>();
    Enumeration<String> parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      String fieldName = parameterNames.nextElement();
      String fieldValue = request.getParameter(fieldName);
      if (fieldValue != null && !fieldValue.isBlank()) {
        fields.put(fieldName, fieldValue);
      }
    }

    String secureHash = fields.remove("vnp_SecureHash");
    fields.remove("vnp_SecureHashType");
    String calculatedHash = VNPAYConfig.hashAllFields(fields);
    boolean signatureValid =
        secureHash != null && secureHash.equalsIgnoreCase(calculatedHash);

    String responseCode = fields.get("vnp_ResponseCode");
    String transactionStatus = fields.get("vnp_TransactionStatus");
    boolean paymentSuccess =
        signatureValid && "00".equals(responseCode) && "00".equals(transactionStatus);

    return new VnpayReturnResult(
        signatureValid,
        paymentSuccess,
        responseCode,
        transactionStatus,
        fields.get("vnp_OrderInfo"),
        fields.get("vnp_TransactionNo"),
        fields.get("vnp_TxnRef"),
        parseLongSafe(fields.get("vnp_Amount")),
        fields.get("vnp_PayDate"));
  }

  private long parseLongSafe(String value) {
    if (value == null || value.isBlank()) {
      return 0L;
    }
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException ex) {
      return 0L;
    }
  }
}
