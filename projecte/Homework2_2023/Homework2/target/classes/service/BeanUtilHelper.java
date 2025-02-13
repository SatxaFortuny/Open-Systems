package deim.urv.cat.homework2.service;

import org.apache.commons.beanutils.BeanUtils;

import deim.urv.cat.homework2.controller.Credentials;
import deim.urv.cat.homework2.controller.CustomerViewBean;
import deim.urv.cat.homework2.controller.CustomerUpdate;
import deim.urv.cat.homework2.model.Customer;
import deim.urv.cat.homework2.model.ViewCredentials;
import java.lang.reflect.InvocationTargetException;

public class BeanUtilHelper {

    public static void copyCredentials(Credentials source, ViewCredentials target) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error al copiar les dades del Bean", e);
        }
    }
    
    public static void copyCustomer(Customer source, CustomerViewBean target) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error al copiar les dades del Bean", e);
        }
    }
    
    public static void copyCustomer2(CustomerViewBean source, Customer target) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error al copiar les dades del Bean", e);
        }
    }
    
    public static void copyCustomer3(CustomerUpdate source, Customer target) {
        try {
            BeanUtils.copyProperties(target, source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error al copiar les dades del Bean", e);
        }
    }
}

