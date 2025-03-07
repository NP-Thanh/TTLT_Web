package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.CommentDao;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.Comment;
import vn.edu.hcmuaf.fit.web.model.CommentByUser;
import vn.edu.hcmuaf.fit.web.model.User;

import java.util.ArrayList;
import java.util.List;

public class CommentServiece {
    static CommentDao commentDao = new CommentDao();
    static UserDao userDao = new UserDao();

    public List<Comment> getAllByPID(int pid) {
        return commentDao.getAllByPID(pid);
    }

    public double getAverageRatingByPID(int pid) {
        return commentDao.getAverageRatingByPID(pid);
    }

    public List<CommentByUser> get2CommentByUser(int pid) {
        // Lấy danh sách 2 comment từ CommentDao
        List<Comment> comments = commentDao.get2CommentsByProductId(pid);

        // Danh sách để chứa các đối tượng CommentByUser
        List<CommentByUser> commentByUsers = new ArrayList<>();

        // Duyệt qua từng comment để lấy thông tin User tương ứng
        for (Comment comment : comments) {
            // Giả sử bạn có một UserDao để lấy thông tin User theo user_id
            User user = userDao.getUserById(comment.getUser_id());

            // Tạo đối tượng CommentByUser từ User và Comment
            CommentByUser commentByUser = new CommentByUser(comment, user);

            // Thêm vào danh sách
            commentByUsers.add(commentByUser);
        }

        return commentByUsers;
    }

    public List<CommentByUser> getCommentByUser(int pid) {
        // Lấy danh sách comment từ CommentDao
        List<Comment> comments = commentDao.getAllByPID(pid);

        // Danh sách để chứa các đối tượng CommentByUser
        List<CommentByUser> commentByUsers = new ArrayList<>();

        // Duyệt qua từng comment để lấy thông tin User tương ứng
        for (Comment comment : comments) {
            // Giả sử bạn có một UserDao để lấy thông tin User theo user_id
            User user = userDao.getUserById(comment.getUser_id());

            // Tạo đối tượng CommentByUser từ User và Comment
            CommentByUser commentByUser = new CommentByUser(comment, user);

            // Thêm vào danh sách
            commentByUsers.add(commentByUser);
        }

        return commentByUsers;
    }

    public List<CommentByUser> getFilteredComments(int productId, String ratingFilter, String sortOrder) {
        int rating = ratingFilter != null && !ratingFilter.equals("all") ? Integer.parseInt(ratingFilter) : -1;
        boolean isNewestFirst = sortOrder == null || sortOrder.equals("newest");

        List<Comment> comments = commentDao.getFilteredComments(productId, rating, isNewestFirst);
        // Danh sách để chứa các đối tượng CommentByUser
        List<CommentByUser> commentByUsers = new ArrayList<>();
        for (Comment comment : comments) {
            User user = userDao.getUserById(comment.getUser_id());

            CommentByUser commentByUser = new CommentByUser(comment, user);

            commentByUsers.add(commentByUser);
        }

        return commentByUsers;
    }

    public Comment getCommentByOrder(int oid, int pid){
        return commentDao.getCommentByOrder(oid, pid);
    }

    public boolean addComment(int user_id, int product_id, int order_id, int num_rate, String comment) {
        return commentDao.addComment(user_id, product_id, order_id, num_rate, comment);
    }

}
