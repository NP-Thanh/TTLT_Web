package vn.edu.hcmuaf.fit.web.servieces;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.web.dao.DiscountDao;
import vn.edu.hcmuaf.fit.web.model.Discount;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class DiscountService {
    static DiscountDao discountDAO = new DiscountDao();

    public DiscountService() {
        this.discountDAO = new DiscountDao();
    }

    public List<Discount> getAllDiscounts() {
        try {
            List<Discount> discounts = discountDAO.getAllDiscounts();
            System.out.println("\nAll discounts:");
            discounts.forEach(System.out::println);
            return discounts;
        } catch (Exception e) {
            System.err.println("Error getting all discounts: " + e.getMessage());
            return null;
        }
    }

    public Discount getDiscountById(int id) {
        return discountDAO.getDiscountById(id);
    }

    public void deleteDiscount(int id) {
        try {
            boolean deleted = discountDAO.deleteDiscount(id);
            if (deleted) {
                System.out.println("\nSuccessfully deleted discount with ID: " + id);
            } else {
                System.out.println("\nFailed to delete discount with ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error deleting discount: " + e.getMessage());
        }
    }

    public Discount updateDiscount(Discount discount) {
        try {
            Discount updatedDiscount = discountDAO.updateDiscount(discount);
            System.out.println("\nUpdated discount: " + updatedDiscount);
            return updatedDiscount;
        } catch (Exception e) {
            System.err.println("Error updating discount: " + e.getMessage());
            return null;
        }
    }

    public Discount addDiscount(Discount discount) {
        try {
            Discount addedDiscount = discountDAO.addDiscount(discount);
            System.out.println("\nAdded discount: " + addedDiscount);
            return addedDiscount;
        } catch (Exception e) {
            System.err.println("Error adding discount: " + e.getMessage());
            return null;
        }
    }

    public boolean checkDiscount(int id){
        Discount discount = getDiscountById(id);
        LocalDate today = LocalDate.now();
        if(discount != null && discount.getQuantity() > 0 && discount.getExpDate().isAfter(today)){
            return true;
        }
        return false;
    }
}
