package com.elasticdemo.demo.elasticsearch.indexer.userindexer;

import com.elasticdemo.demo.elasticsearch.indexer.userindexer.dto.UserDto;

public interface UserIndexer {
    UserDto getUserDocumentById(Long id);
    void indexUser(UserDto user);
}
