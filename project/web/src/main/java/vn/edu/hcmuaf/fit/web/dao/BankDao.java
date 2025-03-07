package vn.edu.hcmuaf.fit.web.dao;

import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Bank;
import java.util.List;

public class BankDao {
    public List<Bank> getBanks() {
        List<Bank> banks = (List<Bank>) JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("select * from banks")
                    .mapToBean(Bank.class)
                    .list();
        });
        return banks;
    }

    public Bank getBankById(int id) {
        Bank bank = (Bank) JDBIConnector.getJdbi().withHandle(h -> {
            return h.createQuery("select * from banks where id= :id")
                    .bind("id", id)
                    .mapToBean(Bank.class)
                    .findOne().orElse(null);
        });
        return bank;
    }
}
