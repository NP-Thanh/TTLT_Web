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
    public void updateBank(int id, String name, String number, String owner, String qr){
        JDBIConnector.getJdbi().useHandle(handle -> {
            handle.createUpdate("UPDATE banks SET name = :name, number = :number, owner = :owner, qr = :qr  WHERE id = :id")
                    .bind("id", id)
                    .bind("name", name)
                    .bind("number", number)
                    .bind("owner", owner)
                    .bind("qr", qr)
                    .execute();
        });
    }

    public void deleteBank(int bankId) {
        JDBIConnector.getJdbi().useHandle(handle ->
                handle.createUpdate("DELETE FROM banks WHERE id = :id")
                        .bind("id", bankId)
                        .execute()
        );
    }

    public void addBank(String name, String number, String owner, String qr){
        JDBIConnector.getJdbi().useHandle(handle -> {
            handle.createUpdate("INSERT INTO banks (name,number,owner, qr) VALUES (:name, :number, :owner, :qr)")
                    .bind("name", name)
                    .bind("number", number)
                    .bind("owner", owner)
                    .bind("qr", qr)
                    .execute();
        });
    }
}
