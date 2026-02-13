package com.freecall.service;

import com.freecall.config.FreeSwitchProperties;
import com.freecall.dto.CallControlResponse;
import com.freecall.dto.FreeSwitchHealthResponse;
import com.freecall.dto.OriginateRequest;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.springframework.stereotype.Service;

@Service
public class FreeSwitchService {

    private final Client client = new Client();
    private final FreeSwitchProperties properties;

    public FreeSwitchService(FreeSwitchProperties properties) {
        this.properties = properties;
    }

    public synchronized void ensureConnected() {
        if (client.canSend()) {
            return;
        }
        client.connect(properties.host(), properties.port(), properties.password(), properties.timeoutSeconds());
    }

    public CallControlResponse originate(OriginateRequest req) {
        ensureConnected();

        String endpoint = req.endpoint() != null ? req.endpoint()
                : String.format("sofia/gateway/%s/%s", defaultGateway(req.gateway()), req.callee());
        String command = String.format(
                "originate {origination_caller_id_number=%s,ignore_early_media=true}%s &park()",
                req.caller(), endpoint);

        EslMessage message = client.sendSyncApiCommand("bgapi", command);
        String reply = String.join("\n", message.getBodyLines());
        return new CallControlResponse(true, command, reply);
    }

    public CallControlResponse hangup(String uuid, String cause) {
        ensureConnected();
        String command = "uuid_kill " + uuid + " " + (cause == null ? "NORMAL_CLEARING" : cause);
        EslMessage message = client.sendSyncApiCommand("api", command);
        String reply = String.join("\n", message.getBodyLines());
        return new CallControlResponse(true, command, reply);
    }

    public FreeSwitchHealthResponse healthCheck() {
        String command = "status";
        try {
            ensureConnected();
            EslMessage message = client.sendSyncApiCommand("api", command);
            String reply = String.join("\n", message.getBodyLines());
            return new FreeSwitchHealthResponse(
                    true,
                    client.canSend(),
                    properties.host(),
                    properties.port(),
                    command,
                    reply,
                    null
            );
        } catch (Exception ex) {
            return new FreeSwitchHealthResponse(
                    false,
                    client.canSend(),
                    properties.host(),
                    properties.port(),
                    command,
                    null,
                    ex.getMessage()
            );
        }
    }

    private String defaultGateway(String gateway) {
        return gateway == null || gateway.isBlank() ? "external" : gateway;
    }
}
