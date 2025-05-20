import jakarta.persistence.*;

@Entity
@Table(name = "couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String trackingNumber;
    private String sender;
    private String receiver;
    private String status;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
-

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "[" + id + "] " + trackingNumber + " | " + sender + " → " + receiver + " [" + status + "]";
    }
}
--------------------------------------------DAO-------------------------
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CourierDAO {

    public void saveCourier(Courier courier) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(courier);
            tx.commit();
        }
    }

    public List<Courier> getAllCouriers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Courier", Courier.class).list();
        }
    }

    public Courier getCourierById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Courier.class, id);
        }
    }

    public void updateCourier(Courier updatedCourier) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(updatedCourier);
            tx.commit();
        }
    }

    public void deleteCourier(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Courier c = session.get(Courier.class, id);
            if (c != null) session.remove(c);
            tx.commit();
        }
    }
}

-------------------------------------------UTIL----------------------------------------
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Courier.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

---------------------------------------config.xml-----------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC 
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN" 
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
  <session-factory>
    <!-- DB Connection -->
    <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/courierdb</property>
    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password">yourpassword</property>

    <!-- Hibernate Properties -->
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
    <property name="hibernate.hbm2ddl.auto">update</property>
    <property name="show_sql">true</property>
  </session-factory>
</hibernate-configuration>
