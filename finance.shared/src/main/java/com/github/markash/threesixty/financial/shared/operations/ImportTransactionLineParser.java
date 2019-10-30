package com.github.markash.threesixty.financial.shared.operations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.simpleflatmapper.csv.CsvMapper;
import org.simpleflatmapper.csv.CsvMapperFactory;
import org.simpleflatmapper.csv.CsvParser;

public class ImportTransactionLineParser implements Function<BinaryResource, List<ImportTransactionLine>> {

    public List<ImportTransactionLine> apply(
            final BinaryResource resource) {
        
        if (resource == null) {
            throw new ProcessingException("The import transaction line binary resource to parse is null");
        }
        
        List<ImportTransactionLine> rows = new ArrayList<>();
        
        try {
            
            final String content = resource.getContentAsString();
            
            if (content != null) {
                       
                CsvMapper<ImportTransactionLine> mapper = 
                        CsvMapperFactory.newInstance()
                            .defaultDateFormat("yyyyMMdd")
                            .newBuilder(ImportTransactionLine.class)
                            .addMapping("Date")
                            .addMapping("Description")
                            .addMapping("Amount")
                            .addMapping("Balance")
                            .mapper();
        
                CsvParser.skip(1).mapWith(mapper).forEach(content, rows::add);
            }
        
            return rows;
            
        } catch (IOException e) {
            
            throw new ProcessingException("Unable to parse import transaction lines from binary resource", e);
        }
    }
}
