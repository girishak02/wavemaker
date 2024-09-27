package Service.Impl;

import Model.Address;
import Repository.AddressRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileAddressRepository implements AddressRepository {
    private static final String FILE_PATH = "addresses.txt";

    @Override
    public void addAddress(Address address) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(address.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Address getAddressById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Address address = parseAddress(line);
                if (address.getLocation().equals(id)) {
                    return address;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addresses.add(parseAddress(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    @Override
    public void updateAddress(Address address) {
        List<Address> addresses = getAllAddresses();
        addresses.removeIf(a -> a.getLocation().equals(address.getLocation()));
        addresses.add(address);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Address a : addresses) {
                writer.write(a.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddress(String id) {
        List<Address> addresses = getAllAddresses();
        addresses.removeIf(address -> address.getLocation().equals(id));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Address address : addresses) {
                writer.write(address.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Address parseAddress(String line) {
        // Parse address from line

        String[] parts = line.split(",");
        String location = parts[0];
        Integer pin = Integer.valueOf(parts[1]);

        return new Address(location, pin);

    }
}