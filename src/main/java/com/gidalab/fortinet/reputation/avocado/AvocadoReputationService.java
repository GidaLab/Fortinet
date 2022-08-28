package com.gidalab.fortinet.reputation.avocado;

import com.gidalab.fortinet.reputation.FileType;
import com.gidalab.fortinet.reputation.ReputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvocadoReputationService implements ReputationService {

  @Autowired
  private AvocadoClient client;

  @Override
  public boolean supportType(FileType fileType) {
    return true;
  }

  @Override
  public Double checkFile(String fileHash) {
    final AvocadoResponseDto response;
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
    return 1;
  }

  @Override
  public int getAccuracy() {
      return 8;
  }
}
