package listeners;

import entities.Pro2;
import entities.Property;
import facades.Pro2Facade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@JMSDestinationDefinition(name = "java:app/jms/myTopic", interfaceName = "javax.jms.Topic", resourceAdapter = "jmsra", destinationName = "myTopic")

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "java:app/jms/myTopic")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/jms/myTopic")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "java:app/jms/myTopic")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
    ,
        @ActivationConfigProperty(propertyName = "addressList", propertyValue = "192.168.0.13")
})
public class ListenerERP implements MessageListener {

    @EJB
    private Pro2Facade pro2Facade;
    
    
    public ListenerERP() {
    }
    
    @Override
    public void onMessage(Message message) {
        System.err.println("nuevo mensaje en el topico");
        Property p = null;
        try {
            p = (Property) ((ObjectMessage)message).getObject();
            Pro2 p2 = new Pro2();
            p2.setAdress(p.getAdress());
            p2.setClientId(p.getClientId().getId());
            p2.setId(p.getId());
            p2.setIsAvailable(p.getIsAvailable());
            p2.setIsDelete(p.getIsDelete());
            p2.setNumberRooms(p.getNumberRooms());
            p2.setRent(p.getRent());
            pro2Facade.create(p2);
        } catch (JMSException ex) {
            Logger.getLogger(ListenerERP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
