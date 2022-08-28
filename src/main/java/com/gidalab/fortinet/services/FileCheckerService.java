package com.gidalab.fortinet.services;

import com.gidalab.fortinet.cache.LRUCache;
import com.gidalab.fortinet.reputation.FileType;
import com.gidalab.fortinet.reputation.ReputationService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileCheckerService {

  @Autowired
  private List<ReputationService> reputationServices;

  @Autowired
  private LRUCache scoreCache;

  public Double getScore(String fileHash, FileType fileType) {

    final Double cachedScore = scoreCache.get(fileHash);
    if (cachedScore != null) {
      return cachedScore;
    }

    Map<ReputationService, Double> repScores = new HashMap<>();
    int totalWeight = 0;
    for (ReputationService service : reputationServices) {
      if (!service.supportType(fileType)) {
        continue;
      }
      Double repScore;
      try {
        repScore = service.checkFile(fileHash);
      } catch (Exception e) {
        //todo: log
        continue;
      }
      if (repScore == null) {
        //todo: log
        continue;
      }
      if (repScore < 0 || repScore > service.getScale()) {
        //todo: log
        continue;
      }

      repScores.put(service, repScore);
      totalWeight += service.getAccuracy();
    }
    if (repScores.isEmpty()) {
      return null;
    }

    double score = 0.0;
    for (Entry<ReputationService, Double> entry : repScores.entrySet()) {
      score += (entry.getValue() * 100 / entry.getKey().getScale() * entry.getKey().getAccuracy());
    }
    score /= totalWeight;
    scoreCache.add(fileHash, score);
    return score;
  }

}
