package com.ESproject.AccountApp.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class FavouriteCoin {


    @Id
    @GeneratedValue
    private int aid;
    private String coinName;

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof FavouriteCoin)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        FavouriteCoin c = (FavouriteCoin) o;

        // Compare the data members and return accordingly
        return coinName.equals(c.coinName);
    }
}
