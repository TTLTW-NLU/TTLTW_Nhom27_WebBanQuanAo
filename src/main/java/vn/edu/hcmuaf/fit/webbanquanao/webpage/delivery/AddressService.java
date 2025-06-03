package vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model.Address;

public class AddressService {
    private final AddressDAO addressDAO;

    public AddressService() {
        addressDAO = new AddressDAO();
    }

    // Get user's address
    public Address getAddressByUserId(int userId) {
        return addressDAO.getAddressByUserId(userId);
    }
}
