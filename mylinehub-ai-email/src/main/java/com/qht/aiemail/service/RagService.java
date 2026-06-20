package com.qht.aiemail.service;

import reactor.core.publisher.Mono;

/**
 * Fetches contextual snippets from QHT Clinic RAG/vector store.
 */
public interface RagService {

    Mono<String> fetchContext(String organization, String text);
}
