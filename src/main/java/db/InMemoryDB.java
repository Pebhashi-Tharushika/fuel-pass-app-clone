package db;

import java.util.ArrayList;

public class InMemoryDB {
    private static ArrayList<User> userDatabase = new ArrayList();

    static{
        registerUser(new User("893456789V","William","James","Richmond Drive, Liverpool",16));
        registerUser(new User("987654321V","Oliver","Alexander","10 Oakwood Lane, Leeds",16));
        registerUser(new User("654321789V","David","Christopher","Primrose Avenue, Oxford",16));
        registerUser(new User("786954321V","Henry","Samuel","Queen's Court, London",16));
    }

    public static boolean registerUser(User newUser){
        if(findUser(newUser.getNic())!=null){
            return false;
        }
        userDatabase.add(newUser);
        return true;
    }

    public static User findUser(String nic){
        for (User user : userDatabase) {
            if(user.getNic().equalsIgnoreCase(nic)){
                return user;
            }
        }
        return null;
    }

    public static void removeUser(String nic){
        User user = findUser(nic);
        if(user!= null){
            userDatabase.remove(user);
        }
    }

    public static ArrayList<User> getUserDatabase(){
        return userDatabase;
    }

    public static ArrayList<User> findUsers(String query){
        ArrayList<User> searchResult = new ArrayList<>();
        for (User user : userDatabase) {
            if(user.getNic().contains(query) || user.getFirstName().contains(query) || user.getLastName().contains(query) || user.getAddress().contains(query)){
                searchResult.add(user);
            }
        }
        return searchResult;
    }
}
