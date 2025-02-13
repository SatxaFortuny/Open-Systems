/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package deim.urv.cat.homework2.service;
import deim.urv.cat.homework2.model.CustomerDTO;
import deim.urv.cat.homework2.model.Customer;
import deim.urv.cat.homework2.model.ViewCredentials;
import java.util.List;

/**
 *
 * @author miriam
 */
public interface CustomerService {
    public List<CustomerDTO> getAllCustomers();
    public CustomerDTO getCustomerById(Long id);
    public void updateCustomer(Long id, ViewCredentials credentials, Customer updatedData);
    public Customer authenticateCustomer(ViewCredentials credentials);
    public boolean registerCustomer(Customer customer);
}
