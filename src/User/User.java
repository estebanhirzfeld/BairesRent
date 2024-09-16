package User;

import Estate.Estate;

public class User {

  private String fullName;
  private String email;

  private Estate[] rentedEstates;

  public User(String fullName, String email) {
    this.fullName = fullName;
    this.email = email;
  }

  public String getFullName() {
    return fullName;
  }

  public Estate[] getRentedEstates() {
    return rentedEstates;
  }

  public void addRentedEstate(Estate newEstate) {
    if (this.rentedEstates == null) {
      this.rentedEstates = new Estate[1];
      this.rentedEstates[0] = newEstate;
    } else {
      Estate[] combinedEstates = new Estate[this.rentedEstates.length + 1];

      for (int i = 0; i < this.rentedEstates.length; i++) {
        combinedEstates[i] = this.rentedEstates[i];
      }
      combinedEstates[this.rentedEstates.length] = newEstate;
      this.rentedEstates = combinedEstates;
    }
  }

  @Override
  public String toString() {
    return email;
  }

}