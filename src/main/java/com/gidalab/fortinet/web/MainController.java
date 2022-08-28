package com.gidalab.fortinet.web;

import com.gidalab.fortinet.reputation.FileType;
import com.gidalab.fortinet.services.FileCheckerService;
import com.gidalab.fortinet.web.model.FileScoreResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

  @Autowired
  private FileCheckerService fileCheckerService;

  @GetMapping("/classify_file")
  public ResponseEntity getFileScore(@RequestParam String fileHash,
      @RequestParam FileType fileType) {
    final Double score = fileCheckerService.getScore(fileHash, fileType);
    return ResponseEntity.status(HttpStatus.OK)
        .body(FileScoreResponse.builder().score(score).build());
  }

}
