package jrds.snmp;

import java.util.Map;

import jrds.HostInfo;
import jrds.factories.HostBuilderAgent;
import jrds.factories.xml.JrdsElement;
import jrds.starter.StarterNode;

import org.apache.log4j.Logger;

public class SnmpHostBuilderAgent extends HostBuilderAgent {
    static final private Logger logger = Logger.getLogger(SnmpHostBuilderAgent.class);

    @Override
    public void buildStarters(JrdsElement fragment, StarterNode sn, HostInfo host) {
        JrdsElement snmpNode = fragment.getElementbyName("snmp");
        if(snmpNode != null) {
            logger.trace("found a snmp starter");
            SnmpStarter starter = snmpStarter(snmpNode, host);
            sn.registerStarter(starter);
        }
    }
    
    private SnmpStarter snmpStarter(JrdsElement d, HostInfo host) {
        SnmpStarter starter = new SnmpStarter();
        Map<String,String> attributes = d.attrMap();
        //Mandatory parameters
        starter.setCommunity(attributes.get("community"));
        starter.setVersion(attributes.get("version"));

        //Optional parameters
        String portStr = attributes.get("port");
        int port = jrds.Util.parseStringNumber(portStr, 161);
        starter.setPort(port);

        String hostName = attributes.get("host");
        if(hostName == null) {
            hostName = host.getDnsName();
        }
        starter.setHostname(hostName);
        return starter;
    }

}
