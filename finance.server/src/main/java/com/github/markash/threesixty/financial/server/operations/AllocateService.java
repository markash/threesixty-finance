package com.github.markash.threesixty.financial.server.operations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.github.markash.threesixty.financial.shared.database.DatabaseException;
import com.github.markash.threesixty.financial.shared.operations.AllocateFormData;
import com.github.markash.threesixty.financial.shared.operations.AllocateFormData.AllocationTable.AllocationTableRowData;
import com.github.markash.threesixty.financial.shared.operations.Allocation;
import com.github.markash.threesixty.financial.shared.operations.IAllocateService;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsService;
import com.github.markash.threesixty.financial.shared.operations.ReadAllocatePermission;
import com.github.markash.threesixty.financial.shared.operations.UpdateAllocatePermission;
import com.github.markash.threesixty.financial.shared.text.TextService;

public class AllocateService implements IAllocateService {

    @Override
    public AllocateFormData load(
            final AllocateFormData formData) {
        
        if (!ACCESS.check(new ReadAllocatePermission())) {
            throw new VetoException(TextService.authorizationFailed());
        }
        
        return formData;
    }

    @Override
    public AllocateFormData store(
            final AllocateFormData formData) {
        
        if (!ACCESS.check(new UpdateAllocatePermission())) {
            throw new VetoException(TextService.authorizationFailed());
        }
            
        List<Allocation> allocations = new ArrayList<>();
        
        if (Boolean.FALSE.equals(formData.getSplit().getValue())) {
            
            Allocation allocation = new Allocation ( 
                    formData.getAccount().getValue(),
                    formData.getAmount().getValue(),
                    formData.getComment().getValue());

            allocations.add(allocation);
            
        } else {
        
            BigDecimal allocated = Arrays.stream(formData.getAllocationTable().getRows())
                    .map(AllocationTableRowData::getAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, (i, v) -> i.add(v));
            
            int comparison = allocated.compareTo(formData.getAmount().getValue());
            
            if (comparison < 0) {
                throw new VetoException("The allocated amount is less than the amount.");
            }
            
            if (comparison > 0) {
                throw new VetoException("The allocated amount exceeds the amount"); 
            } 
                            
            Arrays.stream(formData.getAllocationTable().getRows())
                    .map(row -> new Allocation(row.getAccount(), row.getAmount(), row.getComment()))
                    .forEach(allocations::add);
        }
        
        try {
            BEANS.get(IImportTransactionsService.class)
                .manualAllocateTransactionHistory(
                        formData.getTransactionId().getValue(), 
                        allocations);
        } catch (DatabaseException e) {
            throw new VetoException("Unable to allocate the transaction history", e);
        }
        
        return formData;
    }
}
