package com.finance.backend.service;

import com.finance.backend.dto.FinancialRecordRequest;
import com.finance.backend.model.FinancialRecord;
import com.finance.backend.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinancialRecordService {

    private final FinancialRecordRepository recordRepository;

    @Transactional(readOnly = true)
    public Page<FinancialRecord> getAllRecords(Pageable pageable) {
        return recordRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public FinancialRecord getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new com.finance.backend.exception.ResourceNotFoundException("Record not found with id: " + id));
    }

    @Transactional
    public FinancialRecord createRecord(FinancialRecordRequest request) {
        FinancialRecord record = FinancialRecord.builder()
                .amount(request.getAmount())
                .transactionType(request.getTransactionType())
                .category(request.getCategory())
                .transactionDate(request.getTransactionDate())
                .notes(request.getNotes())
                .build();

        return recordRepository.save(record);
    }

    @Transactional
    public FinancialRecord updateRecord(Long id, FinancialRecordRequest request) {
        FinancialRecord record = getRecordById(id);
        
        record.setAmount(request.getAmount());
        record.setTransactionType(request.getTransactionType());
        record.setCategory(request.getCategory());
        record.setTransactionDate(request.getTransactionDate());
        record.setNotes(request.getNotes());
        
        return recordRepository.save(record);
    }

    @Transactional
    public void deleteRecord(Long id) {
        FinancialRecord record = getRecordById(id);
        record.setDeleted(true);
        recordRepository.save(record);
    }
}
