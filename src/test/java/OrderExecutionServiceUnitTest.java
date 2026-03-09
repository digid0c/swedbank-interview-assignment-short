import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderExecutionServiceUnitTest {

  private final OrderExecutionService orderExecutionService = new OrderExecutionService();

  @Test
  public void shouldExecuteOrderForSingleAccount() {
    orderExecutionService.processOrder(1L);

    OrderExecutionService.Customer customer = orderExecutionService.customerRepository.findById(4L);
    assertEquals(10.0, customer.account.balance);
  }
}
