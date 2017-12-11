package com.elasticdemo.demo;

import com.elasticdemo.demo.elasticsearch.IndexManager;
import com.elasticdemo.demo.elasticsearch.indexer.userindexer.UserIndexer;
import com.elasticdemo.demo.elasticsearch.indexer.userindexer.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private IndexManager indexManager;
    @Autowired
    private UserIndexer userIndexer;

    @Before
    public void setUp() {
        indexManager.recreate();
    }

    @Test
    public void saveUserDocument() {
        UserDto userDto = new UserDto();

        userDto.id = 1L;
        userDto.firstName = "Reece";
        userDto.lastName = "Fowell";
        userDto.age = 30;

        userIndexer.indexUser(userDto);

        indexManager.reindex();

        UserDto actual = userIndexer.getUserDocumentById(1L);

        assertThat(actual, is(notNullValue()));
        assertThat(actual.id, is(1L));
        assertThat(actual.firstName, is("Reece"));
        assertThat(actual.lastName, is("Fowell"));
        assertThat(actual.age, is(30));
    }
}
