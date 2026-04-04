package com.finance.backend.service;

import com.finance.backend.dto.DashboardSummary;
import com.finance.backend.model.TransactionType;
import com.finance.backend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final FinancialRecordRepository recordRepository;

    @Transactional(readOnly = true)
    public DashboardSummary getDashboardSummary() {
        BigDecimal totalIncome = recordRepository.sumAmountByType(TransactionType.INCOME);
        BigDecimal totalExpenses = recordRepository.sumAmountByType(TransactionType.EXPENSE);

        List<Object[]> incomeData = recordRepository.sumAmountByCategoryAndType(TransactionType.INCOME);
        Map<String, BigDecimal> incomeByCategory = incomeData.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (BigDecimal) obj[1]
                ));

        List<Object[]> expenseData = recordRepository.sumAmountByCategoryAndType(TransactionType.EXPENSE);
        Map<String, BigDecimal> expensesByCategory = expenseData.stream()
                .collect(Collectors.toMap(
                        obj -> (String) obj[0],
                        obj -> (BigDecimal) obj[1]
                ));

        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        return DashboardSummary.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .netBalance(netBalance)
                .incomeByCategory(incomeByCategory)
                .expensesByCategory(expensesByCategory)
                .build();
    }
}
