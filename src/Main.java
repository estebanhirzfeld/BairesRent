import javax.swing.JOptionPane;

import Estate.Estate;
import User.User;

public class Main {

  public static void customDialog(String title, String msg, int type) {
    if (title == null) {
      title = "BairesRent";
    } else {
      title = "BairesRent - " + title;
    }

    JOptionPane.showMessageDialog(
        null,
        msg,
        title,
        type);
  }

  public static int customOptionsDialog(String[] options, String title, String msg) {
    if (title == null) {
      title = "BairesRent";
    }
    return JOptionPane.showOptionDialog(null, msg, "BairesRent - " + title, JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null, options, options[0]);
  }

  public static User userSelectDialog(User[] users, String title, String msg) {
    if (title == null) {
      title = "BairesRent";
    }
    return (User) JOptionPane.showInputDialog(null, msg, "BairesRent - " + title, JOptionPane.INFORMATION_MESSAGE,
        null, users, users[0]);
  }

  public static Estate estateSelectDialog(Estate[] estates, String title, String msg) {
    if (title == null) {
      title = "BairesRent";
    }
    if (estates == null || estates.length <= 0) {
      customDialog("Error", "No hay Propiedades Disponibles", 0);
      return null;
    }
    return (Estate) JOptionPane.showInputDialog(null, msg, "BairesRent - " + title, JOptionPane.INFORMATION_MESSAGE,
        null, estates, estates[0]);
  }

  public static String menuFormatMsg(User user, Estate[] estates) {
    StringBuilder msg = new StringBuilder();

    if (user != null) {
      String selectedUser = user.getFullName();

      msg.append("Bienvenido ").append(selectedUser).append("!\n\n");

      if (estates.length > 0) {
        msg.append("Propiedades:\n");
        for (Estate estate : estates) {
          msg.append(estate.getTitle()).append(" | ");
          msg.append(estate.getLocation()).append(" | ");
          msg.append("m2: ").append(estate.getM2()).append(" | ");
          msg.append("Distancia: ").append(estate.getDistance()).append("km").append(" | ");
          if (estate.isAvailable()) {
            msg.append("Disponible").append(" | ");
          } else {
            msg.append("No Disponible").append(" | ");
            msg.append(estate.getRentUser()).append(" | ");
          }
          msg.append("$").append(estate.getPrice()).append(" | \n");
        }
      } else {
        msg.append("Aún no hay Propiedades.\n");
      }
    }

    return msg.toString();
  }

  public static String userRentedEstatesFormatMsg(User user) {
    Estate[] estates = user.getRentedEstates();
    StringBuilder msg = new StringBuilder();

    if (user != null) {
      String selectedUser = user.getFullName();
      msg.append("Propiedades Alquiladas: ").append(selectedUser).append("\n\n");

      if (estates.length > 0) {
        for (Estate estate : estates) {
          msg.append(estate.getTitle()).append(" en ");
          msg.append(estate.getLocation()).append("\n");
        }
      } else {
        msg.append("Aún no hay Propiedades.\n");
      }
    }

    return msg.toString();
  }

  public static String CustomFilterFormatMsg(double maxDistance, double maxPrice, double maxM2) {
    StringBuilder msg = new StringBuilder();

    msg.append("Filtro Personalizado:").append("\n\n");

    msg.append("Max price: $").append(maxPrice).append("\n");
    msg.append("Max distance: ").append(maxDistance).append(" km\n");
    msg.append("Max m2: ").append(maxM2).append("\n\n");

    return msg.toString();
  }

  public static Estate[] getEstatesSortedByDistance(Estate[] estates) {
    Estate[] estatesSortedByDistance = estates.clone();

    for (int i = 0; i < estatesSortedByDistance.length - 1; i++) {
      for (int j = 0; j < estatesSortedByDistance.length - 1 - i; j++) {
        if (estatesSortedByDistance[j].getDistance() > estatesSortedByDistance[j + 1].getDistance()) {
          Estate temp = estatesSortedByDistance[j];
          estatesSortedByDistance[j] = estatesSortedByDistance[j + 1];
          estatesSortedByDistance[j + 1] = temp;
        }
      }
    }

    return estatesSortedByDistance;
  }

