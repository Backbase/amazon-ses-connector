package com.backbase.communication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.backbase.buildingblocks.testutils.TestTokenUtil;
import com.backbase.communication.config.DefaultMailSenderProperties;
import com.backbase.communication.reader.EmailReader;
import java.io.File;
import java.util.List;
import java.util.UUID;
import javax.mail.Message;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers
@ActiveProfiles({"default", "it"})
@SpringBootTest(classes = {SesEmailConnectorApplication.class})
class SendEmailIT {

    private final RestTemplate template = new RestTemplate();

    @Autowired
    private EmailReader emailReader;

    @Autowired
    private DefaultMailSenderProperties properties;

    @Container
    public static DockerComposeContainer environment = new DockerComposeContainer(
        new File("src/test/resources/docker-compose.yml"))
        .withLogConsumer("message-broker", new Slf4jLogConsumer(log))
        .withLogConsumer("greenmail", new Slf4jLogConsumer(log))
        .withLogConsumer("communication", new Slf4jLogConsumer(log))
        .withExposedService("message-broker", 61616, Wait.forListeningPort())
        .withExposedService("communication", 8080, Wait.forHttp("/actuator/health")
            .forStatusCode(200))
        .withExposedService("greenmail", 3025, Wait.forListeningPort())
        .withExposedService("greenmail", 3110, Wait.forListeningPort())
        .withLocalCompose(true);

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        System.setProperty("SIG_SECRET_KEY", "JWTSecretKeyDontUseInProduction!");
        System.setProperty("TESTCONTAINERS_RYUK_DISABLED", "true");

        registry.add("spring.activemq.broker-url", () -> "tcp://%s:%s"
            .formatted(environment.getServiceHost("message-broker", 61616),
                environment.getServicePort("message-broker", 61616)));
        registry.add("mail.pop3.host", () -> environment.getServiceHost("greenmail", 3110));
        registry.add("spring.mail.host", () -> environment.getServiceHost("greenmail", 3025));
        registry.add("mail.pop3.port", () -> environment.getServicePort("greenmail", 3110));
        registry.add("spring.mail.port", () -> environment.getServicePort("greenmail", 3025));
    }

    @ParameterizedTest
    @CsvSource({
        "Important Email Subject, This is the email body for john, john@domain.com"
    })
    void sendEmail(String subject, String body, String to) throws Exception {
        String host = environment.getServiceHost("communication", 8080);
        int port = environment.getServicePort("communication", 8080);

        String uuid = UUID.randomUUID().toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + TestTokenUtil.encode(TestTokenUtil.serviceClaimSet()));

        RequestEntity<String> request = RequestEntity.post("http://%s:%s/service-api/v1/messages".formatted(host, port))
            .headers(headers)
            .body("""
                {
                  "messages": [
                    {
                      "deliveryChannel": "email",
                      "priority": 0,
                      "tag": "%s",
                      "payload": {
                        "subject": "%s",
                        "body": "%s",
                        "to": ["%s"]
                      }
                    }
                  ]
                }
                """.formatted(uuid, subject, body, to));

        ResponseEntity<Void> exchange = template.exchange(request, Void.TYPE);
        Assertions.assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        Thread.sleep(5000);

        List<Message> emails = emailReader.getEmails(to, to);

        log.debug("received emails: {}", emails);
        Assertions.assertThat(emails).hasSize(1);
        for (javax.mail.Message email : emails) {
            log.info("email = " + email.toString());

            // From
            String receivedFrom = email.getFrom()[0].toString().replace("\"", "");
            assertThat(receivedFrom).isEqualTo(
                "%s <%s>".formatted(properties.getFromName(), properties.getFromAddress()));

            // To
            String realTo = email.getRecipients(javax.mail.Message.RecipientType.TO)[0].toString();
            assertThat(realTo).isEqualTo(to);

            // Subject
            assertThat(email.getSubject()).isEqualTo(subject);

            // Body
            String realBody = emailReader.getTextFromMessage(email);
            assertThat(realBody).isEqualTo(body);
        }
        emailReader.close();
    }
}