package vn.edu.hcmuaf.fit.web.servieces;

import vn.edu.hcmuaf.fit.web.dao.StorageDao;
import vn.edu.hcmuaf.fit.web.model.Storage;

import java.util.List;

public class StorageServiece {
    static StorageDao storageDao = new StorageDao();

    public List<Storage> getAvailableKeys(int pid, int quantity) {
        return storageDao.getAvailableKeys(pid, quantity);
    }

    public void updateKeyStatus(int keyId, int oid, String status) {
        storageDao.updateKeyStatus(keyId, oid, status);
    }
}
