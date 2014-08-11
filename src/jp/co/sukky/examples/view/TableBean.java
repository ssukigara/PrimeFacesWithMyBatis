package jp.co.sukky.examples.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.SelectableDataModel;

import jp.co.sukky.examples.dao.PersonDao;
import jp.co.sukky.examples.model.PersonModel;
import jp.co.sukky.examples.mybatis.MyBatisSessionFactory;

/**
 * TableBean.
 */
@ManagedBean
@SessionScoped
public class TableBean implements Serializable, SelectableDataModel<PersonModel>{

    /** serialVersionUID as default. */
    private static final long serialVersionUID = 1L;

    /** List of PersonModel. */
    private List<PersonModel> persons;

    /** Selected PersonModel on the DataTable. */
    private PersonModel selectedPerson;

    /** Input data for Name from the Dialog. */
    private String inputtedName;

    /** Input data for Age from the Dialog. */
    private int inputtedAge;

    /** Input data for Address from the Dialog. */
    private String inputtedAddress;

    /** Whether deleteRowBtn is disable or not. */
    private boolean deleteRowBtnDisable;

    /** Whether deleteAllBtn is disable or not. */
    private boolean deleteAllBtnDisable;

    /**
     * Initialization.
     */
    public void init() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            // Non operation runs when the screen is shown by itself.
        } else {
            // Prepared PersonModel.
            persons = new ArrayList<PersonModel>();

            // Retrieve PersonModel from DB.
            PersonDao personDAO = new PersonDao(MyBatisSessionFactory.getSqlSessionFactory());
            persons = personDAO.selectAll();
            changeBtnStatus();
        }
    }

    /**
     * Add Person.
     */
    public void add() {
        // Add PersonModel to DB.
        PersonDao personDAO = new PersonDao(MyBatisSessionFactory.getSqlSessionFactory());
        PersonModel person = new PersonModel();
        person.setName(inputtedName);
        person.setAge(inputtedAge);
        person.setAddress(inputtedAddress);
        personDAO.insert(person);

        // Redisplay.
        persons = personDAO.selectAll();
        changeBtnStatus();

        // Clear input data from inputTexts
        inputtedName = "";
        inputtedAge = 0;
        inputtedAddress = "";
    }

    /**
     * Delete Person.
     */
    public void delete() {
        // Delete PersonModel from DB.
        PersonDao personDAO = new PersonDao(MyBatisSessionFactory.getSqlSessionFactory());
        personDAO.delete(selectedPerson.getId());

        // Clear selected person's object
        selectedPerson = null;

        // Redisplay.
        persons = personDAO.selectAll();
        changeBtnStatus();
    }

    /**
     * Delete All Persons.
     */
    public void deleteAll() {
        // Delete All PersonModel from DB.
        PersonDao personDAO = new PersonDao(MyBatisSessionFactory.getSqlSessionFactory());
        personDAO.deleteAll();

        // Redisplay.
        persons = personDAO.selectAll();
        changeBtnStatus();
    }

    /**
     * Get inputted name from the Dialog.
     * 
     * @return inputted person's name
     */
    public String getInputtedName() {
        return inputtedName;
    }

    /**
     * Set name into the Dialog.
     * 
     * @param person's name
     */
    public void setInputtedName(String inputtedName) {
        this.inputtedName = inputtedName;
    }

    /**
     * Get inputted age from the Dialog.
     * 
     * @return inputted person's age
     */
    public int getInputtedAge() {
        return inputtedAge;
    }

    /**
     * Set age into the Dialog.
     * 
     * @param inputtedAge person's age
     */
    public void setInputtedAge(int inputtedAge) {
        this.inputtedAge = inputtedAge;
    }

    /**
     * Get inputted address from the Dialog.
     * 
     * @return inputted person's address
     */
    public String getInputtedAddress() {
        return inputtedAddress;
    }

    /**
     * Set address into the Dialog.
     * 
     * @param inputtedAddress person's address
     */
    public void setInputtedAddress(String inputtedAddress) {
        this.inputtedAddress = inputtedAddress;
    }

    /**
     * Get Persons list for DataTable.
     * 
     * @return List of PersonModel
     */
    public List<PersonModel> getPersons() {
        return persons;
    }

    /**
     * Get selected PersonModel on DataTable.
     * 
     * @return selected PersonModel
     */
    public PersonModel getSelectedPerson() {
        return selectedPerson;
    }

    /**
     * Set selected PersonModel on DataTable.
     * 
     * @param selectedPerson
     */
    public void setSelectedPerson(PersonModel selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    /**
     * Get the status whether DeleteRow button is disabled or not.
     * 
     * @return button is disabled in case this status is true 
     */
    public boolean getDeleteRowBtnDisable() {
        return deleteRowBtnDisable;
    }

    /**
     * Set the status whether DeleteRow button is disabled or not.
     * 
     * @param button is disabled in case this status is true 
     */
    public void setDeleteRowBtnDisable(boolean deleteRowBtnDisable) {
        this.deleteRowBtnDisable = deleteRowBtnDisable;
    }

    /**
     * Get the status whether DeleteAll button is disabled or not.
     * 
     * @return button is disabled in case this status is true 
     */
    public boolean getDeleteAllBtnDisable() {
        return deleteAllBtnDisable;
    }

    /**
     * Set the status whether DeleteAll button is disabled or not.
     * 
     * @param button is disabled in case this status is true 
     */
    public void setDeleteAllBtnDisable(boolean deleteAllBtnDisable) {
        this.deleteAllBtnDisable = deleteAllBtnDisable;
    }

    /* (non-Javadoc)
     * @see org.primefaces.model.SelectableDataModel#getRowKey(java.lang.Object)
     */
    @Override
    public Object getRowKey(PersonModel person) {
        return person.getId();
    }

    /* (non-Javadoc)
     * @see org.primefaces.model.SelectableDataModel#getRowData(java.lang.String)
     */
    @Override
    public PersonModel getRowData(String rowKey) {
        int iRowKey = Integer.parseInt(rowKey);
        for (PersonModel person : persons) {
            if (person.getId() == iRowKey) {
                return person;
            }
        }
        return null;
    }

    /**
     * Event listener method for DataTable's selection.
     * 
     * @param event SelectEvent
     */
    public void onRowSelect(SelectEvent event) {
        changeBtnStatus();
    }

    /**
     * Event listener method for DataTable's unselection.
     * 
     * @param event UnselectEvent
     */
    public void onRowUnselect(UnselectEvent event) {
        selectedPerson = null;
        changeBtnStatus();
    }

    /**
     * Change buttons status enable or disable by conditions.
     */
    private void changeBtnStatus() {
        if (persons.size() == 0) {
            // following buttons are disabled in case of no persons data.
            deleteAllBtnDisable = true;
            deleteRowBtnDisable = true;
        } else {
            // Delete All button is set as enable status in case persons data exists.
            deleteAllBtnDisable = false;
            // Delete Row button depends on the existence of selectedPerson.
            if (selectedPerson == null) {
                deleteRowBtnDisable = true;
            } else {
                deleteRowBtnDisable = false;
            }
        }
    }
}
