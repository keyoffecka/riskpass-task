package com.riskpass.task;

import com.riskpass.task.config.Config;
import com.riskpass.task.services.ExtractionService;

public class Task {
  public static void main(final String[] args) {
    Config.Modifiable config = new Config.Modifiable();
    config.comma(";");

    ExtractionService extractionService = new ExtractionService(config);
    extractionService.extract(args[0]).forEach(System.out::println);
  }
}
