package com.shumisoft.employee_management_system.seeder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shumisoft.employee_management_system.entity.Employee;
import com.shumisoft.employee_management_system.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeSeeder implements ApplicationRunner {

	private final EmployeeRepository repository;

	private static final Object[][] EMPLOYEE_DATA = {
			{ "John", "Doe", "john.doe@example.com", "555-1234", LocalDate.of(2020, 1, 15),
					Employee.Status.ACTIVE },
			{ "Jane", "Smith", "jane.smith@example.com", "555-5678", LocalDate.of(2019, 3, 22),
					Employee.Status.ACTIVE },
			{ "Alice", "Johnson", "alice.johnson@example.com", "555-8765", LocalDate.of(2021, 7, 10),
					Employee.Status.INACTIVE },
			{ "Bob", "Brown", "bob.brown@example.com", "555-4321", LocalDate.of(2022, 9, 5),
					Employee.Status.ACTIVE },
			{ "Charlie", "Davis", "charlie.davis@example.com", "555-9988", LocalDate.of(2020, 5, 30),
					Employee.Status.INACTIVE },
			{ "David", "Miller", "david.miller@example.com", "555-2233", LocalDate.of(2018, 8, 14),
					Employee.Status.ACTIVE },
			{ "Eva", "Wilson", "eva.wilson@example.com", "555-6655", LocalDate.of(2023, 2, 19),
					Employee.Status.INACTIVE },
			{ "Frank", "Moore", "frank.moore@example.com", "555-4455", LocalDate.of(2021, 11, 11),
					Employee.Status.ACTIVE },
			{ "Grace", "Taylor", "grace.taylor@example.com", "555-1122", LocalDate.of(2020, 4, 12),
					Employee.Status.ACTIVE },
			{ "Henry", "Anderson", "henry.anderson@example.com", "555-3344", LocalDate.of(2019, 6, 27),
					Employee.Status.INACTIVE },
			{ "Isla", "Martinez", "isla.martinez@example.com", "555-8888", LocalDate.of(2021, 3, 10),
					Employee.Status.ACTIVE },
			{ "James", "Garcia", "james.garcia@example.com", "555-7777", LocalDate.of(2019, 10, 1),
					Employee.Status.ACTIVE },
			{ "Lily", "Rodriguez", "lily.rodriguez@example.com", "555-1230", LocalDate.of(2022, 6, 15),
					Employee.Status.INACTIVE },
			{ "Mason", "Martinez", "mason.martinez@example.com", "555-9999", LocalDate.of(2021, 4, 21),
					Employee.Status.ACTIVE },
			{ "Nina", "Hernandez", "nina.hernandez@example.com", "555-7770", LocalDate.of(2020, 12, 18),
					Employee.Status.ACTIVE },
			{ "Olivia", "Lopez", "olivia.lopez@example.com", "555-1010", LocalDate.of(2023, 1, 20),
					Employee.Status.ACTIVE },
			{ "Peter", "Perez", "peter.perez@example.com", "555-2020", LocalDate.of(2022, 8, 11),
					Employee.Status.INACTIVE },
			{ "Quinn", "Ramirez", "quinn.ramirez@example.com", "555-3030", LocalDate.of(2021, 5, 5),
					Employee.Status.ACTIVE },
			{ "Rachel", "Foster", "rachel.foster@example.com", "555-4040", LocalDate.of(2020, 10, 25),
					Employee.Status.ACTIVE },
			{ "Sam", "Washington", "sam.washington@example.com", "555-5050", LocalDate.of(2022, 3, 17),
					Employee.Status.ACTIVE },
			{ "Tina", "Cooper", "tina.cooper@example.com", "555-6060", LocalDate.of(2019, 1, 30),
					Employee.Status.INACTIVE },
			{ "Ulysses", "Morgan", "ulysses.morgan@example.com", "555-7070", LocalDate.of(2023, 4, 9),
					Employee.Status.ACTIVE },
			{ "Victoria", "Bell", "victoria.bell@example.com", "555-8080", LocalDate.of(2021, 9, 2),
					Employee.Status.ACTIVE },
			{ "Walter", "Brooks", "walter.brooks@example.com", "555-9090", LocalDate.of(2020, 7, 21),
					Employee.Status.ACTIVE },
			{ "Xena", "Kelly", "xena.kelly@example.com", "555-1111", LocalDate.of(2022, 11, 14),
					Employee.Status.INACTIVE },
			{ "Yara", "Ross", "yara.ross@example.com", "555-2222", LocalDate.of(2018, 5, 1),
					Employee.Status.ACTIVE },
			{ "Zane", "Barnes", "zane.barnes@example.com", "555-3333", LocalDate.of(2023, 6, 28),
					Employee.Status.INACTIVE },
			{ "Alex", "Stewart", "alex.stewart@example.com", "555-4444", LocalDate.of(2021, 2, 18),
					Employee.Status.ACTIVE },
			{ "Bethany", "Price", "bethany.price@example.com", "555-5555", LocalDate.of(2020, 8, 7),
					Employee.Status.ACTIVE },
			{ "Caleb", "White", "caleb.white@example.com", "555-6666", LocalDate.of(2022, 1, 3),
					Employee.Status.ACTIVE },
			{ "Diana", "King", "diana.king@example.com", "555-7777", LocalDate.of(2019, 11, 22),
					Employee.Status.INACTIVE },
			{ "Ethan", "Green", "ethan.green@example.com", "555-8888", LocalDate.of(2023, 3, 14),
					Employee.Status.ACTIVE },
			{ "Fiona", "Hall", "fiona.hall@example.com", "555-9990", LocalDate.of(2020, 6, 1),
					Employee.Status.ACTIVE },
			{ "Gabriel", "Baker", "gabriel.baker@example.com", "555-1000", LocalDate.of(2021, 10, 29),
					Employee.Status.INACTIVE },
			{ "Hannah", "Wright", "hannah.wright@example.com", "555-2000", LocalDate.of(2022, 5, 20),
					Employee.Status.ACTIVE },
	};

	private static final Map<String, List<String>> MANAGER_MAP = Map.of(
			"john.doe@example.com",
			List.of("alice.johnson@example.com", "bob.brown@example.com", "charlie.davis@example.com"),
			"jane.smith@example.com",
			List.of("eva.wilson@example.com", "frank.moore@example.com", "grace.taylor@example.com",
					"henry.anderson@example.com"),
			"david.miller@example.com",
			List.of("isla.martinez@example.com", "james.garcia@example.com", "lily.rodriguez@example.com",
					"mason.martinez@example.com", "nina.hernandez@example.com"),
			"alice.johnson@example.com", List.of("olivia.lopez@example.com", "peter.perez@example.com"),
			"frank.moore@example.com", List.of("quinn.ramirez@example.com", "rachel.foster@example.com"),
			"isla.martinez@example.com", List.of("sam.washington@example.com", "tina.cooper@example.com"));

	@Override
	public void run(ApplicationArguments args) throws Exception {

		log.info("[Seeder] EmployeeSeeder started!");

		if (repository.count() == 0) {

			log.info("[Seeder] No employees found. Seeding sample employees...");

			seedSampleEmployees();

		} else {

			log.info("[Seeder] Employees already exist. Skipping sample employee seeding.");

		}

	}

	private void seedSampleEmployees() {

		List<Employee> saved = repository.saveAll(buildEmployees());

		log.info("[Seeder] Seeded {} employees", saved.size());

		assignManagers(saved);
		log.info("[Seeder] Manager assignments applied!");

	}

	private List<Employee> buildEmployees() {

		return java.util.Arrays.stream(EMPLOYEE_DATA)
				.map(row -> Employee.builder()
						.firstName((String) row[0])
						.lastName((String) row[1])
						.email((String) row[2])
						.phone((String) row[3])
						.joinDate((LocalDate) row[4])
						.status((Employee.Status) row[5])
						.build())
				.toList();

	}

	private void assignManagers(List<Employee> employees) {
		Map<String, Employee> byEmail = new java.util.HashMap<>();
		for (Employee emp : employees)
			byEmail.put(emp.getEmail(), emp);

		MANAGER_MAP.forEach((managerEmail, reportEmails) -> {
			Employee manager = byEmail.get(managerEmail);
			reportEmails.forEach(reportEmail -> {
				Employee report = byEmail.get(reportEmail);
				if (manager != null && report != null) {
					report.setManager(manager);
				}
			});
		});

		repository.saveAll(employees);
	}

}
