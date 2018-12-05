package br.com.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springdemo.entity.Customer;
import br.com.springdemo.exception.CustomerNotFoundException;
import br.com.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// injecting customer service;
	@Autowired
	private CustomerService customerService;

	/**
	 * Finding all customers (NO FILTER)
	 * 
	 * @return
	 */
	@GetMapping("/customers")
	public List<Customer> findCustomers() {
		// Creating list of customers;
		List<Customer> customers = new ArrayList<>();

		// Get list os customers from database;
		customers = customerService.getCustomers();

		return customers;

	}

	/**
	 * Find customer by ID
	 * 
	 * @param customerId
	 * @return
	 */
	@GetMapping("/customers/{customerId}")
	public Customer findCustomerById(@PathVariable int customerId) {

		Customer customer = customerService.getCustomer(customerId);

		if (customer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
		return customer;
	}

	/**
	 * Saving a new customer
	 * 
	 * @param customer
	 * @return
	 */
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {

		customer.setId(0);

		customerService.saveCustomer(customer);

		return customer;
	}

	/**
	 * Updating a customer
	 * 
	 * @param customer
	 * @return
	 */
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {

		Customer customer = customerService.getCustomer(theCustomer.getId());

		if (customer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + theCustomer.getId());
		}

		customerService.saveCustomer(customer);

		return customer;
	}

	/**
	 * Deleting a customer
	 * 
	 * @param customerId
	 * @return
	 */
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {

		Customer customer = customerService.getCustomer(customerId);

		if (customer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}

		customerService.deleteCustomer(customerId);

		return "Deleted customer id - " + customerId;
	}
}
