package support.agenda.bean;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@RequestScoped
public class PickListBean {
    private DualListModel<Employee> listModel;

    @PostConstruct
    public void init() {
        //initial unselected list
        List<Employee> sourceList = Arrays.asList(
                new Employee("1", "Jim", "IT"),
                new Employee("2", "Sara", "Sale"),
                new Employee("3", "Tom", "Admin"),
                new Employee("4", "Diana", "IT")
        );

      //initial selected list
        List<Employee> destinationList = new ArrayList<Employee>();
        destinationList.add(new Employee("5", "Jessica", "Sale"));

        listModel = new DualListModel<Employee>(new ArrayList<Employee>(sourceList), destinationList);
    }

    public DualListModel<Employee> getListModel() {
        return listModel;
    }

    public void setListModel(DualListModel<Employee> listModel) {
        this.listModel = listModel;
    }
}