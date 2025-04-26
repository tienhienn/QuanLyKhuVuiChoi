package Controller;

import Model.Khu;
import DAO.KhuDAO;
import java.util.ArrayList;

public class KhuController {
    private KhuDAO khuDAO;

    public KhuController(KhuDAO khuDAO) {
        this.khuDAO = khuDAO;
    }

    public ArrayList<Khu> getAllKhu() {
        return khuDAO.getAllKhu();
    }

    public boolean themKhu(Khu khu) {
        return khuDAO.insertKhu(khu);
    }

    public boolean suaKhu(Khu khu) {
        return khuDAO.updateKhu(khu);
    }

    public boolean xoaKhu(String maKhu) {
        return khuDAO.deleteKhu(maKhu);
    }
}

