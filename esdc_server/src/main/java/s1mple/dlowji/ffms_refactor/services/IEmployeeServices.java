package s1mple.dlowji.ffms_refactor.services;
import s1mple.dlowji.ffms_refactor.entities.Employee;

public interface IEmployeeServices {
	double getTotalSalaryEmployeeEachMonth();

	Employee deleteById(Long id);

	Employee findEmployeeById(Long id);
}
