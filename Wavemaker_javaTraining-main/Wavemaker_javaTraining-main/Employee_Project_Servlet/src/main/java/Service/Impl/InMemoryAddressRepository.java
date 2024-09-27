package Service.Impl;

import Model.Address;
import Repository.AddressRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAddressRepository implements AddressRepository {
    private final Map<String, Address> addressMap = new HashMap<>();

    @Override
    public void addAddress(Address address) {
        addressMap.put(address.getLocation(), address);
    }

    @Override
    public Address getAddressById(String id) {
        return addressMap.get(id);
    }

    @Override
    public List<Address> getAllAddresses() {
        return new ArrayList<>(addressMap.values());
    }

    @Override
    public void updateAddress(Address address) {
        addressMap.put(address.getLocation(), address);
    }

    @Override
    public void deleteAddress(String id) {
        addressMap.remove(id);
    }
}