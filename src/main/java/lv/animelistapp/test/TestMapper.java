package lv.animelistapp.test;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    List<Test> getTestData();

    //TeacherBean getTeacherById(@Param("id") String id);
}
