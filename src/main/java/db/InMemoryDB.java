package db;

import java.util.ArrayList;

public class InMemoryDB {
    private static ArrayList<User> userDatabase = new ArrayList();

    static{
        registerUser(new User("123456789V","Kasun","Nuwan","Galle Road,Panadura",16));
        registerUser(new User("111111111V","Nimal","Perera","Kandy Road,Colombo",16));
        registerUser(new User("222222222V","Pasan","Kumara","Matara Road,Galle",16));
        registerUser(new User("333333333V","Lalith","Saman","Jaffna Road,Wawniya",16));
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
