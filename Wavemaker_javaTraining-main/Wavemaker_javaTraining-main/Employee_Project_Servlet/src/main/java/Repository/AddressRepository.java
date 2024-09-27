package Repository;

import Model.Address;

import java.util.List;

public interface AddressRepository {
    void addAddress(Address address);
    Address getAddressById(String id);
    List<Address> getAllAddresses();
    void updateAddress(Address address);
    void deleteAddress(String id);
}