package com.littleinc.orm_benchmark.active;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ada on 12.06.14.
 */
@Table(name = "user")
public class User extends Model{

    public User(){
        super();
    }

    public static final String LAST_NAME_COLUMN = "last_name";

    public static final String FIRST_NAME_COLUMN = "first_name";

    @Column(name = LAST_NAME_COLUMN)
    private String mLastName;

    @Column(name = FIRST_NAME_COLUMN)
    private String mFirstName;

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }
}