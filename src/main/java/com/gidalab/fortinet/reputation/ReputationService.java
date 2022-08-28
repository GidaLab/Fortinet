package com.gidalab.fortinet.reputation;

public interface ReputationService {

  /**
   * @param fileType
   * @return true if file is supported
   */
  boolean supportType(FileType fileType);

  /**
   * @param fileHash
   * @return a score between 100 and the service scale, null is there is no result
   */
  Double checkFile(String fileHash);

  /**
   * @return the scale of the result
   */
  int getScale();

  /**
   * @return number between 1-10
   */
  int getAccuracy();

}
