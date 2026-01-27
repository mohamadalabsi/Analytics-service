package com.pm.analyticsservice.kafka;


import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);


//     return to patient service to see how we send events to kafka topic from there again
//    any event that is send to this topic will be consumed by this method
//    groupId is to tell kafka broker who this consumer is
    @KafkaListener(topics = "patient" )//, groupId = "analytics-service") // specify topic that we
    // will consume from
    public void consumeEvent(byte[] event) {
        // Logic to process the consumed event , first convert byte[] to string
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
//            preform any business logic with the event data
            log.info("Received Patient Event : PatientId={} , PatientName={}, PatientEmail={}",
                    patientEvent.getPatientId() , patientEvent.getName() , patientEvent.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error deserializing event: {}", e.getMessage());
//            we do not want to throw exception here as it will crash the consumer , but we log
//            it to know what happened

        }

    }
}
