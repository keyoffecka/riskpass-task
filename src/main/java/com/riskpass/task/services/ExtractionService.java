package com.riskpass.task.services;

import com.riskpass.task.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ExtractionService {
  private final Config config;
  public Config config() {return this.config;}

  public ExtractionService(final Config config) {
    this.config = config;
  }

  public List<String> extract(final String line) {
    List<String> values = new ArrayList<>();

    String text = line.trim();
    while (!text.isEmpty()) {
      if (text.startsWith(this.config().quote())) {
        text = this.addQuotedCell(text, values);
      } else {
        text = this.addUnquotedCell(text, values);
      }
    }

    return values;
  }

  private String addQuotedCell(
    final String text,
    final List<String> values
  ) {
    String newText = "";

    String unquotedText = text.substring(1);
    Matcher matcher = this.config().nextQuotePattern().matcher(unquotedText);
    if (matcher.find()) {
      newText = this.addCell(
        unquotedText, values,
        matcher.start(), matcher.end(),
        false
      );
    } else {
      this.addLastQuotedCell(unquotedText, values);
    }

    return newText;
  }

  private String addUnquotedCell(
    final String text,
    final List<String> values
  ) {
    String newText = "";

    Matcher matcher = this.config().commaPattern().matcher(text);
    if (matcher.find()) {
      newText = this.addCell(
        text, values,
        matcher.start(), matcher.end(),
        true
      );
    } else {
      this.addLastUnquotedCell(text, values);
    }

    return newText;
  }

  private void addLastQuotedCell(
    final String text,
    final List<String> values
  ) {
    Matcher matcher = this.config().lastQuotePattern().matcher(text);
    if (!matcher.find()) {
      throw new IllegalStateException();
    }

    values.add(text.substring(0, text.length() - 1));
  }

  private void addLastUnquotedCell(
    final String text,
    final List<String> values
  ) {
    values.add(text.trim());
  }

  private String addCell(
    final String text,
    final List<String> values,
    final int start,
    final int end,
    final boolean trim
  ) {
    String value = text.substring(0, start);
    if (trim) {
      value = value.trim();
    }
    values.add(value);

    String newText = text.substring(end).trim();
    if (newText.isEmpty()) {
      values.add("");
    }

    return newText;
  }
}
