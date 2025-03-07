//package vn.edu.hcmuaf.fit.demo.controller;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import vn.edu.hcmuaf.fit.demo.dao.UserDao;
//import vn.edu.hcmuaf.fit.demo.dao.model.User;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//@WebServlet("/signup")
//public class SignUpController extends HttpServlet {
//    private UserDao userDao;
//
//    @Override
//    public void init() throws ServletException {
//        userDao = new UserDao(); // Khởi tạo UserDao
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Lấy thông tin từ request
//        String email = request.getParameter("email");
//        String password = request.getParameter("password");
//        String password2 = request.getParameter("password2");
//
//        // Kiểm tra xem mật khẩu có khớp không
//        if (!password.equals(password2)) {
//            request.setAttribute("errorMessage", "Mật khẩu không khớp!");
//            request.getRequestDispatcher("signup.jsp").forward(request, response);
//            return;
//        }
//
//        // Tạo đối tượng User
//        User newUser = new User();
//        // Xóa dòng này vì userID sẽ tự động được sinh ra từ DB
//        // newUser.setUserID(generateUserID()); // Không cần thiết
//
//        newUser.setName(request.getParameter("name")); // Lấy tên từ request
//        newUser.setRoleID(1); // Giả sử vai trò mặc định là 1
//        newUser.setEmail(email);
//        newUser.setPassword(hashPassword(password)); // Mã hóa mật khẩu trước khi lưu
//        newUser.setPhone(request.getParameter("phone")); // Lấy số điện thoại từ request
//        newUser.setRegisDate(LocalDateTime.now()); // Ngày đăng ký hiện tại
//        newUser.setStatus("active"); // Trạng thái mặc định
//
//        // Kiểm tra xem người dùng đã tồn tại chưa
//        if (userDao.findUserByEmail(email) != null) {
//            request.setAttribute("errorMessage", "Email đã tồn tại!");
//            request.getRequestDispatcher("signup.jsp").forward(request, response);
//            return;
//        }
//
//        // Thêm người dùng mới vào DB
//        if (userDao.addUser(newUser)) {
//            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập
//        } else {
//            request.setAttribute("errorMessage", "Đăng ký thất bại, vui lòng thử lại.");
//            request.getRequestDispatcher("signup.jsp").forward(request, response);
//        }
//    }
//    private String hashPassword(String password) {
//        // Logic mã hóa mật khẩu (ví dụ: sử dụng bcrypt hoặc SHA-256)
//        return password; // Thay thế bằng mã hóa thực tế
//    }
//}
package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.servieces.SignUpService;

import java.io.IOException;

@WebServlet("/signup")
public class SignUpController extends HttpServlet {
    private SignUpService signUpService;

    @Override
    public void init() throws ServletException {
        signUpService = new SignUpService(); // Khởi tạo SignUpService
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        // Gọi SignUpService để đăng ký người dùng
        String resultMessage = signUpService.registerUser(
                request.getParameter("name"), email, password, request.getParameter("phone"), password2
        );

        // Xử lý kết quả
        if (resultMessage.equals("Đăng ký thành công!")) {
            response.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập
        } else {
            request.setAttribute("errorMessage", resultMessage);
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }
    }
}