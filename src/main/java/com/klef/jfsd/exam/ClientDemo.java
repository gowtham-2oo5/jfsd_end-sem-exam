package com.klef.jfsd.exam;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class ClientDemo {

    public static void main(String[] args) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory sf = cfg.buildSessionFactory();
        Session s = sf.openSession();

        Boolean loop = true;
        Scanner sc = new Scanner(System.in);
        while(loop){
            System.out.println("Enter choice: ");
            int choice = sc.nextInt();
            switch (choice){
                case 1: insertOne(s, sc);break;
                case 2: retrieveAll(s);break;
                case 3: loop = false; break;
            }
        }
        sc.close();
        s.close();
    }
    private static void insertOne(Session s, Scanner sc) {
        System.out.println("Enter Name, Age, Email, Gender, Location, Mobile: ");
        Client c = new Client();
        c.setName(sc.next());
        c.setAge(sc.nextInt());
        c.setEmail(sc.next());
        c.setGender(sc.next());
        c.setLocation(sc.next());
        c.setMobile(sc.nextLong());

        Transaction tx = s.beginTransaction();

        String sql = "INSERT INTO clients (name, age, email, gender, location, mobile) VALUES (?, ?, ?, ?, ?, ?)";
        Query query = s.createSQLQuery(sql);
        query.setParameter(1, c.getName());
        query.setParameter(2, c.getAge());
        query.setParameter(3, c.getEmail());
        query.setParameter(4, c.getGender());
        query.setParameter(5, c.getLocation());
        query.setParameter(6, c.getMobile());

        query.executeUpdate();
        tx.commit();

        System.out.println("Client created successfully,  " + c.getName());
    }


    private static void retrieveAll(Session s){
        List<Client> res = s.createQuery("from Client", Client.class).list();
        for(Client c : res){
            System.out.println(c);
        }

    }

}
