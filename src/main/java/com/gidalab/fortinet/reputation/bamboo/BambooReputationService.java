package com.gidalab.fortinet.reputation.bamboo;

import com.gidalab.fortinet.reputation.FileType;
import com.gidalab.fortinet.reputation.ReputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BambooReputationService implements ReputationService {

  @Autowired
  private BambooClient client;

  @Override
  public boolean supportType(FileType fileType) {
    return fileType == FileType.DLL || fileType == FileType.EXE;
  }

  @Override
  public Double checkFile(String fileHash) {
    final BambooResponseDto response;
    try {
      response = client.call(fileHash);
    } catch (Exception e) {
      //todo: log
      return null;
    }
    return response.getScore();
  }

  @Override
  public int getScale() {
    return 100;
  }

  @Override
  public int getAccuracy() {
    return 4;
  }
}
