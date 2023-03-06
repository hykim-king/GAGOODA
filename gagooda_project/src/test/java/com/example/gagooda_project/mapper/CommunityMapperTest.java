package com.example.gagooda_project.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityMapperTest {
    @Autowired
    private CommunityMapper communityMapper;

//    public CommunityMapperTest(CommunityMapper communityMapper) {
//        this.communityMapper = communityMapper;
//    }

    @Test
    void insertOne() {
    }

    @Test
    void updateOne() {
    }

    @Test
    void count() {
    }

    @Test
    void listAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void deleteOne() {
        int commId = 7;
        int delete = communityMapper.deleteById(commId);
        System.out.println(delete);
    }
}