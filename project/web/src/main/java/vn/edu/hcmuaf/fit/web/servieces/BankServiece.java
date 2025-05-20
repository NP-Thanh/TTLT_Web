package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.BankDao;
import vn.edu.hcmuaf.fit.web.model.Bank;

import java.util.List;

public class BankServiece {
    static BankDao bankDao = new BankDao();

    public List<Bank> getBanks() {
        return bankDao.getBanks();
    }

    public Bank getBankById(int id) {
        return bankDao.getBankById(id);
    }

    public void updateBank(int id, String name, String number, String owner, String qr) {
        bankDao.updateBank(id, name, number, owner, qr);
    }
    public void deleteBank(int id) {
        bankDao.deleteBank(id);
    }
    public void addBank(String name, String number, String owner, String qr) {
        bankDao.addBank( name, number, owner, qr);
    }

}