  public static Estate[] getEstatesSortedBySize(Estate[] estates) {
    Estate[] estatesSortedBySize = estates.clone();

    for (int i = 0; i < estatesSortedBySize.length - 1; i++) {
      for (int j = 0; j < estatesSortedBySize.length - 1 - i; j++) {
        if (estatesSortedBySize[j].getM2() < estatesSortedBySize[j + 1].getM2()) {
          Estate temp = estatesSortedBySize[j];
          estatesSortedBySize[j] = estatesSortedBySize[j + 1];
          estatesSortedBySize[j + 1] = temp;
        }
      }
    }

    return estatesSortedBySize;
  }

  public static Estate[] getEstatesFiltered(Estate[] estates, Double maxDistance, Double maxPrice, Double maxM2,
      Estate[] allEstates) {

    if ((maxDistance == null || maxDistance == 0) &&
        (maxPrice == null || maxPrice == 0) &&
        (maxM2 == null || maxM2 == 0)) {
      return allEstates;
    }

    int count = 0;
    for (Estate estate : estates) {
      if ((maxDistance == null || maxDistance == 0 || estate.getDistance() <= maxDistance) &&
          (maxPrice == null || maxPrice == 0 || estate.getPrice() <= maxPrice) &&
          (maxM2 == null || maxM2 == 0 || estate.getM2() <= maxM2)) {
        count++;
      }
    }

    Estate[] filteredEstates = new Estate[count];
    int index = 0;

    for (Estate estate : estates) {
      if ((maxDistance == null || maxDistance == 0 || estate.getDistance() <= maxDistance) &&
          (maxPrice == null || maxPrice == 0 || estate.getPrice() <= maxPrice) &&
          (maxM2 == null || maxM2 == 0 || estate.getM2() <= maxM2)) {
        filteredEstates[index++] = estate;
      }
    }

    return filteredEstates;
  }

  public static Estate[] CustomFilterDialog(Estate[] estates, Estate[] allEstates) {
    Estate[] filteredEstates = estates;

    int filterOption;
    boolean exitFilter = false;
    String[] filterOptions = { "Precio Max", "Distancia Max", "Tamaño Max", "Buscar", "Cancelar" };

    double maxDistance = 0;
    double maxPrice = 0;
    double maxM2 = 0;

    while (!exitFilter) {
      filterOption = customOptionsDialog(filterOptions, "Filtro",
          CustomFilterFormatMsg(maxDistance, maxPrice, maxM2));
      switch (filterOptions[filterOption]) {
        case "Precio Max":
          String priceInput;
          while (true) {
            priceInput = JOptionPane.showInputDialog(null, "Ingrese precio max");
            if (checkNumber(priceInput)) {
              maxPrice = Math.abs(Double.parseDouble(priceInput));
              break;
            } else {
              customDialog("Error", "Por favor, ingrese un número válido.", 0);
            }
          }
          break;
        case "Distancia Max":
          String distanceInput;
          while (true) {
            distanceInput = JOptionPane.showInputDialog(null, "Ingrese distancia max");
            if (checkNumber(distanceInput)) {
              maxDistance = Math.abs(Double.parseDouble(distanceInput));
              break;
            } else {
              customDialog("Error", "Por favor, ingrese un número válido.", 0);
            }
          }
          break;
        case "Tamaño Max":
          String sizeInput;
          while (true) {
            sizeInput = JOptionPane.showInputDialog(null, "Ingrese tamaño max (m2)");
            if (checkNumber(sizeInput)) {
              maxM2 = Math.abs(Double.parseDouble(sizeInput));
              break;
            } else {
              customDialog("Error", "Por favor, ingrese un número válido.", 0);
            }
          }
          break;
        case "Buscar":
          filteredEstates = getEstatesFiltered(allEstates, maxDistance, maxPrice, maxM2, allEstates);
          exitFilter = true;
          break;
        case "Cancelar":
          exitFilter = true;
          break;
      }
    }
    return filteredEstates;
  }

