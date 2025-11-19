package org.ukma.spring.crooodle.hotelsvc.contract;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.ukma.spring.crooodle.hotelsvc.config.FeignClientConfig;
import org.ukma.spring.crooodle.usersvc.client.UserSvcClient;
import org.ukma.spring.crooodle.usersvc.dto.Role;
import org.ukma.spring.crooodle.usersvc.dto.UserResponseDto;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
  classes = {UserClientContractTest.TestConfig.class},
  webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@TestPropertySource(properties = {
  "spring.cloud.openfeign.client.config.user-svc.url=http://localhost:${stubrunner.runningstubs.user-svc.port}",
  "spring.cloud.loadbalancer.enabled=false",
  "security.internal.api-key=test-api-key",
  "spring.autoconfigure.exclude=" +
    "org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration," +
    "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
    "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
})
@AutoConfigureStubRunner(
  ids = "org.ukma.spring.crooodle:user-svc:+:stubs",
  stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class UserClientContractTest {

  @Autowired
  private UserSvcClient userSvcClient;

  @ParameterizedTest
  @MethodSource("userIds")
  void shouldFetchUserByIdFromStub(UUID id) {
    UserResponseDto dto = userSvcClient.getUser(id);

    assertThat(dto.id()).isEqualTo(id);
    assertThat(dto.name()).isEqualTo("John Doe");
    assertThat(dto.email()).isEqualTo("john.doe@example.com");
    assertThat(dto.role()).isEqualTo(Role.ROLE_HOTEL_OWNER);
  }

  private static Stream<UUID> userIds() {
    return Stream.of(
      UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
      UUID.randomUUID()
    );
  }

  @Configuration
  @EnableFeignClients(clients = UserSvcClient.class)
  @Import({FeignClientConfig.class})
  @ImportAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
  }, value = {
    FeignAutoConfiguration.class,
    HttpMessageConvertersAutoConfiguration.class
  })
  static class TestConfig {
  }
}
