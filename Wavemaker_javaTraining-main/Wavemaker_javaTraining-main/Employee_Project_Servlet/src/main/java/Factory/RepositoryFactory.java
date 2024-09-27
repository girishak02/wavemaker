package Factory;

import Repository.*;
import Service.Impl.*;

public class RepositoryFactory {

    public static EmployeeRepository getEmployeeRepository(String type) {
        if (type.equalsIgnoreCase("memory")) {
            return new InMemoryEmployeeRepository();
        } else if (type.equalsIgnoreCase("file")) {
            return new FileEmployeeRepository();
        } else if (type.equalsIgnoreCase("DataBase")) {
            return new DataBaseEmployeeRepository();
        }
        throw new IllegalArgumentException("Invalid repository type");
    }

    public static AddressRepository getAddressRepository(String type) {
        if (type.equalsIgnoreCase("memory")) {
            return new InMemoryAddressRepository();
        } else if (type.equalsIgnoreCase("file")) {
            return new FileAddressRepository();
        }
        throw new IllegalArgumentException("Invalid repository type");
    }

    public static EmployeeRepository getRepository(String type) {
        return null;
    }

//    public static EmployeeRepository getRepository(String type) {
//        return null;
//    }
}
