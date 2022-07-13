package com.backbase.productled.communication;

import com.backbase.buildingblocks.commns.listener.CommnsAbstractMessagesStreamListener;
import com.backbase.buildingblocks.commns.service.AbstractPriorityQueueService;
import com.backbase.buildingblocks.commns.service.MessageSenderService;
import com.backbase.buildingblocks.commns.service.TrackingService;
import com.backbase.buildingblocks.multitenancy.TenantProvider;
import com.backbase.outbound.integration.communications.rest.spec.v1.model.BatchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommunicationChannelListener extends CommnsAbstractMessagesStreamListener<BatchResponse> {

    public CommunicationChannelListener(AbstractPriorityQueueService<BatchResponse> priorityQueueService,
                                        TrackingService trackingService,
                                        ObjectMapper objectMapper,
                                        TenantProvider tenantProvider) {
        super(priorityQueueService, trackingService, objectMapper, tenantProvider);
        log.info("Instantiated CommnsMessageListener");
    }
}