  public static Estate[] getAvailableEstates(Estate[] estates) {
    int availableCount = 0;
    for (Estate estate : estates) {
      if (estate.isAvailable()) {
        availableCount++;
      }
    }

    Estate[] availableestates = new Estate[availableCount];
    int index = 0;

    for (Estate estate : estates) {
      if (estate.isAvailable()) {
        availableestates[index++] = estate;
      }
    }

    return availableestates;
  }

  public static boolean checkNumber(String input) {
    if (input == null || input.isEmpty()) {
      return false;
    }

    boolean hasDecimal = false;

    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);

      if (c == '.') {
        if (hasDecimal) {
          return false;
        }
        hasDecimal = true;
      } else if (!Character.isDigit(c)) {
        return false;
      }
    }

    return hasDecimal || input.length() > 0;
  }

  public static void main(String[] args) {
    User user_1 = new User("Tony Montana", "tony@montana.com");
    User user_2 = new User("John Locke", "john@locke.com");
    User user_3 = new User("Nicholas Cage", "nicholas@cage.com");
    User user_4 = new User("Homer Thompson", "homer@thompson.com");
    User[] users = { user_1, user_2 };

    Estate estate_1 = new Estate("Amplio Dpto. En CABA", 400000, "Recoleta", 200, user_1);
    Estate estate_2 = new Estate("Increible Casa con Piscina", 600000, "Canning", 500, user_1);
    Estate estate_3 = new Estate("Duplex Vacacional, Mendoza", 300000, "San Rafael", 400, user_2);
    Estate estate_4 = new Estate("Moderno Loft en Palermo", 350000, "Palermo", 150, user_3);
    Estate estate_5 = new Estate("Casa de Campo en Pilar", 450000, "Pilar", 150, user_4);
    Estate estate_6 = new Estate("Departamento de Lujo en Belgrano", 750000, "Belgrano", 250, user_3);
    Estate estate_7 = new Estate("Cabaña en Bariloche", 280000, "Bariloche", 275, user_4);
    Estate estate_8 = new Estate("Penthouse en Puerto Madero", 900000, "Puerto Madero", 330, user_1);

    Estate[] estates = { estate_1, estate_2, estate_3, estate_4, estate_5, estate_6, estate_7, estate_8 };
    Estate[] filteredEstates = estates;
    Estate[] tempEstates;

    Estate selectedEstate;

    int option;
    String[] menuOptions = { "Alquilar", "Ordenar por Distancia", "Ordenar por Tamaño", "Filtro Personalizado",
        "Mis Alquileres",
        "Cambiar de Cuenta" };

    boolean exitSignIn = false;
    while (!exitSignIn) {
      User loggedUser = userSelectDialog(users, "Iniciar Sesion", "Seleccionar Cuenta");
      if (loggedUser == null) {
        exitSignIn = true;
      } else {
        boolean logOut = false;
        while (!logOut) {
          option = customOptionsDialog(menuOptions, "Tu Cuenta", menuFormatMsg(loggedUser, estates));
          switch (menuOptions[option]) {
            case "Alquilar":
              estates = getAvailableEstates(estates);
              selectedEstate = estateSelectDialog(estates, "Alquilar", "Propiedades Disponibles");
              if (selectedEstate != null) {
                selectedEstate.setRentUser(loggedUser);
                loggedUser.addRentedBook(selectedEstate);
                customDialog(null, "Propiedad: '" + selectedEstate.toString() + "'\nAlquilado con exito!", 1);
              }
              break;
            case "Ordenar por Distancia":
              estates = getEstatesSortedByDistance(estates);
              break;
            case "Ordenar por Tamaño":
              estates = getEstatesSortedBySize(estates);
              break;
            case "Filtro Personalizado":
              estates = CustomFilterDialog(estates, filteredEstates);
              break;
            case "Mis Alquileres":
              tempEstates = loggedUser.getRentedEstates();
              if (tempEstates != null) {
                JOptionPane.showMessageDialog(null, userRentedEstatesFormatMsg(loggedUser),
                    "BairesRent - Mis Propiedades", 1);
              } else {
                customDialog("Error", "No hay Propiedades Disponibles", 0);
              }
              break;
            case "Cambiar de Cuenta":
              logOut = true;
          }
        }
      }
    }
  }
}