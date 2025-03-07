package vn.edu.hcmuaf.fit.web.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.Discount;
import vn.edu.hcmuaf.fit.web.model.ProductType;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.DiscountService;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Show", value = "/Cart")
public class Show extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        DiscountService discountServiece = new DiscountService();
        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
        }
        List<ProductType> types=productServiece.getAllProductTypes();
        request.setAttribute("productTypes", types);
        String did = request.getParameter("did");
        if (did != null && !did.isEmpty()){
            int discount_id= Integer.parseInt(did);
            if (discountServiece.checkDiscount(discount_id)){
                Discount discount= discountServiece.getDiscountById(discount_id);
                request.setAttribute("discount", discount);
            }
        }
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
