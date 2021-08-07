package lv.animelistapp.test;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@Route("test")
public class Testing extends VerticalLayout implements Serializable {

    /*@Autowired
    private TestRepository testRepository;
    List<Test> testList;

    @Autowired*/
    public Testing(/*TestRepository testRepository*/) {
        /*this.testRepository = testRepository;
        testList = testRepository.getTestData();
        Select<Test> select = new Select<Test>();
        select.setItems(testList);
        select.setItemLabelGenerator(Test::getName);
        add(select);*/


    }

}
