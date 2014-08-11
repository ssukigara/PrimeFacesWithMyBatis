package jp.co.sukky.examples.model;

import java.io.Serializable;

/**
 * PersonModel.
 */
public class PersonModel implements Serializable {

    /**
     * serialVersionUID as default.
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    private int id;

    /**
     * Person's name.
     */
    private String name;

    /**
     * Person's age.
     */
    private int age;

    /**
     * Person's address.
     */
    private String address;

    /**
     * Get person's id.
     * 
     * @return Person's id
     */
    public int getId() {
        return id;
    }

    /**
     * Get person's name.
     * 
     * @return Person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set person's name.
     * 
     * @param name person's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get person's age.
     * 
     * @return person's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Set person's age.
     * 
     * @param age person's age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Get person's address.
     * 
     * @return person's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set person's address.
     * 
     * @param address person's address
     */
    public void setAddress(String address) {
        this.address = address;
    }


}
