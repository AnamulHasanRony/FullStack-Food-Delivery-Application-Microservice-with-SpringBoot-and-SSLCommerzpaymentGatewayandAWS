package com.anamul.payment_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class SslCommerzClient {

        @Value("${sslCommerz.store.id}")
        private String storeId;

        @Value("${sslCommerz.secret.key}")
        private String storePassword;
        @Value("${backend.base.url}")
        private String BACKEND_BASE_URL;

        private static final String SANDBOX_URL =
                "https://sandbox.sslcommerz.com/gwprocess/v4/api.php";

        private static final String SANDBOX_PAYMENT_VERIFY_URL=
                "https://sandbox.sslcommerz.com/validator/api/validationserverAPI.php";


    public String createSession(Map<String, String> params) {

            params.put("store_id", storeId);
            params.put("store_passwd", storePassword);
            params.put("success_url", BACKEND_BASE_URL+"/api/payment/success");
            params.put("fail_url",BACKEND_BASE_URL+"/api/payment/fail" );
            params.put("cancel_url", BACKEND_BASE_URL+"/api/payment/cancel");
            params.put("ipn_url", BACKEND_BASE_URL+"/api/payment/ipn");


        RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.setAll(params);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response ;

            try {
                response = restTemplate.postForEntity(SANDBOX_URL, request, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("SSLCommerz request failed: " + e.getMessage());
            }

            Map responseBody = response.getBody();

            System.out.println("===== SSLCommerz Response =====");
            System.out.println(responseBody);

            if (responseBody != null && "SUCCESS".equals(responseBody.get("status"))) {
                return responseBody.get("GatewayPageURL").toString();
            }

            throw new RuntimeException("SSLCommerz session creation failed");
        }

        public boolean verifyPayment(String tranId, String valId){



            String url = SANDBOX_PAYMENT_VERIFY_URL
                    + "?tran_id=" + tranId
                    + "&val_id=" + valId
                    + "&store_id=" + storeId
                    + "&store_passwd=" + storePassword
                    + "&v=1"
                    + "&format=json";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            Map<String, Object> data = response.getBody();

            if (data == null) {
                return false;
            }


            String status = (String) data.get("status");

            return "VALID".equals(status) || "VALIDATED".equals(status);

        }


}
