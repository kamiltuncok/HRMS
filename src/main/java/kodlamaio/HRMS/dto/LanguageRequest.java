package kodlamaio.HRMS.dto;

public record LanguageRequest(
    String languageName,
    String level,
    Long resumeId
) {}
