package com.qht.aiemail.service;

import com.qht.aiemail.dto.LanguageHeuristicResult;
import reactor.core.publisher.Mono;

/**
 * Uses OpenAI to detect language & heuristics for an inbound email body.
 */
public interface LanguageHeuristicService {

    /**
     * Analyze raw user text and return structured language + heuristic info.
     */
    Mono<LanguageHeuristicResult> analyze(String text);
}
