package com.riskpass.task.config;

import java.util.regex.Pattern;

public class Config {
  private static final String QUOTE = "\"";
  private static final String COMMA = ",";

  String quote = Config.QUOTE;
  public String quote(){return this.quote;}

  String comma = Config.COMMA;
  public String comma(){return this.comma;}

  Pattern commaPattern = null;
  public Pattern commaPattern() {return this.commaPattern;}

  Pattern nextQuotePattern = null;
  public Pattern nextQuotePattern() {return this.nextQuotePattern;}

  Pattern lastQuotePattern = null;
  public Pattern lastQuotePattern() {return this.lastQuotePattern;}

  public Config() {
    this.commaPattern = this.createCommaPattern();
    this.nextQuotePattern = this.createNextQuotePattern();
    this.lastQuotePattern = this.createLastQuotePattern();
  }

  public Config(
    final String quote,
    final String comma
  ) {
    this.quote = quote;
    this.comma = comma;

    this.commaPattern = this.createCommaPattern();
    this.nextQuotePattern = this.createNextQuotePattern();
    this.lastQuotePattern = this.createLastQuotePattern();
  }

  Pattern createCommaPattern() {
    return Pattern.compile(this.comma);
  }

  Pattern createNextQuotePattern() {
    return Pattern.compile(this.quote + "\\s*" + this.comma);
  }

  Pattern createLastQuotePattern() {
    return Pattern.compile(this.quote + "$");
  }

  public static class Modifiable extends Config {
    public Modifiable() {
    }

    public Modifiable(
      final String quote,
      final String comma
    ) {
      super(quote, comma);
    }

    public void comma(final String comma) {
      this.comma = comma;
      this.commaPattern = this.createCommaPattern();
      this.nextQuotePattern = this.createNextQuotePattern();
    }

    public void quote(final String quote) {
      this.quote = quote;
      this.nextQuotePattern = this.createNextQuotePattern();
      this.lastQuotePattern = this.createLastQuotePattern();
    }
  }
}
