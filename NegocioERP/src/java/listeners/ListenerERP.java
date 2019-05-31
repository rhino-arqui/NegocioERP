package listeners;

import entities.Property;
import facades.FacadeERP;
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
import javax.mail.MessagingException;

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
        @ActivationConfigProperty(propertyName = "addressList", propertyValue = "10.192.12.42")
})
public class ListenerERP implements MessageListener {
    @EJB
    private FacadeERP facadeERP;
    
    public ListenerERP() {
    }
    
    @Override
    public void onMessage(Message message) {
        System.err.println("nuevo mensaje en el topico");
        Property p = null;
        try {
            p = (Property) ((ObjectMessage)message).getObject();
            facadeERP.guardarBD(p);
        } catch (JMSException ex) {
            Logger.getLogger(ListenerERP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
