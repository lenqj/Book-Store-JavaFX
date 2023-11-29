package controller.Customer;

import launcher.ComponentFactory;
import view.Customer.CustomerView;

public class CustomerController {
        private final CustomerView customerView;

        public CustomerController(CustomerView customerView) {
            this.customerView = customerView;
            customerView.showPane(ComponentFactory.getCustomerBooksView().getPane());
        }

        public CustomerView getCustomerView() {
            return customerView;
        }
}
