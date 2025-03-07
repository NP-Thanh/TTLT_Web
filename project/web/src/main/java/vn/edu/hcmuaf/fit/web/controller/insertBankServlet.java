package vn.edu.hcmuaf.fit.web.controller; import jakarta.servlet.*; 
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.ProductDao;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/insertBankServlet")
public class insertBankServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDao dao = new ProductDao();
        int id = Integer.parseInt(request.getParameter("bankId"));
        String name = request.getParameter("bankName");
        String number = request.getParameter("bankNumber");
        String owner = request.getParameter("bankOwner");
        String qr = request.getParameter("bankQr");
        System.out.println("id :"+id);
        System.out.println("name :"+name);
        System.out.println("number :"+number);
        System.out.println("owner :"+owner);
        System.out.println("qr :"+qr);

        dao.insertBank(id,name,number,owner,qr);
        response.sendRedirect("bank");
    }
}

