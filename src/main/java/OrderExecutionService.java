import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * MVP solution that supports order processing considering that every customer has exactly one account. Unfortunately, our
 * business stakeholders are not very satisfied with this solution and require us to implement support for multi-account
 * environment...
 */
public class OrderExecutionService {

  OrderRepository orderRepository;
  CustomerRepository customerRepository;

  public OrderExecutionService() {
    orderRepository = new OrderRepository();
    customerRepository = new CustomerRepository();
  }

  /**
   * Main function that is responsible for processing orders
   * @param orderId order to process
   */
  public void processOrder(Long orderId) {
    Order order = orderRepository.findById(orderId);
    Customer customer = customerRepository.findById(order.customerId);

    Account account = customer.account;
    account.balance = account.balance - order.amount;

    order.execute();
    customerRepository.save(customer);
  }

  class OrderRepository {
    private final Map<Long, Order> orders =  new HashMap<>();

    private OrderRepository() {
      orders.put(1L, new Order(1L, 4L, 10.0));
      orders.put(2L, new Order(2L, 5L, 30.0));
      orders.put(3L, new Order(3L, 6L, 50.0));
    }

    Order findById(Long id) {
      return orders.get(id);
    }

    void save(Order order) {
      orders.put(order.id, order);
    }
  }

  class CustomerRepository {
    private final Map<Long, Customer> customers =  new HashMap<>();

    private CustomerRepository() {
      customers.put(4L, new Customer(4L, "John Doe", new Account(7L, "Main", 20.0)));
      customers.put(5L, new Customer(5L, "Joe Doe", new Account(8L, "Secondary", 10.0)));
      customers.put(6L, new Customer(6L, "Mary Doe", new Account(9L, "Tertiary", 25.0)));
    }

    Customer findById(Long id) {
      return customers.get(id);
    }

    void save(Customer customer) {
      customers.put(customer.id, customer);
    }
  }

  @AllArgsConstructor
  class Order {
    Long id;
    Long customerId;
    double amount;

    void execute() {
      System.out.println("Executing Order #" + id);
    }
  }

  @AllArgsConstructor
  class Customer {
    Long id;
    String name;
    Account account;
  }

  @AllArgsConstructor
  class Account {
    Long id;
    String description;
    double balance;
  }
}
