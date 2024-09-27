
package Repository;
import Factory.RepositoryFactory;
public class SingletonEmployeeRepository {
    private static EmployeeRepository instance;

    private SingletonEmployeeRepository() {}

    public static EmployeeRepository getInstance(String type) {
        if (instance == null) {
            instance = RepositoryFactory.getRepository(type);
        }
        return instance;
    }

}
