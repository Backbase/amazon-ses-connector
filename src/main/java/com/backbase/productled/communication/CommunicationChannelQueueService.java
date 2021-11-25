package com.backbase.productled.communication;

import com.backbase.buildingblocks.commns.service.AbstractPriorityQueueService;
import com.backbase.buildingblocks.multitenancy.TenantProvider;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommunicationChannelQueueService extends AbstractPriorityQueueService<BatchResponse> {

    public CommunicationChannelQueueService(
            @Value("${backbase.email.worker-count}") int numberOfWorkers,
            CommunicationChannelConsumer communicationChannelConsumer,
            TenantProvider tenantProvider
    ) {
        super(numberOfWorkers, communicationChannelConsumer, tenantProvider);
    }
}
