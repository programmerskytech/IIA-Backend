package com.astro.repository.ProcurementModule;

import com.astro.entity.ProcurementModule.IndentCancellationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndentCancellationRequestRepository extends JpaRepository<IndentCancellationRequest, Long> {

    Optional<IndentCancellationRequest> findByIndentIdAndRequestStatus(String indentId, String requestStatus);

    List<IndentCancellationRequest> findByRequestStatus(String requestStatus);

    @Query("SELECT icr FROM IndentCancellationRequest icr WHERE icr.requestStatus = 'PENDING' ORDER BY icr.createdDate ASC")
    List<IndentCancellationRequest> findPendingCancellationRequests();

    Optional<IndentCancellationRequest> findByIndentId(String indentId);
}
