CREATE TABLE couriers (
  id INT PRIMARY KEY AUTO_INCREMENT,
  trackingNumber VARCHAR(100),
  sender VARCHAR(100),
  receiver VARCHAR(100),
  status VARCHAR(50)
);
 database 
import javax.persistence.*;

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
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

Entity cclass
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class CourierDAO {
    private SessionFactory factory;

    public CourierDAO() {
        factory = new Configuration().configure().addAnnotatedClass(Courier.class).buildSessionFactory();
    }

    public void createCourier(Courier courier) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(courier);
        tx.commit();
        session.close();
    }

    public Courier getCourierById(int id) {
        Session session = factory.openSession();
        Courier courier = session.get(Courier.class, id);
        session.close();
        return courier;
    }

    public void updateStatus(int id, String newStatus) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Courier courier = session.get(Courier.class, id);
        if (courier != null) {
            courier.setStatus(newStatus);
            session.update(courier);
        }
        tx.commit();
        session.close();
    }

    public void deleteCourier(int id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Courier courier = session.get(Courier.class, id);
        if (courier != null) {
            session.delete(courier);
        }
        tx.commit();
        session.close();
    }

    public void close() {
        factory.close();
    }
}

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 5.3//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-5.3.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/courierdb</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">password</property>

        <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="hibernate.show_sql">true</property>
    </session-factory>
</hibernate-configuration>
