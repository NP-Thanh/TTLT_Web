package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.web.servieces.TransportService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "UpdateTransportServlet ", value = "/update-transport")
public class UpdateTransportServlet extends HttpServlet {
    private TransportService transportService = new TransportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Đọc JSON body từ request
            String body = request.getReader().lines().collect(Collectors.joining());
            JSONObject json = new JSONObject(body);

            // Đọc các giá trị từ JSON
            String province = json.optString("province", null);
            String district = json.optString("district", null);
            String ward = json.optString("ward", null);
            String detailed = json.optString("detailed", null);
            int orderId = Integer.parseInt(json.getString("orderId"));

            // Gọi hàm update
            transportService.updateFullAddress(orderId, province, district, ward, detailed);

            out.write("{\"success\": true}");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra log server
            out.write("{\"success\": false, \"message\": \"" + e.getMessage().replace("\"", "'") + "\"}");
        }
    }

}
