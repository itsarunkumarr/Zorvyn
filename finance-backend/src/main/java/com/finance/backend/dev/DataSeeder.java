package com.finance.backend.dev;

import com.finance.backend.dto.FinancialRecordRequest;
import com.finance.backend.model.Role;
import com.finance.backend.model.TransactionType;
import com.finance.backend.service.FinancialRecordService;
import com.finance.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserService userService;
    private final FinancialRecordService recordService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Seeding initial data...");

        try {
            userService.createUser("admin", "admin123", Role.ADMIN);
            userService.createUser("analyst", "analyst123", Role.ANALYST);
            userService.createUser("viewer", "viewer123", Role.VIEWER);
            log.info("Users seeded: admin (ADMIN), analyst (ANALYST), viewer (VIEWER)");

            recordService.createRecord(FinancialRecordRequest.builder()
                    .amount(new BigDecimal("5000.00"))
                    .transactionType(TransactionType.INCOME)
                    .category("Salary")
                    .transactionDate(LocalDate.now().minusDays(5))
                    .notes("Monthly salary")
                    .build());

            recordService.createRecord(FinancialRecordRequest.builder()
                    .amount(new BigDecimal("1200.00"))
                    .transactionType(TransactionType.EXPENSE)
                    .category("Rent")
                    .transactionDate(LocalDate.now().minusDays(4))
                    .notes("Apartment rent")
                    .build());

            recordService.createRecord(FinancialRecordRequest.builder()
                    .amount(new BigDecimal("150.00"))
                    .transactionType(TransactionType.EXPENSE)
                    .category("Groceries")
                    .transactionDate(LocalDate.now().minusDays(2))
                    .notes("Supermarket")
                    .build());

            log.info("Financial records seeded.");

        } catch (Exception e) {
            log.warn("Seeding failed or already seeded: " + e.getMessage());
        }
    }
}
