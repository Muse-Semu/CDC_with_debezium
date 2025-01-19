package com.debzium.debzium_slave.service;

import com.debzium.debzium_slave.dto.InvoiceAuditDTO;
import com.debzium.debzium_slave.entity.InvoiceAudit;
import com.debzium.debzium_slave.mapper.InvoiceAuditMapper;
import com.debzium.debzium_slave.repository.InvoiceAuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InvoiceAuditService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceAuditService.class);
    private final InvoiceAuditRepository invoiceAuditRepository;
    private final InvoiceAuditMapper invoiceAuditMapper;

    @Autowired
    public InvoiceAuditService(InvoiceAuditRepository invoiceAuditRepository, InvoiceAuditMapper invoiceAuditMapper) {
        this.invoiceAuditRepository = invoiceAuditRepository;
        this.invoiceAuditMapper = invoiceAuditMapper;
    }

    public void saveInvoiceAudit(InvoiceAuditDTO dto) {
        // Convert DTO to Entity
        InvoiceAudit invoiceAudit = new InvoiceAudit();

        invoiceAudit.setSource(dto.getSource());
        invoiceAudit.setBefore(dto.getBefore());
        invoiceAudit.setAfter(dto.getAfter());
        invoiceAudit.setOperation(dto.getOperation());
        invoiceAudit.setTopic(dto.getTopic());
        invoiceAudit.setChangedColumns(dto.getChangedColumns());
        invoiceAudit.setUpdatedDate(dto.getUpdatedDate());


        log.info("Invoice {}", invoiceAudit);
        // Save to database
        invoiceAuditRepository.save(invoiceAudit);
    }
}
