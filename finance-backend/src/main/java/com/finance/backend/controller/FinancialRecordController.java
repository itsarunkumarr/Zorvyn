package com.finance.backend.controller;

import com.finance.backend.dto.FinancialRecordRequest;
import com.finance.backend.model.FinancialRecord;
import com.finance.backend.service.FinancialRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
@Tag(name = "Financial Records", description = "Endpoints for managing financial records")
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get all financial records (paginated)", description = "Accessible by Analyst and Admin. Can use size, page, sort query params.")
    public Page<FinancialRecord> getAllRecords(Pageable pageable) {
        return recordService.getAllRecords(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
    @Operation(summary = "Get a financial record by ID", description = "Accessible by Analyst and Admin")
    public FinancialRecord getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new financial record", description = "Accessible by Admin only")
    public FinancialRecord createRecord(@Valid @RequestBody FinancialRecordRequest request) {
        return recordService.createRecord(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing financial record", description = "Accessible by Admin only")
    public FinancialRecord updateRecord(@PathVariable Long id, @Valid @RequestBody FinancialRecordRequest request) {
        return recordService.updateRecord(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft delete a financial record", description = "Accessible by Admin only")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
