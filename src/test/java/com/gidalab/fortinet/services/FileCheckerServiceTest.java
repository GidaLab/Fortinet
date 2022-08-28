package com.gidalab.fortinet.services;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

import com.gidalab.fortinet.FortinetApplication;
import com.gidalab.fortinet.reputation.FileType;
import com.gidalab.fortinet.reputation.avocado.AvocadoClient;
import com.gidalab.fortinet.reputation.avocado.AvocadoResponseDto;
import com.gidalab.fortinet.reputation.bamboo.BambooClient;
import com.gidalab.fortinet.reputation.bamboo.BambooResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest(classes = FortinetApplication.class)
class FileCheckerServiceTest {

  @Autowired
  private FileCheckerService fileCheckerService;

  @MockBean
  private AvocadoClient avocadoClient;

  @MockBean
  private BambooClient bambooClient;

  @Test
  void testSuccess() {
    given(bambooClient.call("1A")).willReturn(BambooResponseDto.builder().score(80).build());
    given(avocadoClient.call("1A")).willReturn(AvocadoResponseDto.builder().score(0.6).build());
    assertEquals(66.66, fileCheckerService.getScore("1A", FileType.DLL), 0.01);

    given(bambooClient.call("1B")).willReturn(BambooResponseDto.builder().score(70).build());
    given(avocadoClient.call("1B")).willReturn(AvocadoResponseDto.builder().score(0.7).build());
    assertEquals(70.0, fileCheckerService.getScore("1B", FileType.DLL));
  }

  @Test
  void testSingleService() {
    given(bambooClient.call("2A")).willReturn(BambooResponseDto.builder().score(80).build());
    given(avocadoClient.call("2A")).willReturn(null);
    assertEquals(80, fileCheckerService.getScore("2A", FileType.DLL));

    given(bambooClient.call("2B")).willReturn(BambooResponseDto.builder().score(80).build());
    given(avocadoClient.call("2B")).willReturn(AvocadoResponseDto.builder().score(0.6).build());
    assertEquals(60, fileCheckerService.getScore("2B", FileType.ELF));
  }

  @Test
  void testNoResult() {
    given(bambooClient.call("1C")).willReturn(null);
    given(avocadoClient.call("1C")).willReturn(null);
    assertNull(null, fileCheckerService.getScore("1C", FileType.DLL));
  }

  @Test
  void testFailure() {
    given(bambooClient.call("1D")).willReturn(null);
    given(avocadoClient.call("1D")).willReturn(null);
    assertNull(null, fileCheckerService.getScore("1D", FileType.DLL));
  }

}