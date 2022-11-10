package com.backbase.communication.channel;

import com.backbase.communication.model.Sendable;
import com.backbase.communication.service.EmailStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommunicationChannelConsumer implements Consumer<Sendable> {
    private final EmailStrategyFactory emailStrategyFactory;

    @Override
    public void accept(Sendable sendable) {
        log.debug("Email payload: '{}'", sendable.toString());
        emailStrategyFactory.findEmailStrategy(sendable.getVersion()).handleEmail(sendable);
    }
}
