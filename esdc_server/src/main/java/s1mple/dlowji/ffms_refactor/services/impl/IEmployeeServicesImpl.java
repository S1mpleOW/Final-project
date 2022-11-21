package s1mple.dlowji.ffms_refactor.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s1mple.dlowji.ffms_refactor.entities.Customer;
import s1mple.dlowji.ffms_refactor.entities.Employee;
import s1mple.dlowji.ffms_refactor.repositories.EmployeeRepository;
import s1mple.dlowji.ffms_refactor.services.IEmployeeServices;

import java.util.List;
import java.util.Optional;

@Service
public class IEmployeeServicesImpl implements IEmployeeServices {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public double getTotalSalaryEmployeeEachMonth() {
		double totalPrice = 0;
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		totalPrice = employees.stream().mapToDouble(Employee::getSalary).sum();
		return totalPrice;
	}

	@Override
	public Employee deleteById(Long id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if(optionalEmployee.isPresent() && !optionalEmployee.isEmpty()) {
			Employee employee = optionalEmployee.get();
			employee.setDeleted(true);
			employeeRepository.save(employee);
		}
		return optionalEmployee.get();
	}

	@Override
	public Employee findEmployeeById(Long id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if(optionalEmployee.isPresent() && !optionalEmployee.isEmpty()) {
			Employee employee = optionalEmployee.get();
			return employee;
		}
		return null;
	}
}
