package lv.animelistapp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("testRepository")
public class TestRepository {

    //@Autowired
    TestMapper testMapper;

    public List<Test> getTestData() {
        return testMapper.getTestData();
    }
}
