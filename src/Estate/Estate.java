package Estate;

import User.User;

public class Estate {
  private String title;
  private double price;
  private String location;
  private double distance;
  private double m2;

  private User owner;
  private User rentUser;

  private boolean isAvailable = true;

  public Estate(String title, double price, String location, double m2, User owner) {
    this.title = title;
    this.price = price;
    this.location = location;
    this.distance = Math.round((10 + (Math.random() * 90)) * 100.0) / 100.0;
    this.m2 = m2;
    this.owner = owner;
  }

  public String getTitle() {
    return title;
  }

  public double getPrice() {
    return price;
  }

  public String getLocation() {
    return location;
  }

  public double getDistance() {
    return distance;
  }

  public double getM2() {
    return m2;
  }

  public User getOwner() {
    return owner;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public User getRentUser() {
    return rentUser;
  }

  public void setRentUser(User rentUser) {
    if (rentUser == null) {
      this.isAvailable = true;
    } else {
      this.rentUser = rentUser;
      this.isAvailable = false;
    }
  }

  @Override
  public String toString() {
    return title;
  }

}
