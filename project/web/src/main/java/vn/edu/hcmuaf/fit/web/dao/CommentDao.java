package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Comment;
import vn.edu.hcmuaf.fit.web.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    public List<Comment> getAllByPID(int pid) {
        List<Comment> comments= JDBIConnector.getJdbi().withHandle(h->{
            return h.createQuery("select * from comments where product_id= :pid").bind("pid", pid).mapToBean(Comment.class).list();
        });
        return comments;
    }

    public double getAverageRatingByPID(int pid) {
        return JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("select coalesce(avg(num_rate), 0) from comments where product_id = :pid")
                    .bind("pid", pid)
                    .mapTo(Double.class)
                    .one();
        });
    }

    public List<Comment> get2CommentsByProductId(int pid) {
        return JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery(
                            "select * from comments where product_id = :pid " +
                                    "order by num_rate desc, date desc limit 2")
                    .bind("pid", pid)
                    .mapToBean(Comment.class)
                    .list();
        });
    }

    public List<Comment> getFilteredComments(int pid, int rating, boolean isNewestFirst) {
        List<Comment> commentByUsers = new ArrayList<>();
        String sql = "SELECT *" +
                " FROM comments"+
                " WHERE product_id = :pid";

        if (rating > 0) {
            sql += " AND num_rate = :rating";
        }

        sql += " ORDER BY date " + (isNewestFirst ? "DESC" : "ASC");
        String finalSql = sql;
        return JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery(finalSql)
                    .bind("pid", pid)
                    .bind("rating", rating)
                    .mapToBean(Comment.class)
                    .list();
        });
    }

    public Comment getCommentByOrder(int oid, int pid){
        Comment comment= (Comment) JDBIConnector.getJdbi().withHandle(h->{
            return h.createQuery("select * from comments where order_id= :oid and product_id= :pid")
                    .bind("oid", oid)
                    .bind("pid", pid)
                    .mapToBean(Comment.class)
                    .findOne()
                    .orElse(null);
        });
        return comment;
    }

    public boolean addComment(int user_id, int product_id, int order_id, int num_rate, String comment) {
        int rowsAffected = JDBIConnector.getJdbi().withHandle(handle ->
                handle.createUpdate("INSERT INTO comments (user_id, product_id, order_id, num_rate, comment) VALUES (?, ?, ?, ?, ?)")
                        .bind(0, user_id)
                        .bind(1, product_id)
                        .bind(2, order_id)
                        .bind(3, num_rate)
                        .bind(4, comment)
                        .execute()
        );
        return rowsAffected > 0;
    }


}
