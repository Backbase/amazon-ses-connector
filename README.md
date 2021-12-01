## Repository Overview

- This project is a development of a small set of [Backbase Service SDK](https://community.backbase.com/documentation/ServiceSDK/latest/index) (**Spring Boot** and **Cloud**) based Microservices projects that implement cloud-native intuitive, Microservices design patterns, and coding best practices.
- The project follows [**CloudNative**](https://www.cncf.io/) recommendations and the [**twelve-factor app**](https://12factor.net/) methodology for building *software-as-a-service apps* to show how μServices should be developed and deployed.
- This project uses technologies broadly used in Backbase.Like Docker, Kubernetes, Java SE 11, Spring Boot, Spring Cloud
- 'ses-email-connector' has been generated using `core-service-archetype` - [Community guide](https://community.backbase.com/documentation/ServiceSDK/latest/create_a_core_service)
- This service implements `actions-spec` library. It listens to Email notification event placed in ActiveMQ by the `Message Delivery - Communication service` and routes the message to the recipient through Amazon Simple Email Service (SES).
- Refer to [workflow guide](../../../docs/tree/master/backend) for Backend CI Workflow documentation

---
## Repository Description
### Project Structure
The project structure for each custom integration service follows the pattern as described below :

```
.
├── .github                      # All GitHub Actions files
│   ├── ISSUE_TEMPLATE           # Templates for 'major','minor','patch' releases
│   └── workflows                # GitHub Actions workflows for CI
├── src                          # Source and Unit Test files
    ├── main                     # Application container projects
    │   ├── java/com/backbase/productled
    |   |   ├── client
    |   |   |   └── ...          # Api client classes and helpers
    │   │   ├── communication    # Messaging classes
    │   │   │   └── ...
    │   │   ├── config           # Configuration classes
    │   │   │   └── ...
    │   │   ├── mapper           # Mapper classes
    │   │   │   └── ...
    │   │   ├── service          # Service classes
    │   │   │   └── ...
    │   │   └── util             # Utility classes
    │   │   |    └── ...
    |   │   ├── validator        # Validator classes
    │   │   │    └── ...
    │   └── resources            # All resource files except core classes
    │       └── ...
    └── test                     # JUnit test file
        └── ...
```

To view individual classes for this repository, select relevant branch from the GitHub UI and then press ‘.'
This will open the GitHub Web Editor.Alternatively, you can also access the Web Editor by changing .com to .dev in the URL.

Expand each file in the Web Editor for explanation and purpose.

### Project Dependencies

---
## Repository Configurations
### DSC (basic-installation.yml) configuration

```yaml
custom:
  enabled: true
  services:
    ses-email-connector:
      enabled: true
      app:
        metadata:
          public: "'true'"
        image:
          tag: "x.y.z-SNAPSHOT"
          repository: ses-email-connector
      database: false
      activemq: true
      env:
        "spring.autoconfigure.exclude": "org.springframework.cloud.netflix.eureka.loadbalancer.LoadBalancerEurekaAutoConfiguration"
        "spring.cloud.stream.default.content-type": "application/json"
        "spring.cloud.stream.default.consumer.exchangeDurable": "true"
        "spring.cloud.stream.default.consumer.concurrency": "1"
        "spring.cloud.stream.default.consumer.prefetch": "1000"
        "spring.cloud.stream.default.consumer.autoCommitOnError": "false"
        "spring.cloud.stream.default.consumer.max-attempts": "5"
        "spring.cloud.stream.default.consumer.backOffInitialInterval": "2000"
        "spring.cloud.stream.default.consumer.backOffMaxInterval": "6000"
        "spring.cloud.stream.default.consumer.backOffMultiplier": "2.0"
        "spring.cloud.stream.default.consumer.default-retryable": "true"
        "spring.cloud.stream.function.definition": "commLowPriority;commMediumPriority;commHighPriority;commTracking"
        "spring.cloud.stream.default-binder": "activemq"
        "backbase.email.worker-count": "2"
        "spring.cloud.stream.bindings.commLowPriority-in-0.destination": "Backbase.communication.email-low-priority"
        "spring.cloud.stream.bindings.commMediumPriority-in-0.destination": "Backbase.communication.email-medium-priority"
        "spring.cloud.stream.bindings.commHighPriority-in-0.destination": "Backbase.communication.email-high-priority"
        "spring.cloud.stream.bindings.commTracking-out-0.destination": "Backbase.communication.messages-tracking"
        spring.mail.username:
          valueFrom:
            secretKeyRef:
              name: ref-dev-eu-central-1-email-sender-account
              key: username
        spring.mail.password:
          valueFrom:
            secretKeyRef:
              name: ref-dev-eu-central-1-email-sender-account
              key: password
        spring.mail.host:
          valueFrom:
            configMapKeyRef:
              name: email-env
              key: host
        spring.mail.port:
          valueFrom:
            configMapKeyRef:
              name: email-env
              key: port
        spring.mail.properties.mail.transport.protocol:
          valueFrom:
            configMapKeyRef:
              name: email-env
              key: protocol
        spring.mail.properties.mail.smtp.auth:
          valueFrom:
            configMapKeyRef:
              name: email-env
              key: smtpAuth
        spring.mail.properties.mail.smtp.starttls.enable:
          valueFrom:
            configMapKeyRef:
              name: email-env
              key: starttlsEnable
        backbase.mail.from-address:
          valueFrom:
            configMapKeyRef:
              name: email-env
              key: fromAddress
        backbase.mail.from-name:
          valueFrom:
            configMapKeyRef:
              name: email-env
              key: fromName
    
```
`Where x = major version, y = minor version and z = incremental version.`

### Mambu config (if applicable)
Not applicable for `ses-email-service`

#### Application configuration
The following properties **must** be set as they have no default:

Property | Description
--- | ---
**spring.mail.username** | sender email username
**spring.mail.password** | sender email password
**spring.mail.host** | email server host
**spring.mail.port** | email server port
**spring.mail.properties.mail.transport.protocol** | protocol used
**spring.mail.properties.mail.smtp.auth** | authorization type
**spring.mail.properties.mail.smtp.starttls.enable** | encryption enabled
**backbase.mail.from-address** | senders email address
**backbase.mail.from-name** | senders name

## Customisation in project
### Configuration changes
All configuration properties for using email-integration-service are project specific and should be replaced.

### Stream config (if applicable)
Not applicable for 'ses-email-connector'.

---
## Customisation in project
All configuration properties for using email-integration-service are project specific and should be replaced.

---

### Component changes

---
## Getting Started
### BaaS setup

- [ ] Step 1: Modify https://github.com/baas-devops-reference/ref-self-service/blob/main/self-service.tfvars by adding to `ecr` list name of new repository: 'ses-email-connector'
- [ ] Step 2: Checkout the following repository: https://github.com/baas-devops-reference/ref-applications-live/blob/main/runtimes/dev/basic-installation.yaml apply your deployment configurations example see _DSC (basic-installation.yml) configuration_ above.
- [ ] Step 3: Run the pre-commit to validate the configurations => ` pre-commit run --all-files --show-diff-on-failure --color=always`
- [ ] Step 4: Commit and Push your changes; wait for the template rendering and lint jobs to complete
- [ ] Step 5: Merge into `master` to trigger deployment.

### Local setup

- [ ] Step 1: Ensure to check the prerequisites for [local developer environment](https://community.backbase.com/documentation/ServiceSDK/latest/create_developer_environment)
- [ ] Step 2: Create `src/main/resources/application-local.yaml` file, then add and modify:

```yaml 
spring:
  mail:
    port: 587
    host: <AWS_SES_HOST>
    username: <AWS_SES_ACCOUNT_NAME>
    password: <AWS_SES_ACCOUNT_PASSWORD>
    properties:
      mail:
        debug: true
        transport:
          protocol: smtp
        smtp:
          auth: true
          starttls:
            enable: true

backbase:
  mail:
    from-address: <SENDER_ADDRESS>
    from-name: <SENDER_NAME>
```

- [ ] Step 3: Run command => `mvn spring-boot:run -Dspring.profiles.active=local`
- [ ] Step 4: To run the service from the built binaries, use => `java -jar target/ses-email-service-x.y.z-SNAPSHOT.jar -Dspring.profiles.active=local`
---
## Contributions
Please create a branch and a PR with your contributions. Commit messages should follow [semantic commit messages](https://seesparkbox.com/foundry/semantic_commit_messages)
