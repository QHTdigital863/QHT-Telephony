package com.qht.crm.rag.repository;


import java.util.List;

import com.qht.crm.rag.dto.ResultDTO;

public interface EmbeddingRepositoryCustom {
	List<ResultDTO> findNearestByVector(String organization, float[] vector, int limit);
}
