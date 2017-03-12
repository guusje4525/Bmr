package client;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class Client {
        
        private static SOAPMessage createSOAPRequest() throws Exception {
                MessageFactory messageFactory = MessageFactory.newInstance();
                SOAPMessage soapMessage = messageFactory.createMessage();
                SOAPPart soapPart = soapMessage.getSOAPPart();
                
                // SOAP Envelope
                SOAPEnvelope envelope = soapPart.getEnvelope();
                envelope.addNamespaceDeclaration("ser", "http://example.com/");

                // SOAP Body
                //Functie die je aanroept
                SOAPBody soapBody = envelope.getBody();
                SOAPElement soapBodyElem = soapBody.addChildElement("generateBmr", "ser");
                
                //Alle elementen aangeven en waarde zetten
                SOAPElement weight = soapBodyElem.addChildElement("weight", "ser");
                weight.addTextNode("70");
                
                SOAPElement height = soapBodyElem.addChildElement("height", "ser");
                height.addTextNode("175");
                
                SOAPElement gender = soapBodyElem.addChildElement("gender", "ser");
                gender.addTextNode("m");
                
                SOAPElement age = soapBodyElem.addChildElement("age", "ser");
                age.addTextNode("21");
                
                //Envelope opslaan en input naar console schrijven
                soapMessage.saveChanges();
                System.out.println("Input:");
                soapMessage.writeTo(System.out);
                return soapMessage;
        }

        private static void printSOAPResponse(SOAPMessage soapResponse) throws Exception {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                Source sourceContent = soapResponse.getSOAPPart().getContent();
                System.out.println("\nResponse:");
                StreamResult result = new StreamResult(System.out);
                transformer.transform(sourceContent, result);
        }

        public static void main(String args[]) {
                try {
                         // Create SOAP Connection
                        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
                        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

                        //Send SOAP Message to SOAP Server
                        String url = "http://localhost:8080/DynamicWebProject/services/BmrClass?wsdl";
                        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
                        
                        // Process the SOAP Response
                        printSOAPResponse(soapResponse);
                        
                        soapConnection.close();
                }
                catch (Exception e) {
                        System.err.println("Error occurred while sending SOAP Request to Server");
                        e.printStackTrace();
                }
        }
}