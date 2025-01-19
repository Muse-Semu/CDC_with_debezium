package com.debzium.debzium_slave.repository;

import com.debzium.debzium_slave.entity.InvoiceAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceAuditRepository extends JpaRepository<InvoiceAudit, Long> {
}
