package vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model.Address;

public class AddressDAO {
    private final Jdbi jdbi;

    public AddressDAO() {
        this.jdbi = JDBIConnector.get();
    }

    // Get user's address
    public Address getAddressByUserId(int userId) {
        return jdbi.withHandle(handle ->
            handle.createQuery("SELECT * FROM addresses WHERE user_id=:userId")
                    .bind("userId", userId)
                    .mapToBean(Address.class)
                    .findOne()
                    .orElse(null)
        );
    }
}
