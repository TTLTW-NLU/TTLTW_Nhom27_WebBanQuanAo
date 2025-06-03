package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.AOrderDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrder;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrderItem;

import java.time.LocalDateTime;
import java.util.Map;

public class AOrderService {
    AOrderDao aOrderDao;

    public AOrderService() {
        aOrderDao = new AOrderDao();
    }

    public Map<Integer, AOrder> showOrders() {
        return aOrderDao.listOrders;
    }

    public boolean updateOrder(Object obj, Integer id) {
        if (!aOrderDao.listOrders.containsKey(id)) return false;
        return aOrderDao.update(obj, id);
    }

    public boolean deleteOrder(Integer id) {
        if (!aOrderDao.listOrders.containsKey(id)) return false;
        AOrder order = aOrderDao.listOrders.get(id);
        if (order.getStatus() == 5) return false;
        // xóa ở db
        boolean isDeletedInDB = aOrderDao.delete(id, 5);
        // nếu xóa ở db rồi thì xóa ở cache (listOrders)
        if (isDeletedInDB) order.setStatus(5);
        return isDeletedInDB;
    }

    public Map<Integer, AOrderItem> showOrderItem(Integer orderId) {
        if (!aOrderDao.listOrders.containsKey(orderId)) return null;
        return aOrderDao.getAllOrderItems(orderId);
    }

    public boolean updateOrderItem(AOrderItem orderItem, Integer id, Integer orderId) {
        if (!aOrderDao.listOrders.containsKey(orderId)) return false;
        return aOrderDao.updateOrderDetails(orderItem, id, orderId);
    }

    public static void main(String[] args) {
        AOrderService a = new AOrderService();

        AOrder order = new AOrder(7, "Luật", 1, "bcd", LocalDateTime.parse("2025-01-14T17:00"), 250000.0, 4);

        System.out.println(a.updateOrder(order, 7));

    }

}
