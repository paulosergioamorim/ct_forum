package br.ufes.ct_forum.dtos;

public record RatingSummaryDto(
    long positiveCount,
    long negativeCount,
    Boolean userVote
) {}
