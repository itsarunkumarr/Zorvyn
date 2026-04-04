package com.finance.backend.repository;

import com.finance.backend.model.FinancialRecord;
import com.finance.backend.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.transactionType = :type")
    BigDecimal sumAmountByType(@Param("type") TransactionType type);
    
    @Query("SELECT f.category, COALESCE(SUM(f.amount), 0) FROM FinancialRecord f WHERE f.transactionType = :type GROUP BY f.category")
    List<Object[]> sumAmountByCategoryAndType(@Param("type") TransactionType type);
    
    List<FinancialRecord> findTop5ByOrderByTransactionDateDescCreatedAtDesc();
}
